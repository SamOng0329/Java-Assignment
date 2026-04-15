import java.time.LocalDate;
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
    private int tenantCount = 1;  
    private int agentCount = 1;     
    private int leaseCount = 1;
    private int paymentCount = 1;
    private int landlordCount = 1;  
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
        // Dummy Roles
        addAdmin(new UserRole("admin", "admin123", UserRole.Role.STAFF));
        
        Tenant t1 = new Tenant("TEN001", "Tan Mei Xin", "meixin@gmail.com", "0123456789", "001234145678");
        addTenant(t1, new UserRole("meixin", "meixin123", UserRole.Role.TENANT, t1));
        Tenant t2 = new Tenant("TEN002", "Chong Wen Liang", "wenliang@gmail.com", "0134567890", "011234145679");
        addTenant(t2, new UserRole("wenliang", "wenliang123", UserRole.Role.TENANT, t2));
        
        Agent a1 = new Agent("AGT001", "Lim Jie Ying", "jieying@gmail.com", "0198765432");
        addAgent(a1, new UserRole("jieying", "jieying123", UserRole.Role.AGENT, a1));
        Agent a2 = new Agent("AGT002", "Ahmad bin Ali", "ahmad@gmail.com", "0188765432");
        addAgent(a2, new UserRole("ahmad", "ahmad123", UserRole.Role.AGENT, a2));
        
        Landlord l1 = new Landlord("LAN001", "Wong Kah Hooi", "kahhooi@gmail.com", "0111111111");
        addLandLord(l1, new UserRole("kahhooi", "kahhooi123", UserRole.Role.LANDLORD, l1));
        Landlord l2 = new Landlord("LAN002", "Goh Mei Ling", "meiling@gmail.com", "0121111111");
        addLandLord(l2, new UserRole("meiling", "meiling123", UserRole.Role.LANDLORD, l2));

        addResidentialProperty("Jalan Ampang, KL", 1500.00, 850, ResidentialProperty.ResidentialType.APARTMENT, 2, 1, true, l1);
        addCommercialProperty("Taman Teratai, Serendah", 5000.00, 2000, CommercialProperty.CommercialType.OFFICE, 10, false, l1);
        addResidentialProperty("Setapak, KL", 2500.00, 1200, ResidentialProperty.ResidentialType.HOUSE, 3, 2, false, l2);
        addCommercialProperty("Taman Melati, KL", 6000.00, 2500, CommercialProperty.CommercialType.RETAIL, 5, true, l2);

        createLeaseRecord("RES001", "TEN001", "AGT001", LocalDate.of(2025, 3, 1), LocalDate.of(2026, 4, 28), 1500.00, true);
        createLeaseRecord("COM002", "TEN002", "AGT002", LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), 5000.00, true);
        
        requestVisit("TEN001", "RES003", "AGT001", LocalDate.of(2026, 5, 20));
        requestVisit("TEN002", "COM004", "AGT002", LocalDate.of(2026, 6, 15));
        
        createPaymentRecord("LEA001", 1500.00, "Bank Transfer");
        createPaymentRecord("LEA002", 5000.00, "Online Transfer");
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

    // Register Staff
    // ====================================================================================================
    public boolean registerStaff(String username, String password) {
        if (isUsernameExists(username) != null) {
            System.out.println("Error : Username already exists");
            return false;
        }
        UserRole user = new UserRole(username, password, UserRole.Role.STAFF);
        addAdmin(user);
        System.out.println("[SUCCESS] New Staff registered successfully.");
        return true;
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

    public ResidentialProperty addResidentialProperty(String location, double price, double sqft,
            ResidentialProperty.ResidentialType resType, int bedroom, int bathroom, boolean furnished, Landlord landlord) {
        String id = "RES" + String.format("%03d", propertyCount++);

        ResidentialProperty newRes = new ResidentialProperty(id, location, price, resType, sqft, bedroom, bathroom,
                furnished);
        propertyList.add(newRes);
        if (landlord != null) {
            landlord.addProperty(newRes); // Link property to landlord
        }
        return newRes;
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
        System.out.print("|    New Commercial Property Added");
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

    public boolean updatePropertyDetails(String propertyId, int fieldChoice, String newValue) {
        Property p = findPropertyById(propertyId);
        if (!(p instanceof ResidentialProperty res)) {
            System.out.println("  [ERROR] Residential property not found: " + propertyId);
            return false;
        }
        String fieldName, oldValue;
        switch (fieldChoice) {
            case 1 -> { fieldName = "Location";     oldValue = res.getLocation();                              res.setLocation(newValue); }
            case 2 -> {
                fieldName = "Rent Price"; oldValue = String.format("%.2f", res.getListingPrice());
                try { res.setListingPrice(Double.parseDouble(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid price: " + newValue); return false; }
            }
            case 3 -> {
                fieldName = "Size (sqft)"; oldValue = String.valueOf(res.getSquareFootage());
                try { res.setSquareFootage(Double.parseDouble(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid size: " + newValue); return false; }
            }
            case 4 -> {
                fieldName = "Bedrooms"; oldValue = String.valueOf(res.getNumBedrooms());
                try { res.setNumBedrooms(Integer.parseInt(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid number: " + newValue); return false; }
            }
            case 5 -> {
                fieldName = "Bathrooms"; oldValue = String.valueOf(res.getNumBathrooms());
                try { res.setNumBathrooms(Integer.parseInt(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid number: " + newValue); return false; }
            }
            case 6 -> {
                fieldName = "Furnished"; oldValue = res.getIsFurnished() ? "Yes" : "No";
                if (newValue.equalsIgnoreCase("true") || newValue.equalsIgnoreCase("yes")) res.setIsFurnished(true);
                else if (newValue.equalsIgnoreCase("false") || newValue.equalsIgnoreCase("no")) res.setIsFurnished(false);
                else { System.out.println("  [ERROR] Enter true/false or yes/no"); return false; }
            }
            default -> { System.out.println("  [ERROR] Invalid field choice."); return false; }
        }
        System.out.println("  [SUCCESS] Property " + propertyId + " updated.");
        System.out.println("  " + fieldName + " changed: [" + oldValue + "] -> [" + newValue + "]");
        return true;
    }

    public boolean updateCommercialDetails(String propertyId, int fieldChoice, String newValue) {
        Property p = findPropertyById(propertyId);
        if (!(p instanceof CommercialProperty com)) {
            System.out.println("  [ERROR] Commercial property not found: " + propertyId);
            return false;
        }
        String fieldName, oldValue;
        switch (fieldChoice) {
            case 1 -> { fieldName = "Location";     oldValue = com.getLocation();                              com.setLocation(newValue); }
            case 2 -> {
                fieldName = "Rent Price"; oldValue = String.format("%.2f", com.getListingPrice());
                try { com.setListingPrice(Double.parseDouble(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid price: " + newValue); return false; }
            }
            case 3 -> {
                fieldName = "Size (sqft)"; oldValue = String.valueOf(com.getSquareFootage());
                try { com.setSquareFootage(Double.parseDouble(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid size: " + newValue); return false; }
            }
            case 4 -> {
                fieldName = "Parking Spots"; oldValue = String.valueOf(com.getNumParkingSpots());
                try { com.setNumParkingSpots(Integer.parseInt(newValue)); }
                catch (NumberFormatException e) { System.out.println("  [ERROR] Invalid number: " + newValue); return false; }
            }
            case 5 -> {
                fieldName = "Loading Dock"; oldValue = com.getHasLoadingDock() ? "Yes" : "No";
                if (newValue.equalsIgnoreCase("true") || newValue.equalsIgnoreCase("yes")) com.setHasLoadingDock(true);
                else if (newValue.equalsIgnoreCase("false") || newValue.equalsIgnoreCase("no")) com.setHasLoadingDock(false);
                else { System.out.println("  [ERROR] Enter true/false or yes/no"); return false; }
            }
            case 6 -> {
                fieldName = "Commercial Type"; oldValue = com.getCommercialType().toString();
                CommercialProperty.CommercialType t = switch (newValue.trim().toUpperCase()) {
                    case "OFFICE"     -> CommercialProperty.CommercialType.OFFICE;
                    case "RETAIL"     -> CommercialProperty.CommercialType.RETAIL;
                    case "INDUSTRIAL" -> CommercialProperty.CommercialType.INDUSTRIAL;
                    default           -> null;
                };
                if (t == null) { System.out.println("  [ERROR] Enter OFFICE / RETAIL / INDUSTRIAL"); return false; }
                com.setCommercialType(t);
            }
            default -> { System.out.println("  [ERROR] Invalid field choice."); return false; }
        }
        System.out.println("  [SUCCESS] Property " + propertyId + " updated.");
        System.out.println("  " + fieldName + " changed: [" + oldValue + "] -> [" + newValue + "]");
        return true;
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

        for (Landlord l : landlordList) {
            l.removeProperty(p);
        }
        
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

        for (LeaseRecord l : leaseRecordList) {
            if (l.getProperty().getPropertyId().equals(propertyId) && l.getLeaseStatus()) {
                System.out.println("  [ERROR] Property is already leased: " + propertyId);
                return null;
            }
        }


        // Generate Lease ID
        String leaseId = "LEA" + String.format("%03d", leaseCount++);

        // Create a lease record
        LeaseRecord lease = new LeaseRecord(leaseId, p, a, t, startDate, endDate, monthlyRent, monthlyRent * 2, status);
        leaseRecordList.add(lease);
        p.setStatus(Property.PropertyStatus.RENTED); // Mark property as rented
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

        if(p.getStatus() != Property.PropertyStatus.AVAILABLE) {
            System.out.println("  [ERROR] Property is not available for visit: " + propertyId);
            return null;
        }
        
        if(visitDate.isBefore(LocalDate.now())) {
            System.out.println("  [ERROR] Visit date cannot be in the past: " + visitDate);
            return null;
        }


        String requestId = "VR" + String.format("%03d", visitRequestCount++);
        VisitRequest visitRequest = new VisitRequest(requestId, t, a, p, visitDate);
        visitRequestList.add(visitRequest);
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

    public ArrayList<VisitRequest> findVisitRequestsByTenantId(String tenantId) {
        ArrayList<VisitRequest> matchedRequests = new ArrayList<>();
        for (VisitRequest request : visitRequestList) {
            if (request.getTenant().getPersonId().equals(tenantId)) {
                matchedRequests.add(request);
            }
        }
        return matchedRequests;
    }

    public void listVisitRequestsByTenant(String tenantId) {
        ArrayList<VisitRequest> requests = findVisitRequestsByTenantId(tenantId);
        if (requests.isEmpty()) {
            System.out.println("  No visit requests found for tenant: " + tenantId);
            return;
        }
        for (VisitRequest vr : requests) {
            vr.displayInfo();
        }
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
