package eu.socialedge.vega.backend.payment.domain;

import eu.socialedge.vega.backend.payment.domain.funding.PaymentMethod;

import javax.money.MonetaryAmount;

public interface PaymentGateway {

    <P extends PaymentMethod> Token tokenize(P paymentMethod)
            throws PaymentGatewayException;

    Charge charge(Token token, MonetaryAmount amount, String description)
            throws PaymentGatewayException;

    Authorization auth(Token token, MonetaryAmount amount, String description)
            throws PaymentGatewayException;

    Capture capture(Token token, MonetaryAmount amount, String statementDescription)
            throws PaymentGatewayException;

    default Capture capture(Token token) throws PaymentGatewayException {
        return capture(token, null, null);
    }
}
