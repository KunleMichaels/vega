package eu.socialedge.vega.backend.terminal.application.api.terminal;

import eu.socialedge.vega.backend.application.api.resource.ResourceApplier;
import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import eu.socialedge.vega.backend.terminal.domain.Build;
import eu.socialedge.vega.backend.terminal.domain.Terminal;
import lombok.val;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class TerminalResourceMapper
        implements ResourceMapper<Terminal, TerminalResource>, ResourceApplier<Terminal, TerminalResource> {

    private static final InstallationResourceMapper installationMapper = new InstallationResourceMapper();

    @Override
    public Terminal fromResource(TerminalResource resource) {
        val build =  new Build(resource.buildNumber());
        val installation = installationMapper.fromResource(resource.installationResource());

        return new Terminal(build, installation);
    }

    @Override
    public TerminalResource toResource(Terminal entity) {
        val resId = entity.id();
        val buildNumber = entity.getBuild().number();

        val installation = entity.getInstallation();
        val operatorId = installation.operatorId();
        val vehicleType = installation.vehicleType();
        val vehicleIdentifier = installation.vehicleIdentifier();
        val installationResource = new InstallationResource(operatorId, vehicleType, vehicleIdentifier);

        return new TerminalResource(resId, buildNumber, installationResource);
    }

    @Override
    public void applyResource(TerminalResource resource, Terminal entity) {
        if (nonNull(resource.buildNumber())) {
            entity.setBuild(new Build(resource.buildNumber()));
        }

        if (nonNull(resource.installationResource())) {
            entity.setInstallation(installationMapper.fromResource(resource.installationResource()));
        }
    }
}
