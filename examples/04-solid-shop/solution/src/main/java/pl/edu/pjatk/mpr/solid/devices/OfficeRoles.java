package pl.edu.pjatk.mpr.solid.devices;

import pl.edu.pjatk.mpr.solid.domain.Receipt;

interface ReceiptPrinter {
  void print(Receipt receipt);
}

interface ReceiptScanner {
  Receipt scan();
}

final class SimpleReceiptPrinter implements ReceiptPrinter {
  @Override
  public void print(Receipt receipt) {
    if (receipt == null) throw new IllegalArgumentException("Receipt is required");
  }
}

final class MultifunctionDevice implements ReceiptPrinter, ReceiptScanner {
  @Override
  public void print(Receipt receipt) {
    if (receipt == null) throw new IllegalArgumentException("Receipt is required");
  }

  @Override
  public Receipt scan() {
    return new Receipt("SCANNED", java.math.BigDecimal.ZERO);
  }
}
