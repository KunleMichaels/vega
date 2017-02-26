package eu.socialedge.vega.backend.fare.application.api.fare;

import eu.socialedge.vega.backend.application.api.resource.ResourceApplier;
import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import eu.socialedge.vega.backend.fare.domain.Deduction;
import eu.socialedge.vega.backend.fare.domain.Fare;
import eu.socialedge.vega.backend.geo.domain.Zone;
import lombok.val;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;

import static java.util.Objects.nonNull;

@Component
public class FareResourceMapper
        implements ResourceMapper<Fare, FareResource>, ResourceApplier<Fare, FareResource> {

    private static final DeductionResourceMapper deductionResourceMapper = new DeductionResourceMapper();

    @Override
    public FareResource toResource(Fare entity) {
        val resId = entity.id();
        val price = entity.price().toString();
        val validity = entity.validity().toString();
        val deductions = new HashSet<DeductionResource>(deductionResourceMapper.toResources(entity.deductions()));
        val vehicleTypes = entity.vehicleTypes();
        val vertices = entity.zone().vertices();
        val operatorIds = entity.operatorIds();

        return FareResource.builder()
            .id(resId)
            .price(price)
            .validity(validity)
            .deductions(deductions)
            .vehicleTypes(vehicleTypes)
            .vertices(vertices)
            .operatorIds(operatorIds)
            .build();
    }

    @Override
    public Fare fromResource(FareResource resource) {
        val price = Money.parse(resource.price());
        val validity = Period.parse(resource.validity());
        val vehicleTypes = resource.vehicleTypes();
        val vertices = new Zone(new ArrayList<>(resource.vertices()));
        val operatorIds = resource.operatorIds();
    
        if (nonNull(resource.deductions())) {
            val deductions = new HashSet<Deduction>(deductionResourceMapper.fromResources(resource.deductions()));
            return new Fare(price, deductions, validity, vehicleTypes, vertices, operatorIds);
        } else
            return new Fare(price, validity, vehicleTypes, vertices, operatorIds);
    }

    @Override
    public void applyResource(FareResource resource, Fare entity) {
        val price = resource.price();
        val deductions = resource.deductions();
        val vehicleTypes = resource.vehicleTypes();
        val operatorIds = resource.operatorIds();

        if (nonNull(price)) entity.price(Money.parse(price));
        if (nonNull(resource.vertices())) entity.zone(new Zone(new ArrayList<>(resource.vertices())));
        if (nonNull(resource.validity())) entity.validity(Period.parse(resource.validity()));

        if (nonNull(deductions)) {
            entity.removeDeductions();
            deductions.stream()
                .map(deductionResourceMapper::fromResource)
                .forEach(entity::addDeduction);
        }

        if (nonNull(vehicleTypes)) {
            entity.removeVehicleTypes();
            vehicleTypes.forEach(entity::addVehicleType);
        }

        if (nonNull(operatorIds)) {
            entity.removeOperatorIds();
            operatorIds.forEach(entity::addOperatorId);
        }
    }
}
