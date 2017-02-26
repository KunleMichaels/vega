package eu.socialedge.vega.backend.fare.application.api.fare;

import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import eu.socialedge.vega.backend.fare.domain.Deduction;

public class DeductionResourceMapper implements ResourceMapper<Deduction, DeductionResource> {

    @Override
    public DeductionResource toResource(Deduction entity) {
        return new DeductionResource(entity.description(), entity.multiplier().doubleValue());
    }

    @Override
    public Deduction fromResource(DeductionResource resource) {
        return new Deduction(resource.description(), resource.multiplier());
    }
}
