package files1;

/**
 * Abstract base class for all persons in the system.
 *
 * OOP Concepts:
 * - Abstraction: Cannot be instantiated directly
 * - Encapsulation: All attributes are private with getters/setters
 * - Polymorphism: getRole() is overridden by subclasses
 */
public abstract class UserInfo {

    private String personId;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public UserInfo(String personId, String name, String email, String phone) {
        this.personId = personId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Abstract method - must be implemented by subclasses (Polymorphism)
    public abstract String getRole();

    // ===================== Getters & Setters =====================

    public String getPersonId() { return personId; }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Display basic info (can be extended by subclasses)
    public void displayInfo() {
        System.out.println("  ID      : " + personId);
        System.out.println("  Name    : " + name);
        System.out.println("  Role    : " + getRole());
        System.out.println("  Email   : " + email);
        System.out.println("  Phone   : " + phone);
    }
}
