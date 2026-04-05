package files1;

import java.time.LocalDate;

/**
 * Represents a property visit request submitted by a Tenant to a Landlord.
 *
 * OOP Concepts:
 * - Association : References Tenant, Property (exist independently)
 * - Encapsulation: All fields are private
 */
public class VisitRequest {

    public enum VisitStatus {
        PENDING, APPROVED, REJECTED
    }

    private String requestId;
    private Tenant tenant;
    private Property property;
    private LocalDate preferredDate;
    private String message;
    private VisitStatus status;
    private String landlordReply; // Optional reply from landlord

    // Constructor
    public VisitRequest(String requestId, Tenant tenant, Property property,
                        LocalDate preferredDate, String message) {
        this.requestId     = requestId;
        this.tenant        = tenant;
        this.property      = property;
        this.preferredDate = preferredDate;
        this.message       = message;
        this.status        = VisitStatus.PENDING;
        this.landlordReply = "";
    }

    // ===================== Business Logic =====================

    public void approve(String reply) {
        this.status        = VisitStatus.APPROVED;
        this.landlordReply = (reply != null && !reply.isBlank()) ? reply : "Your visit has been approved.";
    }

    public void reject(String reply) {
        this.status        = VisitStatus.REJECTED;
        this.landlordReply = (reply != null && !reply.isBlank()) ? reply : "Your visit request was declined.";
    }

    // ===================== Getters =====================

    public String      getRequestId()     { return requestId; }
    public Tenant      getTenant()        { return tenant; }
    public Property    getProperty()      { return property; }
    public LocalDate   getPreferredDate() { return preferredDate; }
    public String      getMessage()       { return message; }
    public VisitStatus getStatus()        { return status; }
    public String      getLandlordReply() { return landlordReply; }

    // For loading from database
    public void setStatus(VisitStatus status)        { this.status = status; }
    public void setLandlordReply(String reply)        { this.landlordReply = reply; }

    public void displayInfo() {
        System.out.println("  ==========================================");
        System.out.println("  Request ID     : " + requestId);
        System.out.println("  Property       : " + property.getPropertyId() + " (" + property.getLocation() + ")");
        System.out.println("  Tenant         : " + tenant.getName() + " (" + tenant.getPersonId() + ")");
        System.out.println("  Preferred Date : " + preferredDate);
        System.out.println("  Message        : " + (message.isBlank() ? "-" : message));
        System.out.println("  Status         : " + status);
        if (!landlordReply.isBlank()) {
            System.out.println("  Landlord Reply : " + landlordReply);
        }
        System.out.println("  ==========================================");
    }
}
