package files1;

import java.time.LocalDate;

/**
 * Represents a monthly rent payment linked to a lease.
 *
 * OOP Concepts:
 * - Association: References a LeaseRecord (exists independently)
 * - Encapsulation: All fields are private
 */
public class PaymentRecord {

    private String paymentId;
    private LeaseRecord lease;      // Association
    private double amountPaid;
    private LocalDate paymentDate;
    private String paymentMethod;
    private boolean isLatePayment;

    // Constructor
    public PaymentRecord(String paymentId, LeaseRecord lease, double amountPaid,
                         LocalDate paymentDate, String paymentMethod, boolean isLatePayment) {
        this.paymentId = paymentId;
        this.lease = lease;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.isLatePayment = isLatePayment;
    }

    // ===================== Getters =====================

    public String getPaymentId() { return paymentId; }
    public LeaseRecord getLease() { return lease; }
    public double getAmountPaid() { return amountPaid; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isLatePayment() { return isLatePayment; }

    public void displayInfo() {
        System.out.println("  [" + paymentId + "] "
                + paymentDate + " | RM " + String.format("%.2f", amountPaid)
                + " | " + paymentMethod
                + (isLatePayment ? " | *** LATE ***" : ""));
    }
}
