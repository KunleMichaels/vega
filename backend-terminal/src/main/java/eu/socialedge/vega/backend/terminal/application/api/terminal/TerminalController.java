package eu.socialedge.vega.backend.terminal.application.api.terminal;

import eu.socialedge.vega.backend.application.api.Endpoints;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import eu.socialedge.vega.backend.terminal.domain.TerminalRepository;
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

import static eu.socialedge.vega.backend.application.api.Endpoints.TERMINALS_ID;
import static eu.socialedge.vega.backend.application.api.Endpoints.TERMINALS_ROOT;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Transactional(readOnly = true)
public class TerminalController {

    @Autowired
    private TerminalResourceMapper mapper;

    @Autowired
    private TerminalRepository terminalRepository;

    @Transactional
    @RequestMapping(method = POST, path = TERMINALS_ROOT)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid TerminalResource terminalResource,
                                       UriComponentsBuilder uriBuilder) {
        val terminal = mapper.fromResource(terminalResource);
        terminalRepository.add(terminal);

        val uri = uriBuilder.path(Endpoints.TERMINALS_ID).buildAndExpand(terminal.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET, path = TERMINALS_ROOT)
    public ResponseEntity<Collection<TerminalResource>> read() {
        val terminals = terminalRepository.list();
        val terminalResources = mapper.toResources(terminals);

        return ResponseEntity.ok(terminalResources);
    }

    @RequestMapping(method = GET, path = TERMINALS_ID)
    public ResponseEntity<TerminalResource> read(@PathVariable @NotNull TerminalId terminalId) {
        val terminalOpt = terminalRepository.get(terminalId);

        if (!terminalOpt.isPresent())
            return ResponseEntity.notFound().build();

        val terminalResource = mapper.toResource(terminalOpt.get());
        return ResponseEntity.ok(terminalResource);
    }

    @Transactional
    @RequestMapping(method = PATCH, path = TERMINALS_ID)
    public ResponseEntity<Void> update(@RequestBody @NotNull TerminalResource terminalResource,
                                       @PathVariable @NotNull TerminalId terminalId) {
        val terminalOpt = terminalRepository.get(terminalId);

        if (!terminalOpt.isPresent())
            return ResponseEntity.notFound().build();

        val terminal = terminalOpt.get();
        mapper.applyResource(terminalResource, terminal);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = TERMINALS_ID)
    public ResponseEntity<Void> delete(@PathVariable @NotNull TerminalId terminalId) {
        if (!terminalRepository.contains(terminalId))
            return ResponseEntity.notFound().build();

        terminalRepository.remove(terminalId);

        return ResponseEntity.ok().build();
    }
}
