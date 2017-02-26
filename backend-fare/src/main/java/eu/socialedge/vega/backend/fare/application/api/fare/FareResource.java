package eu.socialedge.vega.backend.fare.application.api.fare;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.fare.domain.VehicleType;
import eu.socialedge.vega.backend.geo.domain.Location;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@Getter @Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class FareResource {

    private FareId id;

    @NotEmpty(message = "Price can not be empty")
    private String price;

    @NotNull(message = "Validity period can not be null")
    private String validity;

    private Set<DeductionResource> deductions;

    @NotEmpty(message = "Set of vehicle types can not be empty")
    private Set<VehicleType> vehicleTypes;

    @NotEmpty(message = "Zone vertices can not be empty")
    private Set<Location> vertices;

    @NotEmpty(message = "Set of operator IDs can not be empty")
    private Set<OperatorId> operatorIds;
}
