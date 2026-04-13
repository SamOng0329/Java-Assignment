import java.time.LocalDate;

public class LeaseRecord {

    private String leaseId;
    private Property property; // creating lease record needs property to exist
    private Tenant tenant; // same with property need a tenant to exist
    private Agent agent; // Agent is optional
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlyRent;
    private double deposit;
    private boolean leaseStatus; // false = active, true = terminated

    public LeaseRecord(String leaseId, Property property, Agent agent, Tenant tenant, LocalDate startDate,
            LocalDate endDate, double monthlyRent, double deposit, boolean leaseStatus) {

        // Validation
        if (leaseId == null || property == null || tenant == null || startDate == null || endDate == null
                || monthlyRent <= 0) {
            throw new IllegalArgumentException("Invalid lease record data.");
        }

        this.leaseId = leaseId;
        this.property = property;
        this.agent = agent;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
        this.deposit = monthlyRent * 2; // deposit is 2 months rent
        this.leaseStatus = leaseStatus;

        // Update the status of the property
        if (this.leaseStatus == false){// only update if the lease is active
        property.setStatus(Property.PropertyStatus.RENTED);}
        else {
            property.setStatus(Property.PropertyStatus.AVAILABLE);
        }

        if (agent != null) {
            agent.addDeal();
        }
    }

    // Update the status of lease termination/free up
    public void terminationLease() {
        this.leaseStatus = true; // true = terminated
    }

    public void acceptLease() {
        this.leaseStatus = false; // false = active
    }

    // Getters
    public String getLeaseId() {
        return leaseId;
    }

    public Property getProperty() {
        return property;
    }

    public Agent getAgent() {
        return agent;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public double getDeposit() {
        return deposit;
    }

    public boolean getLeaseStatus() {
        return leaseStatus;
    }


    public void setLeasestatus(boolean leaseStatus) {
        this.leaseStatus = leaseStatus;
        
    }

    // No setter due to it is only for reads 

    // Display Info
    public void displayInfo() {
        System.out.println("==========================================");
        System.out.printf("%-15s : %5s\n", "Lease ID", leaseId);
        System.out.printf("%-15s : %5s\n", "Property", property.getPropertyId() + " (" + property.getLocation() + ")");
        System.out.printf("%-15s : %5s\n", "Tenant", tenant.getName() + " (" + tenant.getPersonId() + ")");
        System.out.printf("%-15s : %5s\n", "Agent", (agent != null ? agent.getName() : "None"));
        System.out.printf("%-15s : %5s\n", "Start Date", startDate);
        System.out.printf("%-15s : %5s\n", "End Date", endDate);
        System.out.printf("%-15s : %5s\n", "Monthly Rent", String.format("RM %.2f", monthlyRent));
        System.out.printf("%-15s : %5s\n", "Deposit", String.format("RM %.2f", deposit));
        System.out.printf("%-15s : %5s\n", "Status", (getLeaseStatus() == true ? "Unactive" : "Active"));
        System.out.println("==========================================");
    }
}
