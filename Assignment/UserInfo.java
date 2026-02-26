package Assignment;

public class UserInfo {

    private String personId;
    private String password;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public UserInfo(String personId, String password, String name, String email, String phone) {
        this.personId = personId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getPersonId() {
        return personId;
    } // read only no needed setter

    public String getPassword() {
        return password;
    } 

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


    public void setinfo(String name, String password, String phone, String personId) {
        if (name == null || name.trim().isEmpty() || password == null || password.trim().isEmpty() || phone == null || phone.trim().isEmpty() || personId == null || personId.trim().isEmpty()) {
            System.out.println("Error");
            return;
        } else {
            this.name = name;
            this.password = password;
            this.phone = phone;
            this.personId = personId;
        }
    }

    // Display the information
    public void displayInfo() {
        System.out.println("ID: " + personId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);

    }
    

    public void getInfobyId(String personId) {
        if (this.personId.equals(personId)) {
            displayInfo();
        }
    }
}

