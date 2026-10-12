package pl.edu.pjatk.mpr.orders;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class SpyExampleTest {

    @Test
    void spy_callsRealMethodsUnlessStubbed() {
        List<String> auditLog = spy(new ArrayList<>());

        auditLog.add("payment approved");

        assertThat(auditLog, contains("payment approved"));
        verify(auditLog).add("payment approved");
    }

    @Test
    void spy_stubbingUsesDoReturnSyntaxSafely() {
        List<String> auditLog = spy(new ArrayList<>());
        doReturn(42).when(auditLog).size();

        assertThat(auditLog, hasSize(42));
    }
}
