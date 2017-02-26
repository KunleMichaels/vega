package eu.socialedge.vega.backend.fare.application.api.fare;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.application.api.Endpoints;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.fare.domain.FareRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static eu.socialedge.vega.backend.application.api.Endpoints.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Transactional(readOnly = true)
public class FareController {

    @Autowired
    private FareResourceMapper mapper;

    @Autowired
    private FareRepository fareRepository;

    @Transactional
    @RequestMapping(method = POST, path = FARES_ROOT)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid FareResource fareResource,
                                 UriComponentsBuilder uriBuilder) {
        val fare = mapper.fromResource(fareResource);
        fareRepository.add(fare);

        val uri = uriBuilder.path(Endpoints.FARES_ID).buildAndExpand(fare.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET, path = FARES_ROOT)
    public ResponseEntity<Collection<FareResource>> read() {val fares = fareRepository.list();
        val fareResources = mapper.toResources(fares);

        return ResponseEntity.ok(fareResources);
    }

    @RequestMapping(method = GET, path = FARES_ID)
    public ResponseEntity<FareResource> read(@PathVariable @NotNull FareId fareId) {
        val fareOpt = fareRepository.get(fareId);

        if (!fareOpt.isPresent())
            return ResponseEntity.notFound().build();

        val fareResource = mapper.toResource(fareOpt.get());
        return ResponseEntity.ok(fareResource);
    }

    @Transactional
    @RequestMapping(method = PATCH, path = FARES_ID)
    public ResponseEntity<Void> update(@RequestBody @NotNull @Valid FareResource fareResource,
                       @PathVariable @NotNull FareId fareId) {
        val fareOpt = fareRepository.get(fareId);

        if (!fareOpt.isPresent())
            return ResponseEntity.notFound().build();

        val fare = fareOpt.get();
        mapper.applyResource(fareResource, fare);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = FARES_ID)
    public ResponseEntity<Void> delete(@PathVariable @NotNull FareId fareId) {
        if (!fareRepository.contains(fareId))
            return ResponseEntity.notFound().build();

        fareRepository.remove(fareId);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = POST, path = FARES_OPERATORS)
    public ResponseEntity<Void> attachOperator(@PathVariable @NotNull FareId fareId,
                                               @RequestBody @NotNull OperatorId operatorId) {
        val fareOpt = fareRepository.get(fareId);

        if (!fareOpt.isPresent())
            return ResponseEntity.notFound().build();

        val fare = fareOpt.get();
        fare.addOperatorId(operatorId);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = FARES_OPERATORS_ID)
    public ResponseEntity<Void> detachOperator(@PathVariable @NotNull FareId fareId,
                                               @PathVariable @NotNull OperatorId operatorId) {
        val fareOpt = fareRepository.get(fareId);

        if (!fareOpt.isPresent())
            return ResponseEntity.notFound().build();

        val fare = fareOpt.get();
        fare.removeOperatorId(operatorId);

        return ResponseEntity.ok().build();
    }
}
