package eu.socialedge.vega.backend.history.boarding.application.api.boarding;

import eu.socialedge.vega.backend.application.api.serialization.EntityResourceMapper;
import eu.socialedge.vega.backend.history.boarding.domain.Boarding;
import lombok.val;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BoardingEntityMapper extends EntityResourceMapper<Boarding, BoardingResource> {

    public BoardingEntityMapper() {
        super(Boarding.class, BoardingResource.class);
    }

    @Override
    public BoardingResource toResource(Boarding entity) {
        val resource = super.toResource(entity);

        resource.add(linkTo(methodOn(BoardingController.class).read(entity.id())).withSelfRel());

        return resource;
    }

    public Resources<BoardingResource> toResources(Collection<Boarding> operators) {
        val resources = operators.stream().map(this::toResource).collect(Collectors.toList());
        val resourcesCollection = new Resources<BoardingResource>(resources);

        resourcesCollection.add(linkTo(methodOn(BoardingController.class).read()).withSelfRel());

        return resourcesCollection;
    }
}
