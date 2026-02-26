package Example;

/**
 * Represents a system user with login credentials and a role.
 * 
 * OOP Concepts:
 * - Encapsulation: username and password are private
 * - Association: linked to a Person object (Agent or Tenant)
 */
public class User {

    // Enum for user roles
    public enum Role {
        ADMIN, AGENT, TENANT
    }

    // Private attributes (Encapsulation)
    private String username;
    private String password;  // In real systems, this would be hashed
    private Role role;
    private UserInfo linkedPerson; // Can be Agent or Tenant (null for Admin)
    private boolean isLoggedIn;

    // Constructor for Admin (no linked person needed)
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedPerson = null;
        this.isLoggedIn = false;
    }

    // Constructor for Agent/Tenant (linked to a Person)
    public User(String username, String password, Role role, UserInfo linkedPerson) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedPerson = linkedPerson;
        this.isLoggedIn = false;
    }

    // ===================== Login Logic =====================

    /**
     * Checks if the entered password matches the stored password.
     * Returns true if login is successful.
     */
    public boolean login(String enteredPassword) {
        if (this.password.equals(enteredPassword)) {
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    // ===================== Getters =====================

    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public UserInfo getLinkedPerson() { return linkedPerson; }
    public boolean isLoggedIn() { return isLoggedIn; }

    // Setter - allow password change
    public void setPassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        this.password = newPassword;
    }

    public void displayInfo() {
        System.out.println("Username : " + username);
        System.out.println("Role     : " + role);
        if (linkedPerson != null) {
            System.out.println("Linked To: " + linkedPerson.getName() + " (" + linkedPerson.getPersonId() + ")");
        }
    }
}
