package eu.socialedge.vega.backend.terminal.application.api.terminal;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.fare.domain.VehicleType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Builder
@Getter @Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class InstallationResource {

    @NotNull(message = "Operator ID can not be null")
    private OperatorId operatorId;

    @NotNull(message = "Vehicle type can not be null")
    private VehicleType vehicleType;

    @NotEmpty(message = "Vehicle identifier can not be empty")
    private String vehicleIdentifier;
}
