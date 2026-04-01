//package Assignment;

public class Tenant extends UserInfo {
    private String icNumber;
    private boolean overduePayment;

    // constructor
    public Tenant(String tenantId, String name, String email, String phone, String icNumber) {
        super(tenantId, name, email, phone);
        this.icNumber = icNumber;
        this.overduePayment = false;
    }

    @Override
    public String getRole() {
        return "Tenant";
    }

    // setter
    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public void setOverduePayment(boolean overduePayment) {
        this.overduePayment = overduePayment;
    }

    // getter
    public String getIcNumber() {
        return icNumber;
    }

    public boolean getOverduePayment() {
        return overduePayment;
    }


  
    
    
    // display user information
    public String toString() {
        return super.toString()
                + String.format("%s7 %s\n", "IC Number", icNumber)
                + String.format("%s7 %s\n", "Overdue Payment", overduePayment);
    }
}
