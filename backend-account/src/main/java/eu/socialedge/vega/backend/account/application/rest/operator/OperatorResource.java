package eu.socialedge.vega.backend.account.application.rest.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class OperatorResource extends ResourceSupport {

    @NotNull
    String name;

    @NotNull
    String description;
}
