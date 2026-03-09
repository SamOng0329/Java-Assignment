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

    // Get user's role
    public abstract String getRole();

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

    public void setinfo(String name, String phone, String personId) {
        if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty() || personId == null
                || personId.trim().isEmpty()) {
            System.out.println("Error");
            return;
        } else {
            this.name = name;
            this.phone = phone;
            this.personId = personId;
        }
    }

    // Display the information
    public void displayInfo() {
        System.out.printf("%s7 %s", "ID", personId);
        System.out.printf("%s7 %s", "Name", name);
        System.out.printf("%s7 %s", "Email", email);
        System.out.printf("%s7 %s", "Phone", phone);

    }

    public void getInfobyId(String personId) {
        if (this.personId.equals(personId)) {
            displayInfo();
        }
    }
}
