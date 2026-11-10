package pl.edu.pjatk.mpr.aggregates;

import java.util.Optional;

public interface DayScheduleRepository {
    Optional<DaySchedule> findById(String id);

    void save(DaySchedule schedule);
}
