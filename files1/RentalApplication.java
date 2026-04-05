package files1;

import java.time.LocalDate;

/**
 * Represents a rental application submitted by a Tenant to an Agent.
 *
 * OOP Concepts:
 * - Association : References Tenant, Property, Agent (exist independently)
 * - Encapsulation: All fields are private
 */
public class RentalApplication {

    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED
    }

    private String applicationId;
    private Tenant tenant;
    private Property property;
    private Agent agent;              // Agent handling this application
    private LocalDate desiredMoveIn;
    private double offeredRent;       // Tenant's offered monthly rent
    private String message;
    private ApplicationStatus status;
    private String agentReply;        // Optional reply from agent

    // Constructor
    public RentalApplication(String applicationId, Tenant tenant, Property property,
                              Agent agent, LocalDate desiredMoveIn,
                              double offeredRent, String message) {
        this.applicationId = applicationId;
        this.tenant        = tenant;
        this.property      = property;
        this.agent         = agent;
        this.desiredMoveIn = desiredMoveIn;
        this.offeredRent   = offeredRent;
        this.message       = message;
        this.status        = ApplicationStatus.PENDING;
        this.agentReply    = "";
    }

    // ===================== Business Logic =====================

    public void approve(String reply) {
        this.status     = ApplicationStatus.APPROVED;
        this.agentReply = (reply != null && !reply.isBlank()) ? reply : "Your application has been approved.";
    }

    public void reject(String reply) {
        this.status     = ApplicationStatus.REJECTED;
        this.agentReply = (reply != null && !reply.isBlank()) ? reply : "Your application was unsuccessful.";
    }

    // ===================== Getters =====================

    public String            getApplicationId() { return applicationId; }
    public Tenant            getTenant()        { return tenant; }
    public Property          getProperty()      { return property; }
    public Agent             getAgent()         { return agent; }
    public LocalDate         getDesiredMoveIn() { return desiredMoveIn; }
    public double            getOfferedRent()   { return offeredRent; }
    public String            getMessage()       { return message; }
    public ApplicationStatus getStatus()        { return status; }
    public String            getAgentReply()    { return agentReply; }

    // For loading from database
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public void setAgentReply(String reply)          { this.agentReply = reply; }

    public void displayInfo() {
        System.out.println("  ==========================================");
        System.out.println("  Application ID : " + applicationId);
        System.out.println("  Property       : " + property.getPropertyId() + " (" + property.getLocation() + ")");
        System.out.println("  Tenant         : " + tenant.getName() + " (" + tenant.getPersonId() + ")");
        System.out.println("  Agent          : " + (agent != null ? agent.getName() : "Unassigned"));
        System.out.println("  Move-in Date   : " + desiredMoveIn);
        System.out.println("  Offered Rent   : RM " + String.format("%.2f", offeredRent));
        System.out.println("  Message        : " + (message.isBlank() ? "-" : message));
        System.out.println("  Status         : " + status);
        if (!agentReply.isBlank()) {
            System.out.println("  Agent Reply    : " + agentReply);
        }
        System.out.println("  ==========================================");
    }
}
