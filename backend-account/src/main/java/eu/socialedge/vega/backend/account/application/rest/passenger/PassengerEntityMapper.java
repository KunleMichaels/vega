package eu.socialedge.vega.backend.account.application.rest.passenger;

import eu.socialedge.vega.backend.account.domain.Passenger;
import eu.socialedge.vega.backend.application.rest.EntityResourceMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PassengerEntityMapper extends EntityResourceMapper<Passenger, PassengerResource> {

    public PassengerEntityMapper() {
        super(PassengerController.class, Passenger.class, PassengerResource.class);
    }

    @Override
    public PassengerResource enhance(PassengerResource passengerResource, Passenger entity) {
        passengerResource.add(
            linkTo(methodOn(PassengerController.class).tokens(entity.id())).withRel("tokens"),
            linkTo(methodOn(PassengerController.class).tags(entity.id())).withRel("tags"),
            linkTo(methodOn(PassengerController.class).addTags(entity.id(), Collections.emptySet())).withRel("addTags"),
            linkTo(methodOn(PassengerController.class).removeTags(entity.id(), Collections.emptySet())).withRel("removeTags")
        );

        return passengerResource;
    }
}
