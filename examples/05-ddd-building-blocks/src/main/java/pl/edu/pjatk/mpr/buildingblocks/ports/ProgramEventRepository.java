package pl.edu.pjatk.mpr.buildingblocks.ports;

import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEvent;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEventId;

import java.util.Optional;

public interface ProgramEventRepository {
    Optional<ProgramEvent> findById(ProgramEventId id);
    void save(ProgramEvent event);
}
