package Example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a rental lease agreement between a tenant and a property.
 *
 * OOP Concepts:
 * - Composition: Contains Property and Tenant (tightly bound, created together)
 * - Association: References an Agent (optional, not owned by this class)
 * - Encapsulation: All fields are private
 */
public class LeaseRecord {

    private String leaseId;
    private Property property;    // Composition - lease needs a property to exist
    private Tenant tenant;        // Composition - lease needs a tenant to exist
    private Agent agent;          // Association - agent is optional, exists independently
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlyRent;
    private double depositAmount; // Auto-calculated = 2 months rent
    private boolean isActive;

    // Constructor
    public LeaseRecord(String leaseId, Property property, Tenant tenant, Agent agent,
                       LocalDate startDate, LocalDate endDate, double monthlyRent) {

        // Validate dates
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        this.leaseId = leaseId;
        this.property = property;
        this.tenant = tenant;
        this.agent = agent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
        this.depositAmount = monthlyRent * 2; // Auto-calculate deposit
        this.isActive = true;

        // Update property status to RENTED
        property.setStatus(Property.PropertyStatus.RENTED);

        // Credit the agent for this deal
        if (agent != null) {
            agent.incrementDeals();
        }
    }

    // ===================== Business Logic Methods =====================

    /**
     * Terminates this lease and frees up the property.
     */
    public void terminateLease() {
        this.isActive = false;
        property.setStatus(Property.PropertyStatus.AVAILABLE);
    }

    /**
     * Returns true if today is past the lease end date and lease is still active.
     */
    public boolean isOverdue() {
        return isActive && LocalDate.now().isAfter(endDate);
    }

    /**
     * Returns number of days remaining until lease ends (negative if overdue).
     */
    public long getRemainingDays() {
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }

    // ===================== Getters =====================

    public String getLeaseId() { return leaseId; }
    public Property getProperty() { return property; }
    public Tenant getTenant() { return tenant; }
    public Agent getAgent() { return agent; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getMonthlyRent() { return monthlyRent; }
    public double getDepositAmount() { return depositAmount; }
    public boolean isActive() { return isActive; }

    public void displayInfo() {
        System.out.println("  ==========================================");
        System.out.println("  Lease ID     : " + leaseId);
        System.out.println("  Property     : " + property.getPropertyId() + " (" + property.getLocation() + ")");
        System.out.println("  Tenant       : " + tenant.getName() + " (" + tenant.getPersonId() + ")");
        System.out.println("  Agent        : " + (agent != null ? agent.getName() : "None"));
        System.out.println("  Start Date   : " + startDate);
        System.out.println("  End Date     : " + endDate);
        System.out.println("  Monthly Rent : RM " + String.format("%.2f", monthlyRent));
        System.out.println("  Deposit Paid : RM " + String.format("%.2f", depositAmount));
        System.out.println("  Status       : " + (isActive ? (isOverdue() ? "OVERDUE ⚠" : "Active") : "Terminated"));
        System.out.println("  ==========================================");
    }
}
