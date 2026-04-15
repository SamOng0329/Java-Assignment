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
        System.out.println("==========================================");
        System.out.printf("%-15s : %5s\n", "Payment ID", paymentId);
        System.out.printf("%-15s : %5s\n", "Lease Record", leaseRecord.getLeaseId());
        System.out.printf("%-15s : %5s\n", "Amount", String.format("RM %.2f", amount));
        System.out.printf("%-15s : %5s\n", "Payment Date", paymentDate);
        System.out.printf("%-15s : %5s\n", "Payment Method", paymentMethod);
        System.out.printf("%-15s : %5s\n", "Paid", (isPaid ? "Yes" : "No"));
        System.out.println("==========================================");
    }
}
