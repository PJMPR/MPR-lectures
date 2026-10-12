package pl.edu.pjatk.mpr.orders;

public final class PaymentResult {
    private final boolean successful;
    private final String transactionId;

    private PaymentResult(boolean successful, String transactionId) {
        this.successful = successful;
        this.transactionId = transactionId;
    }

    public static PaymentResult approved(String transactionId) {
        return new PaymentResult(true, transactionId);
    }

    public static PaymentResult declined() {
        return new PaymentResult(false, null);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
