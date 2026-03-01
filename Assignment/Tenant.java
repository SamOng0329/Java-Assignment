package Assignment;

public class Tenant {
    private String icNumber;
    private boolean overduePayment;
    private UserInfo userInfo;
    
    //  constructor
    public Tenant(String icNumber, boolean overduePayment, UserInfo userInfo){
        this.icNumber = icNumber;
        this.overduePayment = overduePayment;
        this.userInfo = userInfo;
    }

    //  setter
    public void setIcNumber(String icNumber){
        this.icNumber = icNumber;
    }

    public void setOverduePayment(boolean overduePayment){
        this.overduePayment = overduePayment;
    }

    //   getter
    public String getIcNumber(){
        return icNumber;
    }
    public boolean getOverduePayment(){
        return overduePayment;
    }
    
    // display user information
    public void displayInfo(){
        System.out.println("IC Number: " + icNumber);
        userInfo.displayInfo();
    }

} 
