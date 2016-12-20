package eu.socialedge.vega.backend.payment.domain;

import javax.money.MonetaryAmount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Accessors(fluent = true)
public class Capture {

    public enum State {
        PENDING, COMPLETED, REFUNDED
    }

    private final String captureId;

    private final State state;

    private final MonetaryAmount amount;

    private final boolean isFinal;

    private final String description;
}
