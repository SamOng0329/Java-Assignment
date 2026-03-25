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

    // Constructor
    public PropertyFunction() {
        propertyList = new ArrayList<>();
        agentList = new ArrayList<>();
        tenantList = new ArrayList<>();
        leaseRecordList = new ArrayList<>();
        paymentRecordList = new ArrayList<>();
        userRoleList = new ArrayList<>();
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
        UserRole user = new UserRole(username, password, UserRole.Role.TENANT);
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

    // // Edit tenant details 
    // public void editTenantDetails(String tenantId, String name, String email, String phone, String ic) {
    //     Tenant t = findTenantById(tenantId);
    //     if (t != null) {
    //         t.setName(name);
    //         t.setEmail(email);
    //         t.setPhone(phone);
    //         t.setIcNumber(ic);
    //     }
    // } // sam part dont touch or i will kill u 
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

        // Creating a ures objrct
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
    // ====================================================================================================



}
