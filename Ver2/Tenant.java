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

    public  String getTenantId() {
        return super.getPersonId();
    }





  
    
    
    public String toString() {
        return super.toString()
                + String.format("| %-16s : %s%n", "IC Number",       icNumber)
                + String.format("| %-16s : %s%n", "Overdue Payment", overduePayment ? "Yes" : "No")
                + "=".repeat(40) + "\n";
    }


}
