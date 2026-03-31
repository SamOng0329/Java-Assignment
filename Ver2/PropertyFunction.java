import java.util.ArrayList;

public class PropertyFunction {
    // Creating Arraylist for all object
    public ArrayList<Property> propertyList;
    public ArrayList<Agent> agentList;
    public ArrayList<Tenant> tenantList;
    public ArrayList<Landlord> landlordList;
    public ArrayList<LeaseRecord> leaseRecordList;
    public ArrayList<PaymentRecord> paymentRecordList;
    public ArrayList<UserRole> userRoleList;
    // instance( for initialization checking)

    private static PropertyFunction instance = null;

    // Constructor
    public PropertyFunction() {
        propertyList = new ArrayList<>();
        agentList = new ArrayList<>();
        tenantList = new ArrayList<>();
        landlordList = new ArrayList<>();
        leaseRecordList = new ArrayList<>();
        paymentRecordList = new ArrayList<>();
        userRoleList = new ArrayList<>();

        initializeUsers();
    }

    // Auto-increment ID counters
    private int propertyCount = 1;
    private int tenantCount = 1;
    private int agentCount = 1;
    private int leaseCount = 1;
    private int paymentCount = 1;
    private int landlordCount = 1;

    // Getters
    public ArrayList<Property> getPropertyList() {
        return propertyList;
    }

    public ArrayList<Agent> getAgentList() {
        return agentList;
    }

    public ArrayList<Tenant> getTenantList() {
        return tenantList;
    }

    public ArrayList<LeaseRecord> getLeaseRecordList() {
        return leaseRecordList;
    }

    public ArrayList<PaymentRecord> getPaymentRecordList() {
        return paymentRecordList;
    }

    public ArrayList<UserRole> getUserRoleList() {
        return userRoleList;
    }

    // Setter for counters
    public void setCounters(int prop, int tent, int agt, int lease, int pay) {
        this.propertyCount = prop;
        this.tenantCount = tent;
        this.agentCount = agt;
        this.leaseCount = lease;
        this.paymentCount = pay;
    }

    // function of adding admin
    public void addAdmin(UserRole user) {
        userRoleList.add(user);
    }

    public void addTenant(Tenant tenant, UserRole user) {
        userRoleList.add(user);
        tenantList.add(tenant);
    }

    public void addAgent(Agent agent, UserRole user) {
        agentList.add(agent);
        userRoleList.add(user);
    }

    public void addLandLord(Landlord landlord, UserRole user) {
        landlordList.add(landlord);
        userRoleList.add(user);
    }

    public static PropertyFunction getInstance() {
        if (instance == null) {
            instance = new PropertyFunction();
        }
        return instance;
    }

    public static void clearscreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void initializeUsers() {
        addAdmin(new UserRole("admin", "admin123", UserRole.Role.STAFF));
        Tenant t1 = new Tenant("TEN001", "John Doe", "john.doe@example.com", "1234567890", "S12345678");
        addTenant(t1, new UserRole("johndoe", "password123", UserRole.Role.TENANT, t1));
        Agent a1 = new Agent("AGT001", "Jane Doe", "jane.doe@example.com", "0987654321");
        addAgent(a1, new UserRole("janedoe", "password456", UserRole.Role.AGENT, a1));
        Landlord l1 = new Landlord("LLD001", "Jace Doe", "jace.doe@example.com", "1111111111");
        addLandLord(l1, new UserRole("jacedoe", "password789", UserRole.Role.LANDLORD, l1));
    }

