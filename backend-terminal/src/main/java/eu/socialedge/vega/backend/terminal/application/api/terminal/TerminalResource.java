package eu.socialedge.vega.backend.terminal.application.api.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Builder
@Getter @Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class TerminalResource {

    private TerminalId id;

    @NotNull(message = "Build number can not be null")
    private Long buildNumber;

    @Valid
    @JsonProperty("installation")
    private InstallationResource installationResource;
}
