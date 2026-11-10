package pl.edu.pjatk.mpr.aggregates;
import java.time.LocalDate;
public final class SchedulePublished { private final String scheduleId;private final LocalDate date;private final int entryCount; SchedulePublished(String id,LocalDate date,int count){this.scheduleId=id;this.date=date;this.entryCount=count;} public String scheduleId(){return scheduleId;}public LocalDate date(){return date;}public int entryCount(){return entryCount;} }
