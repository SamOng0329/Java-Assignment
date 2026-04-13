import java.rmi.dgc.Lease;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;




public class PropertyFunction {
    // Creating Arraylist for all object
    public ArrayList<Property> propertyList;
    public ArrayList<Agent> agentList;
    public ArrayList<Tenant> tenantList;
    public ArrayList<Landlord> landlordList;
    public ArrayList<LeaseRecord> leaseRecordList;
    public ArrayList<PaymentRecord> paymentRecordList;
    public ArrayList<UserRole> userRoleList;
    public ArrayList<VisitRequest> visitRequestList;
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
        visitRequestList = new ArrayList<>();


        initializeUsers();
    }

    // Auto-increment ID counters
    private int propertyCount = 1;
    private int tenantCount = 2;  // Start from 2 since we already have 1 tenant initialized
    private int agentCount = 2;   // Start from 2 since we already have 1 agent initialized
    private int leaseCount = 1;
    private int paymentCount = 1;
    private int landlordCount = 2;  // Start from 2 since we already have 1 landlord initialized
    private int visitRequestCount = 1;

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

    public ArrayList<Landlord> getLandlordList() {
        return landlordList;
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


    public ArrayList<VisitRequest> getVisitRequestList() {
        return visitRequestList;
    }

    // Setter for counters
    public void setCounters(int prop, int tent, int agt, int lease, int pay, int visitRequest) {
        this.propertyCount = prop;
        this.tenantCount = tent;
        this.agentCount = agt;
        this.leaseCount = lease;
        this.paymentCount = pay;
        this.visitRequestCount = visitRequest;
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

    public void initializeUsers() {
        addAdmin(new UserRole("admin", "admin123", UserRole.Role.STAFF));
        Tenant t1 = new Tenant("TEN001", "John Doe", "john.doe@example.com", "1234567890", "S12345678");
        addTenant(t1, new UserRole("johndoe", "password123", UserRole.Role.TENANT, t1));
        Agent a1 = new Agent("AGT001", "Jane Doe", "jane.doe@example.com", "0987654321");
        addAgent(a1, new UserRole("janedoe", "password456", UserRole.Role.AGENT, a1));
        Landlord l1 = new Landlord("LAN001", "Jace Doe", "jace.doe@example.com", "1111111111");
        addLandLord(l1, new UserRole("jacedoe", "password789", UserRole.Role.LANDLORD, l1));
        addResidentialProperty("123 Main St", 1500.00, 850, ResidentialProperty.ResidentialType.APARTMENT, 2, 1, true, l1);
        createLeaseRecord("RES001", "TEN001", "AGT001", LocalDate.of(2025, 3, 1), LocalDate.of(2026, 4, 28), 1500.00, true);
        requestVisit("TEN001", "RES001","AGT001", LocalDate.of(2025, 2, 20));
    }

    public boolean login(String username, String password) {
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
                fieldName = "Email";
                oldValue = t.getEmail();
                t.setEmail(newValue);
            }

            case 4 -> {
                fieldName = "Phone";
                oldValue = t.getPhone();
                t.setPhone(newValue);
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

    // Delete tenant
    public boolean deleteTenant(String tenantId) {
        Tenant t = findTenantById(tenantId);
        if (t == null) {
            System.out.println("  [ERROR] Tenant not found: " + tenantId);
            return false;
        }
        // Remove the associated UserRole
        userRoleList.removeIf(u -> u.getRole() == UserRole.Role.TENANT
                && u.getLinkedPerson() != null
                && u.getLinkedPerson().getPersonId().equals(tenantId));
        tenantList.remove(t);
        System.out.println("  [SUCCESS] Tenant " + tenantId + " deleted.");
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

    // Delete agent
    public boolean deleteAgent(String agentId) {
        Agent a = findAgentById(agentId);
        if (a == null) {
            System.out.println("  [ERROR] Agent not found: " + agentId);
            return false;
        }
        userRoleList.removeIf(u -> u.getRole() == UserRole.Role.AGENT
                && u.getLinkedPerson() != null
                && u.getLinkedPerson().getPersonId().equals(agentId));
        agentList.remove(a);
        System.out.println("  [SUCCESS] Agent " + agentId + " deleted.");
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

    // Delete landlord
    public boolean deleteLandlord(String landlordId) {
        Landlord ll = findLandlordById(landlordId);
        if (ll == null) {
            System.out.println("  [ERROR] Landlord not found: " + landlordId);
            return false;
        }
        userRoleList.removeIf(u -> u.getRole() == UserRole.Role.LANDLORD
                && u.getLinkedPerson() != null
                && u.getLinkedPerson().getPersonId().equals(landlordId));
        landlordList.remove(ll);
        System.out.println("  [SUCCESS] Landlord " + landlordId + " deleted.");
        return true;
    }

    // Create Residential

    public void addResidentialProperty(String location, double price, double sqft,
            ResidentialProperty.ResidentialType resType, int bedroom, int bathroom, boolean furnished,  Landlord landlord) {
        String id = "RES" + String.format("%03d", propertyCount++);

        ResidentialProperty newRes = new ResidentialProperty(id, location, price, resType, sqft, bedroom, bathroom,
                furnished);
        propertyList.add(newRes);
        if (landlord != null) {
            landlord.addProperty(newRes); // Link property to landlord
        }
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("|    New Residential Property Added");
        newRes.displayInfo();
    }

    public void addCommercialProperty(String location, double price, double sqft,
            CommercialProperty.CommercialType commType, int parking, boolean loadingDock, Landlord landlord) {
        String id = "COM" + String.format("%03d", propertyCount++);

        CommercialProperty newCom = new CommercialProperty(id, location, price, sqft, commType, parking, loadingDock);
        propertyList.add(newCom);

        if (landlord != null) {
            landlord.addProperty(newCom); // Link property to landlord
        }

        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("|    New Commercial Property Added");
        newCom.displayInfo();
    }

    // Read property

    public void listAllProperties() {
        if (propertyList.isEmpty()) {
            System.out.println("No properties found in the system.");
            return;
        }
        for (Property p : propertyList) {
            p.displayInfo();
        }
    }

    public Property findAvailableProperty(Property.PropertyStatus status) {
        for (Property p : propertyList) {
            if (p.getStatus() == Property.PropertyStatus.AVAILABLE)
                p.displayInfo();
        }
        return null;
    }

    public Property findByPropertyType(String Type) {
        for (Property p : propertyList) {
            if (p.getPropertyType().toLowerCase().contains(Type.toLowerCase()))
                p.displayInfo();
        }
        return null;
    }

    // Update property

    public boolean updatePropertyPrice(String id, double newPrice) {
        Property p = findPropertyById(id);
        if (p != null) {
            p.setListingPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean updatePropertyStatus(String propertyId, Property.PropertyStatus newStatus) {
        Property p = findPropertyById(propertyId);
        if (p == null) {
            System.out.println("  [ERROR] Property not found: " + propertyId);
            return false;
        }
        p.setStatus(newStatus);
        System.out.println("  [SUCCESS] Status of " + propertyId + " updated to " + newStatus);
        return true;
    }

    // Delete property

    public boolean deleteProperty(String propertyId) {
        Property p = findPropertyById(propertyId);
        if (p == null) {
            System.out.println("[ERROR] Property not found.");
            return false;
        }
        if (p.getStatus() == Property.PropertyStatus.RENTED) {
            System.out.println("[ERROR] Cannot delete a rented property!");
            return false;
        }
        propertyList.remove(p);
        System.out.println("[SUCCESS] Property " + propertyId + " deleted.");
        return true;
    }

    // Payment Record
    // ====================================================================================================
    public PaymentRecord createPaymentRecord(String leaseId, double amount, String paymentMethod) {
        LeaseRecord l = findLeaseById(leaseId);

        // Find the lease
        if (l == null) {
            System.out.println("  [ERROR] Lease not found: " + leaseId);
            return null;
        }

        // Cannot pay for the terminated lease
        if (l.getLeaseStatus() == false ) {
            System.out.println("  [ERROR] Lease is already completed: " + leaseId);
            return null;
        }

        if (amount <= 0) {
            System.out.println("  [ERROR] Invalid payment amount" + amount);
            return null;
        }

        // Payment Id
        String paymentId = "PAY" + String.format("%03d", paymentCount++);

        // Create a new payment record
        PaymentRecord payment = new PaymentRecord(paymentId, l, amount, LocalDate.now(), paymentMethod, true);
        paymentRecordList.add(payment);
        l.setLeasestatus(false); // Mark lease as completed after payment

        System.out.println("  [SUCCESS] Payment record created: " + paymentId);
        return payment;
    }

    // Find payment by ID
    public PaymentRecord findPaymentById(String paymentId) {
        for (PaymentRecord p : paymentRecordList) {
            if (p.getPaymentId().equals(paymentId)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<PaymentRecord> findPaymentByLeaseArrayList(String leaseId) {
        ArrayList<PaymentRecord> payments = new ArrayList<>();
        for (PaymentRecord p : paymentRecordList) {
            if (p.getLeaseRecord().getLeaseId().equals(leaseId)) {
                payments.add(p);
            }
        }
        return payments;
    }

    // Read and Display all payment records
    public void listAllPayments() {
        if (paymentRecordList.isEmpty()) {
            System.out.println("  No payment records found.");
            return;
        }
        for (PaymentRecord p : paymentRecordList) {
            p.displayInfo();
        }
    }

    // Display payment records by lease ID
    public void listPaymentsByLeaseId(String leaseId) {
        boolean found = false;
        for (PaymentRecord p : paymentRecordList) {
            if (p.getLeaseRecord().getLeaseId().equalsIgnoreCase(leaseId)) {
                p.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("  [INFO] No payment records found for lease: " + leaseId);
        }
    }

    // Display all payment that mode by tenant
    public void listPaymentsByTenantId(String tenantId) {
        boolean found = false;
        for (PaymentRecord p : paymentRecordList) {
            if (p.getLeaseRecord().getTenant().getPersonId().equalsIgnoreCase(tenantId)) {
                p.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("  [INFO] No payment records found for tenant: " + tenantId);
        }
    }

    // ====================================================================================================

    // CRUD Lease Record
    // ====================================================================================================
    public LeaseRecord createLeaseRecord(String propertyId, String tenantId, String agentId, LocalDate startDate,
            LocalDate endDate, double monthlyRent, boolean status) {
        Property p = findPropertyById(propertyId);
        Tenant t = findTenantById(tenantId);
        Agent a = findAgentById(agentId);

        // Validate property exist
        if (p == null) {
            System.out.println("  [ERROR] Property not found: " + propertyId);
            return null;
        }

        // Valiadate tenant exists
        if (t == null) {
            System.out.println("  [ERROR] Tenant not found: " + tenantId);
            return null;
        }

        // Validate property availability
        if (p.getStatus() != Property.PropertyStatus.AVAILABLE) {
            System.out.println("  [ERROR] Property is not available for lease: " + propertyId);
            return null;
        }

        // Validate dates
        if (startDate.isAfter(endDate)) {
            System.out.println("  [ERROR] Start date must be before end date");
            return null;
        }

        // Generate Lease ID
        String leaseId = "LEA" + String.format("%03d", leaseCount++);

        // Create a lease record
        LeaseRecord lease = new LeaseRecord(leaseId, p, a, t, startDate, endDate, monthlyRent, monthlyRent * 2, status);
        leaseRecordList.add(lease);
        return lease;
    }

    public void deleteLeaseRecord(String leaseId) {
        LeaseRecord l = findLeaseById(leaseId);
        if (l == null) {
            System.out.println("  [ERROR] Lease not found: " + leaseId);
            return;
        }
        leaseRecordList.remove(l);
        System.out.println("  [SUCCESS] Lease " + leaseId + " deleted.");
    }


    // Find lease by ID
    public LeaseRecord findLeaseById(String leaseId) {
        for (LeaseRecord l : leaseRecordList) {
            if (l.getLeaseId().equals(leaseId)) {
                return l;
            }
        }
        return null;
    }

    public LeaseRecord findLeaseByPropertyId(String propertyId) {
        for (LeaseRecord l : leaseRecordList) {
            if (l.getProperty().getPropertyId().equals(propertyId)) {
                return l;
            }
        }
        return null;
    }

    // Display all lease
    public void listAllLease() {
        if (leaseRecordList.isEmpty()) {
            System.out.println("  No lease records found.");
            return;
        }
        for (LeaseRecord l : leaseRecordList) {
            l.displayInfo();
        }
    }


    public void  AgentLeaseTermination(String leaseId, Agent agent) {
        LeaseRecord l = findLeaseById(leaseId);
        if (l == null) {
            System.out.println("  [ERROR] Lease not found: " + leaseId);
            return;
        }

        if (l.getAgent() == null || !l.getAgent().getPersonId().equals(agent.getPersonId())) {
            System.out.println("  [ERROR] You are not the agent responsible for this lease.");
            return;
        }
        l.terminationLease();

    }


    public void Agentacceptlease(String leaseId, Agent agent) {
        LeaseRecord l = findLeaseById(leaseId);
        if (l == null) {
            System.out.println("  [ERROR] Lease not found: " + leaseId);
            return;
        }
        
        if (l.getAgent() == null || !l.getAgent().getPersonId().equals(agent.getPersonId())) {
            System.out.println("  [ERROR] You are not the agent responsible for this lease.");
            return;
        }
        l.acceptLease();
        System.out.println("  [SUCCESS] Lease " + leaseId + " accepted.");
    }

    // Display lease by tenant ID
    public void listLeaseByTenantId(String tenantId) {
        boolean found = false;
        for (LeaseRecord l : leaseRecordList) {
            if (l.getTenant().getPersonId().equalsIgnoreCase(tenantId)) {
                l.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("  [INFO] No lease records found for tenant: " + tenantId);
        }
    }



    // find property by ID
    public Property findPropertyById(String propertyId) {
        for (Property p : propertyList) {
            if (p.getPropertyId().equals(propertyId)) {
                return p;
            }
        }
        return null;
    }
    // ====================================================================================================

    // Reports 
    // ====================================================================================================
    public void generatePropertyReport(){
        int total = propertyList.size();
        int rented = 0;
        int available = 0;
        int sold = 0;
        for(Property p : propertyList){
            switch(p.getStatus()){
                case RENTED: rented++; break;
                case AVAILABLE: available++; break;
                case SOLD: sold++; break;
            }
        }
        System.out.println("\n============= PROPERTY REPORT =============");
        System.out.println("| Total Properties: " + total);
        System.out.println("| Rented Properties: " + rented);
        System.out.println("| Available Properties: " + available);
        System.out.println("| Sold Properties: " + sold);
        System.out.println("===========================================");
    }

    public void generateAgentPerformanceReport(){
        System.out.println("\n============ AGENT PERFORMANCE REPORT ============");
        if(agentList.isEmpty()){
            System.out.println("|  No agents found.");
            return;
        }
        for(Agent a : agentList){
            System.out.println("| " + a.getName() + a.getPersonId());
            System.out.printf("| %s10 : [%s6] - Deals: %d\n", a.getName(), a.getPersonId(), a.getTotalDealsCompleted());
        }
        System.out.println("==================================================");
    }
    // ====================================================================================================

    public VisitRequest requestVisit(String tenantId, String propertyId, String agentId, LocalDate visitDate) {
        Tenant t = findTenantById(tenantId);
        Property p = findPropertyById(propertyId);
        Agent a= findAgentById(agentId);


        if (t == null) {
            System.out.println("  [ERROR] Tenant not found: " + tenantId);
            return null;
        }
        if (p == null) {
            System.out.println("  [ERROR] Property not found: " + propertyId);
            return null;
        }
        if (a == null) {
            System.out.println("  [ERROR] Agent not found: " + agentId);
            return null;
        }


        String requestId = "VR" + String.format("%03d", visitRequestCount++);
        VisitRequest visitRequest = new VisitRequest(requestId, t, a, p, visitDate);
        visitRequestList.add(visitRequest);
        System.out.println("  [SUCCESS] Visit request created with ID: " + requestId);
        return visitRequest;
    }


    public ArrayList<VisitRequest> findVisitRequestsByAgentId(String agentId) {
        ArrayList<VisitRequest> matchedRequests = new ArrayList<>();
        for (VisitRequest request : visitRequestList) {
            if (request.getAgent().getPersonId().equals(agentId)) {
                matchedRequests.add(request);
            }
        }
        return matchedRequests;
    }


    public VisitRequest findVisitRequestById(String requestId) {
        for (VisitRequest vr : visitRequestList) {
            if (vr.getRequestId().equals(requestId)) {
                return vr;
            }
        }
        return null;
    }   


    public void listAllVisitRequests() {
        if (visitRequestList.isEmpty()) {
            System.out.println("  No visit requests found.");
            return;
        }
        for (VisitRequest vr : visitRequestList) {
            vr.displayInfo();
        }
    }


    public void deleteVisitRequest(String requestId) {
        VisitRequest vr = findVisitRequestById(requestId);
        if (vr == null) {
            System.out.println("  [ERROR] Visit request not found: " + requestId);
            return;
        }
        visitRequestList.remove(vr);
        System.out.println("  [SUCCESS] Visit request " + requestId + " deleted.");
    }

    public void processVisitRequest(String requestId, boolean isApproved,UserRole loggedUser) {
        VisitRequest vr = findVisitRequestById(requestId);
        if (vr == null) {
            System.out.println("  [ERROR] Visit request not found: " + requestId);
            return;
        }
        if (loggedUser.getLinkedPerson().getPersonId().equals(vr.getAgent().getPersonId())) {
            vr.approvedecision(isApproved);
            System.out.println("  [SUCCESS] Visit request " + requestId + " processed.");
     
        }
        else {
            System.out.println("  [ERROR] You are not authorized to process this visit request.");
        }

    }


    

    
}
