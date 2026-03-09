import java.time.LocalDate;

public class PaymentRecord {
    
    private String paymentId;
    private LeaseRecord leaseRecord;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private boolean isPaid;
    
    // Constructor
    public PaymentRecord(String paymentId, LeaseRecord leaseRecord, double amount, LocalDate paymentDate,
            String paymentMethod, boolean isPaid) {
        this.paymentId = paymentId;
        this.leaseRecord = leaseRecord;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.isPaid = isPaid;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public LeaseRecord getLeaseRecord() {
        return leaseRecord;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    // No setter due to it is only for reads 

    // Display Info
    public void displayInfo() {
        System.out.println("  ==========================================");
        System.out.printf("%s15 : %s5", "Payment ID", paymentId);
        System.out.printf("%s15 : %s5", "Lease Record", leaseRecord.getLeaseId());
        System.out.printf("%s15 : %s5", "Amount", String.format("RM %.2f", amount));
        System.out.printf("%s15 : %s5", "Payment Date", paymentDate);
        System.out.printf("%s15 : %s5", "Payment Method", paymentMethod);
        System.out.printf("%s15 : %s5", "Paid", (isPaid ? "Yes" : "No"));
        System.out.println("  ==========================================");
    }
}
