package eu.socialedge.vega.backend.fare.application.api.fare;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Builder
@Getter @Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class DeductionResource {

    @NotEmpty(message = "Deduction description can not be empty")
    private final String description;

    @NotNull(message = "Deduction multiplier can not be null")
    private final Double multiplier;
}
