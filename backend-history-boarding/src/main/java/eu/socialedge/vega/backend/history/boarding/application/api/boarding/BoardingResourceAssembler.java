package eu.socialedge.vega.backend.history.boarding.application.api.boarding;

import eu.socialedge.vega.backend.application.api.resource.ResourceAssembler;
import eu.socialedge.vega.backend.history.boarding.domain.Boarding;
import org.springframework.stereotype.Component;

@Component
public class BoardingResourceAssembler implements ResourceAssembler<Boarding,BoardingResource> {

    @Override
    public BoardingResource toResource(Boarding entity) {
        return BoardingResource.builder()
            .id(entity.id())
            .boardingType(entity.boardingType())
            .passengerId(entity.passengerId())
            .terminalId(entity.terminalId())
            .location(entity.location())
            .timestamp(entity.timestamp().toString())
            .build();
    }
}
