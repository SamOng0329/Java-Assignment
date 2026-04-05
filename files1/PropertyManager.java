package files1;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Central manager class. Holds all data and provides all CRUD operations.
 *
 * OOP Concepts:
 * - Aggregation: Has lists of Property, Tenant, Agent, Landlord, LeaseRecord,
 *   PaymentRecord (objects exist independently of this class)
 * - Encapsulation: All lists are private
 */
public class PropertyManager {

    // Aggregation - manager "has" these collections but doesn't own them exclusively
    private ArrayList<Property>      properties;
    private ArrayList<Tenant>        tenants;
    private ArrayList<Agent>         agents;
    private ArrayList<Landlord>      landlords;   // NEW
    private ArrayList<LeaseRecord>   leases;
    private ArrayList<PaymentRecord>    payments;
    private ArrayList<User>             users;
    private ArrayList<VisitRequest>     visitRequests;
    private ArrayList<RentalApplication> rentalApplications;

    // Auto-increment ID counters
    private int propertyCount = 1;
    private int tenantCount   = 1;
    private int agentCount    = 1;
    private int landlordCount = 1;   // NEW
    private int leaseCount           = 1;
    private int paymentCount         = 1;
    private int visitRequestCount    = 1;
    private int rentalAppCount       = 1;

    // Constructor
    public PropertyManager() {
        properties = new ArrayList<>();
        tenants    = new ArrayList<>();
        agents     = new ArrayList<>();
        landlords  = new ArrayList<>();
        leases             = new ArrayList<>();
        payments           = new ArrayList<>();
        users              = new ArrayList<>();
        visitRequests      = new ArrayList<>();
        rentalApplications = new ArrayList<>();
    }

    // =================================================================
    // DATABASE ACCESSORS
    // =================================================================

    public ArrayList<Property>      getProperties() { return properties; }
    public ArrayList<Tenant>        getTenants()    { return tenants; }
    public ArrayList<Agent>         getAgents()     { return agents; }
    public ArrayList<Landlord>      getLandlords()  { return landlords; }
    public ArrayList<LeaseRecord>   getLeases()     { return leases; }
    public ArrayList<PaymentRecord> getPayments()   { return payments; }
    public ArrayList<User>             getUsers()             { return users; }
    public ArrayList<VisitRequest>     getVisitRequests()     { return visitRequests; }
    public ArrayList<RentalApplication> getRentalApplications() { return rentalApplications; }

    public void setCounters(int prop, int ten, int agt, int lse, int pay) {
        this.propertyCount = prop;
        this.tenantCount   = ten;
        this.agentCount    = agt;
        this.leaseCount    = lse;
        this.paymentCount  = pay;
    }

    public void setRequestCounters(int visitReq, int rentalApp) {
        this.visitRequestCount = visitReq;
        this.rentalAppCount    = rentalApp;
    }

    // =================================================================
    // USER / AUTH
    // =================================================================

    public void addUser(User user) {
        users.add(user);
    }

    public User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public User login(String username, String password) {
        User user = findUserByUsername(username);
        if (user == null) {
            System.out.println("  [ERROR] Username not found.");
            return null;
        }
        if (user.login(password)) {
            System.out.println("  [SUCCESS] Welcome, " + username + "! Role: " + user.getRole());
            return user;
        } else {
            System.out.println("  [ERROR] Incorrect password.");
            return null;
        }
    }

    // =================================================================
    // PROPERTY CRUD
    // =================================================================

    public ResidentialProperty addResidentialProperty(String location, double price, double sqft,
            ResidentialProperty.ResidentialType type, int bedrooms, int bathrooms, boolean furnished) {
        String id = "PROP" + String.format("%03d", propertyCount++);
        ResidentialProperty p = new ResidentialProperty(id, location, price, sqft, type, bedrooms, bathrooms, furnished);
        properties.add(p);
        System.out.println("  [SUCCESS] Property added with ID: " + id);
        return p;
    }

