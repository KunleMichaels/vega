package eu.socialedge.vega.backend.account.application.rest.passenger;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.account.domain.PassengerRepository;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.payment.domain.Token;
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
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerEntityMapper passengerEntityMapper;

    @Autowired
    private PassengerRepository passengerRepository;

    @Transactional
    @RequestMapping(method = POST)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid PassengerResource passengerResource) {
        val passenger = passengerEntityMapper.fromResource(passengerResource);
        passengerRepository.add(passenger);

        val uri = linkTo(getClass()).slash(passenger.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<PassengerResource>> read() {
        val passengers = passengerRepository.listActive();

        val passengerResources = passengerEntityMapper.toResources(passengers);
        return ResponseEntity.ok(passengerResources);
    }

    @RequestMapping(method = GET, path = "/{passengerId}")
    public ResponseEntity<PassengerResource> read(@PathVariable @NotNull PassengerId passengerId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passengerResource = passengerEntityMapper.toResource(passengerOpt.get());
        return ResponseEntity.ok(passengerResource);
    }

    @Transactional
    @RequestMapping(method = PUT, path = "/{passengerId}")
    public ResponseEntity<Void> update(@PathVariable @NotNull PassengerId passengerId,
                                       @RequestBody @NotNull @Valid PassengerResource passengerResource) {

        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passenger = passengerOpt.get();

        passenger.name(passengerResource.name());
        passenger.email(passengerResource.email());
        passenger.password(passengerResource.password());

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = DELETE, path = "/{passengerId}")
    public ResponseEntity<Void> deactivate(@PathVariable @NotNull PassengerId passengerId) {
        if (!passengerRepository.contains(passengerId)) {
            return ResponseEntity.notFound().build();
        }

        passengerRepository.deactivate(passengerId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = GET, path = "/{passengerId}/tokens")
    public ResponseEntity<Collection<Token>> tokens(@PathVariable @NotNull PassengerId passengerId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passenger = passengerOpt.get();

        return ResponseEntity.ok(passenger.paymentTokens());
    }

    @Transactional
    @RequestMapping(method = POST, path = "/{passengerId}/tags")
    public ResponseEntity<Void> addTag(@PathVariable @NotNull PassengerId passengerId,
                                       @RequestBody @NotNull TagId tagId) {

        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passenger = passengerOpt.get();

        passenger.addTagId(tagId);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = "/{passengerId}/tags/{tagId}")
    public ResponseEntity<Void> removeTag(@PathVariable @NotNull PassengerId passengerId,
                                          @PathVariable @NotNull TagId tagId) {

        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passenger = passengerOpt.get();

        passenger.removeTagId(tagId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = GET, path = "/{passengerId}/tags")
    public ResponseEntity<Collection<TagId>> tags(@PathVariable @NotNull PassengerId passengerId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passenger = passengerOpt.get();

        return ResponseEntity.ok(passenger.tagIds());
    }
}