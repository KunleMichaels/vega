package eu.socialedge.vega.backend.history.boarding.application.api.boarding;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import eu.socialedge.vega.backend.geo.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Getter
@Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
@Relation(collectionRelation = "boardings")
public class BoardingResource extends ResourceSupport {

    private String boardingType;

    @JsonUnwrapped
    private Location location;

    private String timestamp;
}
