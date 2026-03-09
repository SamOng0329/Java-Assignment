public class UserRole {
    public enum Role {
        LANDLORD,
        TENANT,
        STAFF,
        AGENT
    }

    private String username;
    private String password;
    private Role role;
    private UserInfo linkedPerson;
    private boolean isLogged;

    // Constructor for staff since they dont have userInfo
    public UserRole(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedPerson = null;
        this.isLogged = false; // Default case
    }

    // Constructor for landlord, tenant and agent since they have userInfo
    public UserRole(String username, String password, Role role, UserInfo linkedPerson) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedPerson = linkedPerson;
        this.isLogged = false; // Default case
    }

    // Password checker
    public boolean login(String enteredPassword) {
        if (this.password.equals(enteredPassword))
            ;
        this.isLogged = true;
        return true;
    }

    // Function for user logout
    public void logout() {
        this.isLogged = false;
    }

    // Getter
    // ====================================================================================================
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public UserInfo getLinkedPerson() {
        return linkedPerson;
    }

    public boolean isLoggedIn() {
        return isLogged;
    }
    // ====================================================================================================

    // Setter - Password checker
    // ====================================================================================================
    public void setPassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 4){
        throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }
        this.password = newPassword;
    }
    // ====================================================================================================

    // Display Info
    // ====================================================================================================
    public void displayInfo() {
        System.out.printf("%s15 : %s15", "Username", username);
        System.out.printf("%s15 : %s15", "Role", role);
        if (linkedPerson != null) {
            System.out.printf("%s15 : %s15", "Linked To", linkedPerson.getName());
            System.out.printf("%s15 : %s15", "Person ID", linkedPerson.getPersonId());
        }
    }
    // ====================================================================================================
}