    public boolean Login(String username, String password) {
        for (UserRole user : userRoleList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.login(password);
                return true;
            }
        }
        return false;
    }

    // Function of checking username
    public UserRole isUsernameExists(String username) {
        for (UserRole u : userRoleList) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    // CRUD for Tenant
    // ====================================================================================================
    public Tenant registerTenant(String name, String email, String phone, String ic, String username, String password) {
        String tenantId = "TEN" + String.format("%03d", tenantCount++);

        for (Tenant t : tenantList) {
            if (t.getIcNumber().equals(ic)) {
                System.out.println("Error : IC already exists");
                return null;
            }
            if (t.getPhone().equals(phone)) {
                System.out.println("Error : Phone number already exists");
                return null;
            }
            if (t.getEmail().equals(email)) {
                System.out.println("Error : Email already exists");
                return null;
            }

        }
        for (UserRole u : userRoleList) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Error : Username already exists");
                return null;
            }
            if (u.getPassword().equals(password)) {
                System.out.println("Error : Password already exists");
                return null;
            }
        }

        // Check if username already exists
        if (isUsernameExists(username) != null) {
            System.out.println("Error : Username already exists");
            return null;
        }

        // Creating a user object
        Tenant t = new Tenant(tenantId, name, email, phone, ic);
        tenantList.add(t);

        // Creating a user login
        UserRole user = new UserRole(username, password, UserRole.Role.TENANT, t);
        userRoleList.add(user);

        return t;
    }

    // Find tenant by ID
    public Tenant findTenantById(String tenantId) {
        for (Tenant t : tenantList) {
            if (t.getPersonId().equals(tenantId)) {
                return t;
            }
        }
        return null;
    }

    // Display all tenants
    public void listAllTenants() {
        if (tenantList.isEmpty()) {
            System.out.println("No tenants found");
            return;
        }
        for (Tenant t : tenantList) {
            System.out.println(t);
        }
    }

    // Update tenant details
    public boolean updateTenantDetails(String tenantId, int fieldChoice, String newValue) {
        Tenant t = findTenantById(tenantId);
        if (t == null) {
            System.out.println("  [ERROR] Tenant not found: " + tenantId);
            return false;
        }

        if (fieldChoice < 1 || fieldChoice > 4) {
            System.out.println("  [ERROR] Invalid field choice.");
            return false;
        }

        String fieldName, oldValue;
        switch (fieldChoice) {
            case 1 -> {
                fieldName = "Name";
                oldValue = t.getName();
                t.setName(newValue);
            }

            case 2 -> {
                fieldName = "IC Number";
                oldValue = t.getIcNumber();
                t.setIcNumber(newValue);
            }

            case 3 -> {
                fieldName = "Phone";
                oldValue = t.getPhone();
                t.setPhone(newValue);
            }

            case 4 -> {
                fieldName = "Email";
                oldValue = t.getEmail();
                t.setEmail(newValue);
            }

            default -> {
                System.out.println("  [ERROR] Invalid field choice.");
                return false;
            }
        }
        System.out.println("  [SUCCESS] Tenant " + tenantId + " updated.");
        System.out.println("  " + fieldName + " changed: [" + oldValue + "] -> [" + newValue + "]");
        return true;
    }
    // ====================================================================================================

    // CRUD for Agent
    // ====================================================================================================
    public Agent registerAgent(String name, String email, String phone, String username, String password) {
        String agentId = "AGT" + String.format("%03d", agentCount++);

        // Check if username already exists
        if (isUsernameExists(username) != null) {
            System.out.println("Error : Username already exists");
            return null;
        }

        // Creating a user object
        Agent a = new Agent(agentId, name, email, phone);
        agentList.add(a);

        // Creating a user login
        UserRole user = new UserRole(username, password, UserRole.Role.AGENT);
        userRoleList.add(user);

        return a;
    }

    // Find agent by ID
    public Agent findAgentById(String agentId) {
        for (Agent a : agentList) {
            if (a.getPersonId().equals(agentId)) {
                return a;
            }
        }
        return null;
    }

    // Display all agents
    public void listAllAgents() {
        if (agentList.isEmpty()) {
            System.out.println("No agents found");
            return;
        }
        for (Agent a : agentList) {
            System.out.println(a);
        }
    }

    // Update agent details
    public boolean updateAgentDetails(String agentId, int fieldChoice, String newValue) {
        Agent a = findAgentById(agentId);
        if (a == null) {
            System.out.println("  [ERROR] Agent not found: " + agentId);
            return false;
        }

        if (fieldChoice < 1 || fieldChoice > 3) {
            System.out.println("  [ERROR] Invalid field choice.");
            return false;
        }

        String fieldName, oldValue;

        switch (fieldChoice) {
            case 1 -> {
                fieldName = "Name";
                oldValue = a.getName();
                a.setName(newValue);
            }

            case 2 -> {
                fieldName = "Email";
                oldValue = a.getEmail();
                a.setEmail(newValue);
            }

            case 3 -> {
                fieldName = "Phone";
                oldValue = a.getPhone();
                a.setPhone(newValue);
            }

            default -> {
                System.out.println("  [ERROR] Invalid field choice.");
                return false;
            }
        }

        System.out.println("  [SUCCESS] Agent " + agentId + " updated.");
        System.out.println("  " + fieldName + " changed: [" + oldValue + "] -> [" + newValue + "]");
        return true;
    }
    // ====================================================================================================

    // CRUD for Landlord
    // ====================================================================================================
    public Landlord registerLandlord(String name, String email, String phone, String username, String password) {
        String landlordId = "LAN" + String.format("%03d", landlordCount++);

        // Check if username already exists
        if (isUsernameExists(username) != null) {
            System.out.println("Error : Username already exists");
            return null;
        }

        // Creating a user object
        Landlord l = new Landlord(landlordId, name, email, phone);
        landlordList.add(l);

        // Creating a user login
        UserRole user = new UserRole(username, password, UserRole.Role.LANDLORD);
        userRoleList.add(user);

        return l;
    }

    // Find landlord by ID
    public Landlord findLandlordById(String landlordId) {
        for (Landlord l : landlordList) {
            if (l.getPersonId().equals(landlordId)) {
                return l;
            }
        }
        return null;
    }

    // Display all landlord
    public void listAllLandlord() {
        if (landlordList.isEmpty()) {
            System.out.println("No landlords found");
            return;
        }
        for (Landlord l : landlordList) {
            System.out.println(l);
        }
    }

    // Update landlord details
    public boolean updateLandlordDetails(String landlordId, int fieldChoice, String newValue) {
        Landlord ll = findLandlordById(landlordId);
        if (ll == null) {
            System.out.println("  [ERROR] Landlord not found: " + landlordId);
            return false;
        }

        if (fieldChoice < 1 || fieldChoice > 4) {
            System.out.println("  [ERROR] Invalid field choice.");
            return false;
        }

        String fieldName, oldValue;

        switch (fieldChoice) {
            case 1 -> {
                fieldName = "Name";
                oldValue = ll.getName();
                ll.setName(newValue);
            }

            case 2 -> {
                fieldName = "Email";
                oldValue = ll.getEmail();
                ll.setEmail(newValue);
            }

            case 3 -> {
                fieldName = "Phone";
                oldValue = ll.getPhone();
                ll.setPhone(newValue);
            }

            default -> {
                System.out.println("  [ERROR] Invalid field choice.");
                return false;
            }
        }

        System.out.println("  [SUCCESS] Landlord " + landlordId + " updated.");
        System.out.println("  " + fieldName + " changed: [" + oldValue + "] -> [" + newValue + "]");
        return true;
    }
    // ====================================================================================================

    // ====================================================================================================
    
}
