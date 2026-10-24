package pl.edu.pjatk.mpr.solid.devices;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.solid.domain.Receipt;

class InterfaceSegregationTest {
  @Test
  void simplePrinterOnlyImplementsPrintingRole() {
    ReceiptPrinter printer = new SimpleReceiptPrinter();

    printer.print(new Receipt("ORD-1", BigDecimal.TEN));

    assertThat(printer, not(instanceOf(ReceiptScanner.class)));
  }
}
