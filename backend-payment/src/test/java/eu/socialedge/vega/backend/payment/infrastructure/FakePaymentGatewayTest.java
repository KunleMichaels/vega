/**
 * SocialEdge Vega - An Electronic Transit Fare Payment System
 * Copyright (c) 2016 SocialEdge
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package eu.socialedge.vega.backend.payment.infrastructure;

import eu.socialedge.vega.backend.payment.domain.*;
import eu.socialedge.vega.backend.payment.domain.funding.PaymentMethod;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.money.MonetaryAmount;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FakePaymentGatewayTest {

    private PaymentGateway paymentGateway;

    private @Mock PaymentMethod paymentMethod;

    private @Mock Token deattachedToken;

    private @Mock Authorization deattachedAuthorization;

    private @Mock MonetaryAmount moneyMock;

    @Before
    public void setupGateway() {
        paymentGateway = new FakePaymentGateway();
    }

    @Test
    public void shouldCreateValidPaymentTokens() {
        String descr = "test";
        boolean primary = true;

        Token token = paymentGateway.tokenize(paymentMethod, descr, primary);

        assertTrue(isNotBlank(token.identifier()));
        assertFalse(token.isExpired());
        assertEquals(primary, token.isPrimary());
        assertEquals(descr, token.description());
    }

    @Test
    public void shouldNotAllowToRetokenizePaymentMethods() {
        assertThatThrownBy(() -> {
            paymentGateway.tokenize(paymentMethod);
            paymentGateway.tokenize(paymentMethod);
        }).isInstanceOf(PaymentGatewayException.class).hasMessageContaining("token");
    }

    @Test
    public void shouldNotAllowChargeAndAuthOnDeattachedTokens() {
        assertThatThrownBy(() -> paymentGateway.charge(deattachedToken, moneyMock, null))
            .isInstanceOf(PaymentGatewayException.class)
            .hasMessageContaining("token");

        assertThatThrownBy(() -> paymentGateway.auth(deattachedToken, moneyMock, null))
            .isInstanceOf(PaymentGatewayException.class)
            .hasMessageContaining("token");
    }

    @Test
    public void shouldNotAllowCaptureUnknownAuth() {
        assertThatThrownBy(() -> paymentGateway.capture(deattachedAuthorization))
            .isInstanceOf(PaymentGatewayException.class)
            .hasMessageContaining("auth");
    }

    @Test
    public void shouldChargeCorrectly() {
        String desc = "desc";

        Token token = paymentGateway.tokenize(paymentMethod);
        Charge charge = paymentGateway.charge(token, moneyMock, desc);

        assertTrue(isNotBlank(charge.id()));
        assertEquals(token, charge.token());
        assertEquals(moneyMock, charge.amount());
    }

    @Test
    public void shouldAuthCorrectly() {
        String desc = "desc";

        Token token = paymentGateway.tokenize(paymentMethod);
        Authorization auth = paymentGateway.auth(token, moneyMock, "desc");

        assertTrue(isNotBlank(auth.id()));
        assertEquals(token, auth.token());
        assertEquals(moneyMock, auth.amount());
        assertEquals(desc, auth.description());
    }

    @Test
    public void shouldFinalizeLastCapture() {
        Token token = paymentGateway.tokenize(paymentMethod);
        MonetaryAmount authAmount = Money.of(500, "UAH");
        Authorization auth = paymentGateway.auth(token, authAmount, "desc");

        Capture capture = paymentGateway.capture(auth, authAmount);
        assertTrue(capture.isFinal());
    }

    @Test
    public void shouldSettlePaymentPartiallyCorrectly() {
        Token token = paymentGateway.tokenize(paymentMethod);
        MonetaryAmount authAmount = Money.of(500, "UAH");
        Authorization auth = paymentGateway.auth(token, authAmount, "desc");

        MonetaryAmount firstCaptureAmount = Money.of(200, "UAH");
        Capture firstCapture = paymentGateway.capture(auth, firstCaptureAmount);

        MonetaryAmount firstCaptureAuthReminder = authAmount.subtract(firstCaptureAmount);

        assertFalse(firstCapture.isFinal());
        assertEquals(firstCaptureAuthReminder, firstCapture.reminder());

        MonetaryAmount lastCaptureAmount = Money.of(300, "UAH");
        Capture lastCapture = paymentGateway.capture(auth, lastCaptureAmount);

        MonetaryAmount secondCaptureAuthReminder =
            firstCaptureAuthReminder.subtract(lastCaptureAmount);

        assertEquals(secondCaptureAuthReminder, lastCapture.reminder());
    }

    @Test
    public void shouldRemoveAuthAfterSettlement() {
        Token token = paymentGateway.tokenize(paymentMethod);
        MonetaryAmount authAmount = Money.of(500, "UAH");
        Authorization auth = paymentGateway.auth(token, authAmount, "desc");
        paymentGateway.capture(auth, authAmount);

        assertThatThrownBy(() -> paymentGateway.capture(auth, authAmount))
            .isInstanceOf(PaymentGatewayException.class)
            .hasMessageContaining("auth");
    }
}