    public CommercialProperty addCommercialProperty(String location, double price, double sqft,
            CommercialProperty.CommercialType type, int parking, boolean loadingDock) {
        String id = "PROP" + String.format("%03d", propertyCount++);
        CommercialProperty p = new CommercialProperty(id, location, price, sqft, type, parking, loadingDock);
        properties.add(p);
        System.out.println("  [SUCCESS] Property added with ID: " + id);
        return p;
    }

    /**
     * CREATE - Add a Residential property and link it to a landlord.
     */
    public ResidentialProperty addResidentialPropertyForLandlord(String location, double price, double sqft,
            ResidentialProperty.ResidentialType type, int bedrooms, int bathrooms,
            boolean furnished, Landlord owner) {
        ResidentialProperty p = addResidentialProperty(location, price, sqft, type, bedrooms, bathrooms, furnished);
        if (owner != null) owner.addOwnedProperty(p);
        return p;
    }

    /**
     * CREATE - Add a Commercial property and link it to a landlord.
     */
    public CommercialProperty addCommercialPropertyForLandlord(String location, double price, double sqft,
            CommercialProperty.CommercialType type, int parking, boolean loadingDock, Landlord owner) {
        CommercialProperty p = addCommercialProperty(location, price, sqft, type, parking, loadingDock);
        if (owner != null) owner.addOwnedProperty(p);
        return p;
    }

