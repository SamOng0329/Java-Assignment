package Assignment;

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

    // Getters
    public String getPersonId() {
        return personId;
    } // read only no needed setter

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Display the information
    public void displayInfo() {
        System.out.println("ID: " + personId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}
