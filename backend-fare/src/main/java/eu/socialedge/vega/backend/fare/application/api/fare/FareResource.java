package eu.socialedge.vega.backend.fare.application.api.fare;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.fare.domain.VehicleType;
import eu.socialedge.vega.backend.geo.domain.Zone;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    @JsonUnwrapped
    private Zone zone;

    @NotEmpty(message = "Set of operator IDs can not be empty")
    private Set<OperatorId> operatorIds;
}
