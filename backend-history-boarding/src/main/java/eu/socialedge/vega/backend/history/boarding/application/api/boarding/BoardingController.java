package eu.socialedge.vega.backend.history.boarding.application.api.boarding;

import eu.socialedge.vega.backend.history.boarding.domain.BoardingId;
import eu.socialedge.vega.backend.history.boarding.domain.BoardingRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static eu.socialedge.vega.backend.application.api.Endpoints.BOARDINGS_ID;
import static eu.socialedge.vega.backend.application.api.Endpoints.BOARDINGS_ROOT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BoardingController {

    @Autowired
    private BoardingResourceAssembler resourceAssembler;

    @Autowired
    private BoardingRepository boardingRepository;

    @RequestMapping(method = GET, path = BOARDINGS_ROOT)
    public ResponseEntity<Collection<BoardingResource>> read() {
        val boardings = boardingRepository.list();
        val boardingResources = resourceAssembler.toResources(boardings);

        return ResponseEntity.ok(boardingResources);
    }

    @RequestMapping(method = GET, path = BOARDINGS_ID)
    public ResponseEntity<BoardingResource> read(@PathVariable @NotNull BoardingId boardingId) {
        val boardingOpt = boardingRepository.get(boardingId);

        if (!boardingOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val boardingResource = resourceAssembler.toResource(boardingOpt.get());
        return ResponseEntity.ok(boardingResource);
    }
}
