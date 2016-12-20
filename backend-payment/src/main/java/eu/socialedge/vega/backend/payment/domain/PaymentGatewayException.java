package eu.socialedge.vega.backend.payment.domain;

public class PaymentGatewayException extends RuntimeException {
    public PaymentGatewayException(String message) {
        super(message);
    }

    public PaymentGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentGatewayException(Throwable cause) {
        super(cause);
    }
}
