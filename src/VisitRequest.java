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

        public void approvedecision(boolean isApproved) {
        if(VisitStatus.PENDING != this.status){
            System.out.println("This request has already been processed.");
            return;
        }
        if (isApproved) {
            this.status = VisitStatus.APPROVED;
        } else {
            this.status = VisitStatus.REJECTED;
        }
    }


    public void displayInfo() {
        System.out.println("=".repeat(40));
        System.out.println("| Request ID       : " + requestId);
        System.out.println("| Tenant           : " + tenant.getName());
        System.out.println("| Location         : " + property.getLocation());
        System.out.println("| Visit Date       : " + visitDate);
        System.out.println("| Status           : " + status);
        System.out.println("=".repeat(40));
}
    
}
