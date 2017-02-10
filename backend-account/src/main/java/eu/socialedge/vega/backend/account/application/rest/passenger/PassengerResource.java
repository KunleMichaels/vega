package eu.socialedge.vega.backend.account.application.rest.passenger;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class PassengerResource extends ResourceSupport {

    @NotNull(message = "Name can not be null")
    private String name;

    @NotNull(message = "Email can not be null")
    private String email;

    @NotNull(message = "Password can not be null")
    @JsonProperty(access = WRITE_ONLY)
    private String password;
}
