package pl.edu.pjatk.mpr.scheduling;
import org.junit.jupiter.api.Test; import java.util.Collections;
import static org.hamcrest.MatcherAssert.assertThat; import static org.hamcrest.Matchers.startsWith;
class ProgramServiceTest {
    private final ProgramService service = new ProgramService();
    @Test void processesData() { assertThat(service.process("host-7", Collections.singletonList("panel:main-hall:600")), startsWith("OK:")); }
    @Test void returnsErrorForNoData() { assertThat(service.process("host-7", Collections.emptyList()), startsWith("ERROR:")); }
}
