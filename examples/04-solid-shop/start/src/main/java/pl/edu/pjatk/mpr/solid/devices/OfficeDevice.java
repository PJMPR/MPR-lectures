package pl.edu.pjatk.mpr.solid.devices;

import pl.edu.pjatk.mpr.solid.domain.Receipt;

// KROK 4 (ISP): implementacje są zmuszane do obsługi zbędnych metod.
public interface OfficeDevice {
  void print(Receipt receipt);

  Receipt scan();

  void fax(Receipt receipt);
}

final class ReceiptPrinter implements OfficeDevice {
  @Override
  public void print(Receipt receipt) {}

  @Override
  public Receipt scan() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void fax(Receipt receipt) {
    throw new UnsupportedOperationException();
  }
}

// Propozycja rozwiązania: interfejsy powinny opisywać role potrzebne klientom.
// interface ReceiptPrinter {
//   void print(Receipt receipt);
// }
//
// interface ReceiptScanner {
//   Receipt scan();
// }
//
// final class MultifunctionDevice implements ReceiptPrinter, ReceiptScanner { ... }
