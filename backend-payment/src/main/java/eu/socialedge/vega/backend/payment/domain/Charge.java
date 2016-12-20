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
public class Charge {

    private final String chargeId;

    private final MonetaryAmount amount;

    private final String description;
}
