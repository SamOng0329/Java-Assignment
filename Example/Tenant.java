package files1;

/**
 * Represents a Tenant (renter) in the system.
 *
 * OOP Concepts:
 * - Inheritance: Extends Person
 * - Encapsulation: icNumber and overduePayment are private
 */
public class Tenant extends UserInfo {

    private String icNumber;
    private boolean hasOverduePayment;

    // Constructor
    public Tenant(String tenantId, String name, String email, String phone, String icNumber) {
        super(tenantId, name, email, phone); // Call parent constructor
        this.icNumber = icNumber;
        this.hasOverduePayment = false;
    }

    // Implement abstract method from Person (Polymorphism)
    @Override
    public String getRole() {
        return "Tenant";
    }

    // ===================== Getters & Setters =====================

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public boolean isHasOverduePayment() {
        return hasOverduePayment;
    }

    public void setHasOverduePayment(boolean hasOverduePayment) {
        this.hasOverduePayment = hasOverduePayment;
    }

    // Override displayInfo to add tenant-specific fields
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent's displayInfo first
        System.out.println("  IC No.  : " + icNumber);
        System.out.println("  Overdue : " + (hasOverduePayment ? "⚠ YES - Overdue Payment!" : "No"));
    }
}
