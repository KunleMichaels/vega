package eu.socialedge.vega.backend.account.application.rest.operator;

import eu.socialedge.vega.backend.account.domain.Operator;
import eu.socialedge.vega.backend.application.rest.EntityResourceMapper;
import org.springframework.stereotype.Component;

@Component
public class OperatorEntityMapper extends EntityResourceMapper<Operator, OperatorResource> {

    public OperatorEntityMapper() {
        super(OperatorController.class, Operator.class, OperatorResource.class);
    }
}
