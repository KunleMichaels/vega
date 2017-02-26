package eu.socialedge.vega.backend.terminal.application.api.terminal;

import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import eu.socialedge.vega.backend.terminal.domain.Installation;

public class InstallationResourceMapper implements ResourceMapper<Installation, InstallationResource> {

    @Override
    public InstallationResource toResource(Installation entity) {
        return new InstallationResource(entity.operatorId(), entity.vehicleType(), entity.vehicleIdentifier());
    }

    @Override
    public Installation fromResource(InstallationResource resource) {
        return new Installation(resource.operatorId(), resource.vehicleType(), resource.vehicleIdentifier());
    }
}
