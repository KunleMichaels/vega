package eu.socialedge.vega.backend.account.domain;

import eu.socialedge.vega.backend.account.domain.funding.PaymentMethod;
import eu.socialedge.vega.backend.shared.PassengerId;
import eu.socialedge.vega.backend.shared.TokenId;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PassengerTest {

    @SuppressWarnings("unchecked")
    private static HashSet<TokenId> notEmptyTokenIdSet =
            (HashSet<TokenId>) mock(HashSet.class);

    @SuppressWarnings("unchecked")
    private static HashSet<PaymentMethod> notEmptyPaymentMethodSet =
            (HashSet<PaymentMethod>) mock(HashSet.class);

    static {
        when(notEmptyTokenIdSet.isEmpty()).thenReturn(false);
        when(notEmptyPaymentMethodSet.isEmpty()).thenReturn(false);
    }

    private static final List<String> INVALID_EMAIL_ADDRESSES =
            new ArrayList<String>() {{
                add("plainaddress");
                add("@example.com");
                add("Joe Smith <email@example.com>");
                add("email.example.com");
                add(".email@example.com");
                add("email@-example.com");
                add("email@example..com");
            }};

    private static final List<String> VALID_EMAIL_ADDRESSES =
            new ArrayList<String>() {{
                add("email@example.com");
                add("firstname.lastname@example.com");
                add("email@subdomain.example.com");
                add("firstname+lastname@example.com");
                add("email@example.co.jp");
                add("email@example.name");
            }};

    @Test
    public void shouldThrowExceptionForInvalidEmailAddress() {
        INVALID_EMAIL_ADDRESSES.forEach(email -> {
            assertThatThrownBy(() ->
                    new Passenger(new PassengerId(1L), "test", email, "12",
                            notEmptyTokenIdSet, notEmptyPaymentMethodSet))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("email address");
            assertThatThrownBy(() ->
                    new Passenger(new PassengerId(1L), "test", email, "12",
                            notEmptyTokenIdSet, notEmptyPaymentMethodSet)
                                .email(email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("email address");
        });
    }
    @Test
    public void shouldNotThrowExceptionForValidEmailAddress() {
        VALID_EMAIL_ADDRESSES.forEach(email -> {
            new Passenger(new PassengerId(1L), "test", email, "12",
                    notEmptyTokenIdSet, notEmptyPaymentMethodSet)
                        .email(email);
        });
    }
}