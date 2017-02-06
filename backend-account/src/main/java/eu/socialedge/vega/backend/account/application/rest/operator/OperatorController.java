package eu.socialedge.vega.backend.account.application.rest.operator;

import eu.socialedge.vega.backend.account.domain.Operator;
import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.OperatorRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@Transactional(readOnly = true)
@RequestMapping(value = "/operators")
public class OperatorController {

    @Autowired
    private OperatorEntityMapper mapper;

    @Autowired
    private OperatorRepository operatorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @NotNull OperatorResource operatorResource) {
        val operator = mapper.fromResource(operatorResource);
        operatorRepository.add(operator);

        URI uri = linkTo(getClass()).slash(operator.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(path = "/{operatorId}")
    public ResponseEntity<OperatorResource> read(@PathVariable @NotNull OperatorId operatorId) {
        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val operatorResource = mapper.toResource(operatorOpt.get());
        return ResponseEntity.ok(operatorResource);
    }

    @GetMapping
    public ResponseEntity<Collection<OperatorResource>> read() {
        Collection<Operator> operators = operatorRepository.listActive();

        val operatorResources = mapper.toResources(operators);
        return ResponseEntity.ok(operatorResources);
    }

    @Transactional
    @PutMapping(path = "/{operatorId}")
    public ResponseEntity<OperatorResource> update(@PathVariable @NotNull OperatorId operatorId,
                                                   @NotNull OperatorResource operatorResource) {

        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val operator = operatorOpt.get();

        operator.name(operatorResource.name());
        operator.description(operatorResource.description());

        return ResponseEntity.ok(operatorResource);
    }
}
