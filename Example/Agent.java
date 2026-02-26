package Example;

/**
 * Represents a Real Estate Agent in the system.
 *
 * OOP Concepts:
 * - Inheritance: Extends Person
 * - Encapsulation: licenseNumber and deals are private
 */
public class Agent extends UserInfo {

    private String licenseNumber;
    private int totalDealsCompleted;

    // Constructor
    public Agent(String agentId, String name, String email, String phone, String licenseNumber) {
        super(agentId, name, email, phone); // Call parent constructor
        this.licenseNumber = licenseNumber;
        this.totalDealsCompleted = 0;
    }

    // Implement abstract method from Person (Polymorphism)
    @Override
    public String getRole() {
        return "Agent";
    }

    // ===================== Getters & Setters =====================

    public String getLicenseNumber() { return licenseNumber; }

    public int getTotalDealsCompleted() { return totalDealsCompleted; }

    // Called when a lease is successfully created by this agent
    public void incrementDeals() {
        this.totalDealsCompleted++;
    }

    // Override displayInfo to add agent-specific fields
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent's displayInfo first
        System.out.println("  License : " + licenseNumber);
        System.out.println("  Deals   : " + totalDealsCompleted);
    }
}