    public Property findPropertyById(String id) {
        for (Property p : properties) {
            if (p.getPropertyId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public void listAllProperties() {
        System.out.println("\n  ========== ALL PROPERTIES ==========");
        if (properties.isEmpty()) { System.out.println("  No properties found."); return; }
        for (Property p : properties) { p.displayInfo(); System.out.println("  ------------------------------------"); }
    }

    public void listAvailableProperties() {
        System.out.println("\n  ========== AVAILABLE PROPERTIES ==========");
        boolean found = false;
        for (Property p : properties) {
            if (p.getStatus() == Property.PropertyStatus.AVAILABLE) {
                p.displayInfo();
                System.out.println("  ------------------------------------");
                found = true;
            }
        }
        if (!found) System.out.println("  No available properties at the moment.");
    }

    public void searchPropertiesByType(String keyword) {
        System.out.println("\n  ========== SEARCH: " + keyword.toUpperCase() + " ==========");
        boolean found = false;
        for (Property p : properties) {
            if (p.getPropertyType().toLowerCase().contains(keyword.toLowerCase())) {
                p.displayInfo();
                System.out.println("  ------------------------------------");
                found = true;
            }
        }
        if (!found) System.out.println("  No properties found for: " + keyword);
    }

    public boolean updatePropertyPrice(String propertyId, double newPrice) {
        Property p = findPropertyById(propertyId);
        if (p == null) { System.out.println("  [ERROR] Property not found: " + propertyId); return false; }
        p.setListingPrice(newPrice);
        System.out.println("  [SUCCESS] Price updated for " + propertyId);
        return true;
    }

    public boolean deleteProperty(String propertyId) {
        Property p = findPropertyById(propertyId);
        if (p == null) { System.out.println("  [ERROR] Property not found: " + propertyId); return false; }
        if (p.getStatus() == Property.PropertyStatus.RENTED) {
            System.out.println("  [ERROR] Cannot delete a rented property.");
            return false;
        }
        properties.remove(p);
        System.out.println("  [SUCCESS] Property deleted: " + propertyId);
        return true;
    }

    // =================================================================
    // TENANT CRUD
    // =================================================================

    public Tenant registerTenant(String name, String email, String phone, String ic,
            String username, String password) {
        for (Tenant t : tenants) {
            if (t.getIcNumber().equals(ic)) {
                System.out.println("  [ERROR] A tenant with IC " + ic + " already exists.");
                return null;
            }
        }
        if (findUserByUsername(username) != null) {
            System.out.println("  [ERROR] Username '" + username + "' is already taken.");
            return null;
        }
        String id = "TEN" + String.format("%03d", tenantCount++);
        Tenant t = new Tenant(id, name, email, phone, ic);
        tenants.add(t);
        users.add(new User(username, password, User.Role.TENANT, t));
        System.out.println("  [SUCCESS] Tenant registered: " + id + " - " + name);
        System.out.println("  [SUCCESS] Login account created: username = " + username);
        return t;
    }

    public Tenant findTenantById(String id) {
        for (Tenant t : tenants) {
            if (t.getPersonId().equalsIgnoreCase(id)) return t;
        }
        return null;
    }

    public void listAllTenants() {
        System.out.println("\n  ========== ALL TENANTS ==========");
        if (tenants.isEmpty()) { System.out.println("  No tenants registered."); return; }
        for (Tenant t : tenants) { t.displayInfo(); System.out.println("  ------------------------------------"); }
    }

    public boolean updateTenantDetails(String tenantId, int fieldChoice, String newValue) {
        Tenant t = findTenantById(tenantId);
        if (t == null) { System.out.println("  [ERROR] Tenant not found: " + tenantId); return false; }
        if (fieldChoice < 1 || fieldChoice > 4) {
            System.out.println("  [ERROR] Invalid field choice.");
            return false;
        }
        String fieldName, oldValue;
        switch (fieldChoice) {
            case 1 -> { fieldName = "Name";      oldValue = t.getName();      t.setName(newValue); }
            case 2 -> { fieldName = "IC Number"; oldValue = t.getIcNumber();  t.setIcNumber(newValue); }
            case 3 -> { fieldName = "Phone";     oldValue = t.getPhone();     t.setPhone(newValue); }
            case 4 -> { fieldName = "Email";     oldValue = t.getEmail();     t.setEmail(newValue); }
            default -> { System.out.println("  [ERROR] Invalid field choice."); return false; }
        }
        System.out.println("  [SUCCESS] Tenant " + tenantId + " updated.");
        System.out.println("  " + fieldName + " changed: [" + oldValue + "] -> [" + newValue + "]");
        return true;
    }

    // =================================================================
    // AGENT CRUD
    // =================================================================

    public Agent registerAgent(String name, String email, String phone, String license,
            String username, String password) {
        if (findUserByUsername(username) != null) {
            System.out.println("  [ERROR] Username '" + username + "' is already taken.");
            return null;
        }
        String id = "AGT" + String.format("%03d", agentCount++);
        Agent a = new Agent(id, name, email, phone, license);
        agents.add(a);
        users.add(new User(username, password, User.Role.AGENT, a));
        System.out.println("  [SUCCESS] Agent registered: " + id + " - " + name);
        System.out.println("  [SUCCESS] Login account created: username = " + username);
        return a;
    }
    
    public Agent findAgentById(String id) {
        for (Agent a : agents) {
            if (a.getPersonId().equalsIgnoreCase(id)) return a;
        }
        return null;
    }

    public void listAllAgents() {
        System.out.println("\n  ========== ALL AGENTS ==========");
        if (agents.isEmpty()) { System.out.println("  No agents registered."); return; }
        for (Agent a : agents) { a.displayInfo(); System.out.println("  ------------------------------------"); }
    }

    // =================================================================
    // LANDLORD CRUD  (NEW)
    // =================================================================

    /**
     * CREATE - Register a new Landlord and create their login account.
     */
    public Landlord registerLandlord(String name, String email, String phone,
            String username, String password) {
        if (findUserByUsername(username) != null) {
            System.out.println("  [ERROR] Username '" + username + "' is already taken.");
            return null;
        }
        String id = "LLD" + String.format("%03d", landlordCount++);
        Landlord ll = new Landlord(id, name, email, phone);
        landlords.add(ll);
        users.add(new User(username, password, User.Role.LANDLORD, ll));
        System.out.println("  [SUCCESS] Landlord registered: " + id + " - " + name);
        System.out.println("  [SUCCESS] Login account created: username = " + username);
        return ll;
    }

    /**
     * READ - Find a landlord by their person ID.
     */
    public Landlord findLandlordById(String id) {
        for (Landlord ll : landlords) {
            if (ll.getPersonId().equalsIgnoreCase(id)) return ll;
        }
        return null;
    }

    /**
     * READ - Display all landlords.
     */
    public void listAllLandlords() {
        System.out.println("\n  ========== ALL LANDLORDS ==========");
        if (landlords.isEmpty()) { System.out.println("  No landlords registered."); return; }
        for (Landlord ll : landlords) {
            ll.displayInfo();
            System.out.println("  Properties: " + ll.getOwnedProperties().size());
            System.out.println("  ------------------------------------");
        }
    }

    // =================================================================
    // LEASE CRUD
    // =================================================================

    public LeaseRecord createLease(String propertyId, String tenantId, String agentId,
            LocalDate startDate, LocalDate endDate, double monthlyRent) {
        Property p = findPropertyById(propertyId);
        Tenant t   = findTenantById(tenantId);
        Agent a    = findAgentById(agentId);

        if (p == null) { System.out.println("  [ERROR] Property not found."); return null; }
        if (t == null) { System.out.println("  [ERROR] Tenant not found."); return null; }
        if (p.getStatus() != Property.PropertyStatus.AVAILABLE) {
            System.out.println("  [ERROR] Property is not available for rent.");
            return null;
        }
        String id = "LEASE" + String.format("%03d", leaseCount++);
        LeaseRecord lease = new LeaseRecord(id, p, t, a, startDate, endDate, monthlyRent);
        leases.add(lease);
        System.out.println("  [SUCCESS] Lease created: " + id);
        System.out.println("  [INFO] Deposit required: RM " + String.format("%.2f", lease.getDepositAmount()));
        return lease;
    }

    public LeaseRecord findLeaseById(String id) {
        for (LeaseRecord l : leases) {
            if (l.getLeaseId().equalsIgnoreCase(id)) return l;
        }
        return null;
    }

    public void listAllLeases() {
        System.out.println("\n  ========== ALL LEASES ==========");
        if (leases.isEmpty()) { System.out.println("  No leases found."); return; }
        for (LeaseRecord l : leases) l.displayInfo();
    }

    public void listLeasesByTenant(String tenantId) {
        System.out.println("\n  ========== MY LEASES ==========");
        boolean found = false;
        for (LeaseRecord l : leases) {
            if (l.getTenant().getPersonId().equalsIgnoreCase(tenantId)) {
                l.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No leases found for this tenant.");
    }

    /**
     * READ - Display leases for properties owned by a specific landlord.
     */
    public void listLeasesByLandlord(String landlordId) {
        Landlord ll = findLandlordById(landlordId);
        if (ll == null) { System.out.println("  [ERROR] Landlord not found."); return; }
        System.out.println("\n  ========== LEASES FOR MY PROPERTIES ==========");
        boolean found = false;
        for (LeaseRecord l : leases) {
            if (ll.ownsProperty(l.getProperty().getPropertyId())) {
                l.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No leases found for your properties.");
    }

    public void listOverdueLeases() {
        System.out.println("\n  ========== OVERDUE LEASES ==========");
        boolean found = false;
        for (LeaseRecord l : leases) {
            if (l.isOverdue()) {
                l.displayInfo();
                l.getTenant().setHasOverduePayment(true);
                found = true;
            }
        }
        if (!found) System.out.println("  No overdue leases.");
    }

    public boolean terminateLease(String leaseId) {
        LeaseRecord l = findLeaseById(leaseId);
        if (l == null) { System.out.println("  [ERROR] Lease not found."); return false; }
        if (!l.isActive()) { System.out.println("  [ERROR] Lease already terminated."); return false; }
        l.terminateLease();
        System.out.println("  [SUCCESS] Lease terminated: " + leaseId);
        return true;
    }

    // =================================================================
    // PAYMENT
    // =================================================================

    public PaymentRecord recordPayment(String leaseId, double amount, String method) {
        LeaseRecord l = findLeaseById(leaseId);
        if (l == null) { System.out.println("  [ERROR] Lease not found."); return null; }
        if (!l.isActive()) { System.out.println("  [ERROR] Cannot pay for a terminated lease."); return null; }
        String id = "PAY" + String.format("%03d", paymentCount++);
        boolean late = l.isOverdue();
        PaymentRecord pay = new PaymentRecord(id, l, amount, LocalDate.now(), method, late);
        payments.add(pay);
        System.out.println("  [SUCCESS] Payment recorded: " + id + (late ? " [LATE PAYMENT]" : ""));
        return pay;
    }

    public void viewPaymentHistory(String leaseId) {
        System.out.println("\n  ========== PAYMENT HISTORY: " + leaseId + " ==========");
        boolean found = false;
        for (PaymentRecord pay : payments) {
            if (pay.getLease().getLeaseId().equalsIgnoreCase(leaseId)) {
                pay.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No payments found.");
    }

    /**
     * READ - Display ALL payments ever recorded, sorted newest first.
     */
    public void viewAllPayments() {
        System.out.println("\n  ========== ALL PAYMENTS ==========");

        // Sort by date descending (newest first)
        ArrayList<PaymentRecord> filtered = new ArrayList<>(payments);
        filtered.sort((a, b) -> b.getPaymentDate().compareTo(a.getPaymentDate()));

        if (filtered.isEmpty()) {
            System.out.println("  No payments found.");
            return;
        }

        double total = 0;
        int lateCount = 0;
        for (PaymentRecord pay : filtered) {
            // Show extra context: which lease & tenant
            String tenantName = (pay.getLease() != null)
                    ? pay.getLease().getTenant().getName() : "N/A";
            String leaseId    = (pay.getLease() != null)
                    ? pay.getLease().getLeaseId() : "DELETED";
            String propId     = (pay.getLease() != null)
                    ? pay.getLease().getProperty().getPropertyId() : "N/A";

            System.out.println("  ------------------------------------------");
            System.out.println("  Payment ID : " + pay.getPaymentId());
            System.out.println("  Date       : " + pay.getPaymentDate());
            System.out.println("  Amount     : RM " + String.format("%.2f", pay.getAmountPaid()));
            System.out.println("  Method     : " + pay.getPaymentMethod());
            System.out.println("  Lease      : " + leaseId + "  |  Property: " + propId);
            System.out.println("  Tenant     : " + tenantName);
            System.out.println("  Status     : " + (pay.isLatePayment() ? "⚠ LATE PAYMENT" : "On Time"));

            total += pay.getAmountPaid();
            if (pay.isLatePayment()) lateCount++;
        }

        System.out.println("  ==========================================");
        System.out.println("  Total Records  : " + filtered.size());
        System.out.println("  Total Collected: RM " + String.format("%.2f", total));
        System.out.println("  Late Payments  : " + lateCount);
        System.out.println("  ==========================================");
    }

    /**
     * READ - Display ALL payments for a specific tenant (used in Tenant portal).
     */
    public void viewAllPaymentsByTenant(String tenantId) {
        System.out.println("\n  ========== MY PAYMENTS (ALL) ==========");

        ArrayList<PaymentRecord> filtered = new ArrayList<>();
        for (PaymentRecord pay : payments) {
            if (pay.getLease() != null
                    && pay.getLease().getTenant().getPersonId().equalsIgnoreCase(tenantId)) {
                filtered.add(pay);
            }
        }

        filtered.sort((a, b) -> b.getPaymentDate().compareTo(a.getPaymentDate()));

        if (filtered.isEmpty()) {
            System.out.println("  No payments found.");
            return;
        }

        double total = 0;
        for (PaymentRecord pay : filtered) {
            pay.displayInfo();
            total += pay.getAmountPaid();
        }

        System.out.println("  ------------------------------------------");
        System.out.println("  Total Paid: RM " + String.format("%.2f", total)
                + "  |  Records: " + filtered.size());
        System.out.println("  ==========================================");
    }

    // =================================================================
    // REPORTS
    // =================================================================

    public void generateOccupancyReport() {
        int total = properties.size(), rented = 0, available = 0, sold = 0;
        for (Property p : properties) {
            switch (p.getStatus()) {
                case RENTED    -> rented++;
                case AVAILABLE -> available++;
                case SOLD      -> sold++;
            }
        }
        System.out.println("\n  ========== OCCUPANCY REPORT ==========");
        System.out.println("  Total Properties : " + total);
        System.out.println("  Available        : " + available);
        System.out.println("  Rented           : " + rented);
        System.out.println("  Sold             : " + sold);
        if (total > 0) System.out.printf("  Occupancy Rate   : %.1f%%%n", (rented * 100.0 / total));
        System.out.println("  ======================================");
    }

    public void generateAgentPerformanceReport() {
        System.out.println("\n  ========== AGENT PERFORMANCE ==========");
        if (agents.isEmpty()) { System.out.println("  No agents found."); return; }
        for (Agent a : agents) {
            System.out.println("  " + a.getName() + " [" + a.getPersonId() + "] - Deals: " + a.getTotalDealsCompleted());
        }
        System.out.println("  =======================================");
    }

    // =================================================================
    // VISIT REQUESTS  (Tenant -> Landlord)
    // =================================================================

    /**
     * CREATE - Tenant submits a visit request for a property.
     */
    public VisitRequest submitVisitRequest(String tenantId, String propertyId,
                                           java.time.LocalDate preferredDate, String message) {
        Tenant t   = findTenantById(tenantId);
        Property p = findPropertyById(propertyId);

        if (t == null) { System.out.println("  [ERROR] Tenant not found."); return null; }
        if (p == null) { System.out.println("  [ERROR] Property not found."); return null; }
        if (p.getStatus() == Property.PropertyStatus.SOLD) {
            System.out.println("  [ERROR] This property has been sold and is not available for visits.");
            return null;
        }

        String id = "VIS" + String.format("%03d", visitRequestCount++);
        VisitRequest vr = new VisitRequest(id, t, p, preferredDate, message);
        visitRequests.add(vr);
        System.out.println("  [SUCCESS] Visit request submitted: " + id);
        System.out.println("  [INFO] The landlord will review your request.");
        return vr;
    }

    /**
     * READ - Show all visit requests for properties owned by a landlord.
     */
    public void listVisitRequestsForLandlord(String landlordId) {
        Landlord ll = findLandlordById(landlordId);
        if (ll == null) { System.out.println("  [ERROR] Landlord not found."); return; }

        System.out.println("\n  ========== VISIT REQUESTS FOR MY PROPERTIES ==========");
        boolean found = false;
        for (VisitRequest vr : visitRequests) {
            if (ll.ownsProperty(vr.getProperty().getPropertyId())) {
                vr.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No visit requests found.");
    }

    /**
     * READ - Show all visit requests submitted by a tenant.
     */
    public void listVisitRequestsByTenant(String tenantId) {
        System.out.println("\n  ========== MY VISIT REQUESTS ==========");
        boolean found = false;
        for (VisitRequest vr : visitRequests) {
            if (vr.getTenant().getPersonId().equalsIgnoreCase(tenantId)) {
                vr.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No visit requests found.");
    }

    /**
     * UPDATE - Landlord approves or rejects a visit request.
     */
    public boolean respondToVisitRequest(String requestId, boolean approve, String reply) {
        for (VisitRequest vr : visitRequests) {
            if (vr.getRequestId().equalsIgnoreCase(requestId)) {
                if (vr.getStatus() != VisitRequest.VisitStatus.PENDING) {
                    System.out.println("  [ERROR] This request has already been responded to.");
                    return false;
                }
                if (approve) vr.approve(reply);
                else         vr.reject(reply);
                System.out.println("  [SUCCESS] Visit request " + requestId
                        + (approve ? " APPROVED." : " REJECTED."));
                return true;
            }
        }
        System.out.println("  [ERROR] Visit request not found: " + requestId);
        return false;
    }

    public VisitRequest findVisitRequestById(String id) {
        for (VisitRequest vr : visitRequests) {
            if (vr.getRequestId().equalsIgnoreCase(id)) return vr;
        }
        return null;
    }

    // =================================================================
    // RENTAL APPLICATIONS  (Tenant -> Agent)
    // =================================================================

    /**
     * CREATE - Tenant submits a rental application through an agent.
     */
    public RentalApplication submitRentalApplication(String tenantId, String propertyId,
                                                      String agentId, java.time.LocalDate desiredMoveIn,
                                                      double offeredRent, String message) {
        Tenant t   = findTenantById(tenantId);
        Property p = findPropertyById(propertyId);
        Agent a    = findAgentById(agentId);

        if (t == null) { System.out.println("  [ERROR] Tenant not found."); return null; }
        if (p == null) { System.out.println("  [ERROR] Property not found."); return null; }
        if (a == null) { System.out.println("  [ERROR] Agent not found."); return null; }
        if (p.getStatus() != Property.PropertyStatus.AVAILABLE) {
            System.out.println("  [ERROR] Property is not available for rental application.");
            return null;
        }

        // Check for duplicate pending application
        for (RentalApplication ra : rentalApplications) {
            if (ra.getTenant().getPersonId().equalsIgnoreCase(tenantId)
                    && ra.getProperty().getPropertyId().equalsIgnoreCase(propertyId)
                    && ra.getStatus() == RentalApplication.ApplicationStatus.PENDING) {
                System.out.println("  [ERROR] You already have a pending application for this property.");
                return null;
            }
        }

        String id = "APP" + String.format("%03d", rentalAppCount++);
        RentalApplication ra = new RentalApplication(id, t, p, a, desiredMoveIn, offeredRent, message);
        rentalApplications.add(ra);
        System.out.println("  [SUCCESS] Rental application submitted: " + id);
        System.out.println("  [INFO] Agent " + a.getName() + " will review your application.");
        return ra;
    }

    /**
     * READ - Show all rental applications assigned to a specific agent.
     */
    public void listApplicationsForAgent(String agentId) {
        System.out.println("\n  ========== RENTAL APPLICATIONS (MY QUEUE) ==========");
        boolean found = false;
        for (RentalApplication ra : rentalApplications) {
            if (ra.getAgent() != null
                    && ra.getAgent().getPersonId().equalsIgnoreCase(agentId)) {
                ra.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No rental applications found.");
    }

    /**
     * READ - Show all rental applications submitted by a tenant.
     */
    public void listApplicationsByTenant(String tenantId) {
        System.out.println("\n  ========== MY RENTAL APPLICATIONS ==========");
        boolean found = false;
        for (RentalApplication ra : rentalApplications) {
            if (ra.getTenant().getPersonId().equalsIgnoreCase(tenantId)) {
                ra.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No rental applications found.");
    }

    /**
     * UPDATE - Agent approves or rejects a rental application.
     */
    public boolean respondToApplication(String applicationId, boolean approve, String reply) {
        for (RentalApplication ra : rentalApplications) {
            if (ra.getApplicationId().equalsIgnoreCase(applicationId)) {
                if (ra.getStatus() != RentalApplication.ApplicationStatus.PENDING) {
                    System.out.println("  [ERROR] This application has already been responded to.");
                    return false;
                }
                if (approve) ra.approve(reply);
                else         ra.reject(reply);
                System.out.println("  [SUCCESS] Application " + applicationId
                        + (approve ? " APPROVED." : " REJECTED."));
                return true;
            }
        }
        System.out.println("  [ERROR] Application not found: " + applicationId);
        return false;
    }

    public RentalApplication findApplicationById(String id) {
        for (RentalApplication ra : rentalApplications) {
            if (ra.getApplicationId().equalsIgnoreCase(id)) return ra;
        }
        return null;
    }

}
