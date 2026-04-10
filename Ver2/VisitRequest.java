import java.time.LocalDate;
public class VisitRequest {
    public enum VisitStatus {
        PENDING, APPROVED, REJECTED
    }


    private String requestId;
    private String tenantId;
    private String propertyId;
    private LocalDate visitDate;
    private VisitStatus status;


    public VisitRequest(String requestId, String tenantId, String propertyId, LocalDate visitDate) {
        this.requestId = requestId;
        this.tenantId = tenantId;
        this.propertyId = propertyId;
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

    public String getTenantId() {
        return tenantId;
    }

    public String getPropertyId() {
        return propertyId;
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
    
    
}
