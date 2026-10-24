package pl.edu.pjatk.mpr.scheduling;
import java.util.List;
public final class ProgramService {
    public String process(String person, List<String> data) { return data == null || data.isEmpty() ? "ERROR:EMPTY" : "OK:" + person; }
}
