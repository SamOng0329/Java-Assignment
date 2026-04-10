import java.time.LocalDate;
public class VisitRequest {
    public enum VisitStatus {
        PENDING, APPROVED, REJECTED
    }


    private String requestId;
    private Tenant tenant;
    private Property property;
    private LocalDate visitDate;
    private VisitStatus status;
    private Agent agent;


    public VisitRequest(String requestId, Tenant tenant, Agent agent, Property property, LocalDate visitDate) {
        this.requestId = requestId;
        this.tenant = tenant;
        this.agent = agent;
        this.property = property;
        this.visitDate = visitDate;
        this.status = VisitStatus.PENDING; // Default status
    }


    public String aprovalmsg(){
        if (status == VisitStatus.APPROVED) {
            return "Your visit request has been approved.";
        } else if (status == VisitStatus.REJECTED) {
            return "Your visit request has been rejected. Reason: " ;
        } else {
            return "Your visit request is still pending.";
        }       
    }

    public String getRequestId() {
        return requestId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Agent getAgent() {
        return agent ;
    }

    public Property getProperty() {
        return property;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void approvedecision(String landlordReply, boolean isApproved) {
        if (isApproved) {
            this.status = VisitStatus.APPROVED;
            if (landlordReply != null && !landlordReply.isEmpty()) {
                System.out.println("Landlord's message: " + landlordReply);
            }
        } else {
            this.status = VisitStatus.REJECTED;
            if (landlordReply != null && !landlordReply.isEmpty()) {
                System.out.println("Landlord's message: " + landlordReply);
            }
        }
    }
    

    public void displayInfo() {
        System.out.println("  ==========================================");
        System.out.println("  Request ID: " + requestId);
        System.out.println("  Tenant Name: " + tenant.getName());
        System.out.println("  Property Location: " + property.getLocation());
        System.out.println("  Visit Date: " + visitDate);
        System.out.println("  Status: " + status);
    }
    
}
