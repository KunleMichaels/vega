package eu.socialedge.vega.backend.payment.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Accessors(fluent = true)
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Token {

    private final String tokenId;

    private final String description;
}
