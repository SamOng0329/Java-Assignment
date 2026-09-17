public class Agent extends UserInfo {

    private int totalDealsCompleted;

    // Constructor
    public Agent(String StaffId, String Name, String Email, String Phone) {
        super(StaffId, Name, Email, Phone);
        this.totalDealsCompleted = 0;
    }

    // Implement abstract method from UserInfo
    @Override
    public String getRole() {
        return "Agent";
    }

    // Getter
    public int getTotalDealsCompleted() {
        return totalDealsCompleted;
    }

    // Function of adding Deals to agent
    public void addDeal(){
        this.totalDealsCompleted++;
    }

    @Override
    public String toString(){
        return super.toString()
                + String.format("| %-16s : %s%n", "Deals", totalDealsCompleted)
                + "=".repeat(40) + "\n";
    }
}
