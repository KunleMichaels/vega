package eu.socialedge.vega.backend.account.application.rest.passenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class PassengerResource extends ResourceSupport {

    @NotNull(message = "Name may not be null")
    String name;

    @NotNull(message = "Email may not be null")
    String email;

    @NotNull(message = "Password may not be null")
    @JsonIgnore
    String password;
}
