package Assignment;

public class Tenant {
    private String icNumber;
    private boolean overduePayment;
    private UserInfo userInfo;
    
    //  constructor
    public Tenant(String icNumber, UserInfo userInfo){
        this.icNumber = icNumber;
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
    // display user information
    public void displayInfo(){
        System.out.println("IC Number: " + icNumber);
        userInfo.displayInfo();
    }

} 
