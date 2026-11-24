package pl.edu.pjatk.mpr.convention.program.domain;
import java.util.Objects;
public record SpeakerCandidate(ExternalPersonId externalPersonId, DisplayName displayName) {
    public SpeakerCandidate { Objects.requireNonNull(externalPersonId); Objects.requireNonNull(displayName); }
}
