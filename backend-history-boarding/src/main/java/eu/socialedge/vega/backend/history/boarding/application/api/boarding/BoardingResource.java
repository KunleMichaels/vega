package eu.socialedge.vega.backend.history.boarding.application.api.boarding;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.geo.domain.Location;
import eu.socialedge.vega.backend.history.boarding.domain.BoardingId;
import eu.socialedge.vega.backend.history.boarding.domain.BoardingType;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@Getter @Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class BoardingResource {

    private BoardingId id;

    private BoardingType boardingType;

    private PassengerId passengerId;

    private TerminalId terminalId;

    private Location location;

    private String timestamp;
}
