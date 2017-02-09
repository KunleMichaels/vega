package eu.socialedge.vega.backend.account.application.rest.operator;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.OperatorRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Transactional(readOnly = true)
@RequestMapping(value = "/operators")
public class OperatorController {

    @Autowired
    private OperatorEntityMapper mapper;

    @Autowired
    private OperatorRepository operatorRepository;

    @Transactional
    @RequestMapping(method = POST)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid OperatorResource operatorResource) {
        val operator = mapper.fromResource(operatorResource);
        operatorRepository.add(operator);

        val uri = linkTo(getClass()).slash(operator.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET, path = "/{operatorId}")
    public ResponseEntity<OperatorResource> read(@PathVariable @NotNull OperatorId operatorId) {
        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val operatorResource = mapper.toResource(operatorOpt.get());
        return ResponseEntity.ok(operatorResource);
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<OperatorResource>> read() {
        val operators = operatorRepository.listActive();

        val operatorResources = mapper.toResources(operators);
        return ResponseEntity.ok(operatorResources);
    }

    @Transactional
    @RequestMapping(method = PUT, path = "/{operatorId}")
    public ResponseEntity<Void> update(@PathVariable @NotNull OperatorId operatorId,
                                       @RequestBody  @NotNull @Valid OperatorResource operatorResource) {

        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val operator = operatorOpt.get();

        operator.name(operatorResource.name());
        operator.description(operatorResource.description());

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = "/{operatorId}")
    public ResponseEntity<Void> deactivate(@PathVariable @NotNull OperatorId operatorId) {
        if (!operatorRepository.contains(operatorId)) {
            return ResponseEntity.notFound().build();
        }

        operatorRepository.deactivate(operatorId);
        return ResponseEntity.ok().build();
    }
}
