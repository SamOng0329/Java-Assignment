package Example;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Central manager class. Holds all data and provides all CRUD operations.
 *
 * OOP Concepts:
 * - Aggregation: Has lists of Property, Tenant, Agent, LeaseRecord, PaymentRecord
 *                (objects exist independently of this class)
 * - Encapsulation: All lists are private
 */
public class PropertyManager {

    // Aggregation - manager "has" these collections but doesn't own them exclusively
    private ArrayList<Property> properties;
    private ArrayList<Tenant> tenants;
    private ArrayList<Agent> agents;
    private ArrayList<LeaseRecord> leases;
    private ArrayList<PaymentRecord> payments;
    private ArrayList<User> users;

    // Auto-increment ID counters
    private int propertyCount = 1;
    private int tenantCount   = 1;
    private int agentCount    = 1;
    private int leaseCount    = 1;
    private int paymentCount  = 1;

    // Constructor
    public PropertyManager() {
        properties = new ArrayList<>();
        tenants    = new ArrayList<>();
        agents     = new ArrayList<>();
        leases     = new ArrayList<>();
        payments   = new ArrayList<>();
        users      = new ArrayList<>();
    }

    // =================================================================
    //  USER / AUTH
    // =================================================================

    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Finds a user by username. Returns null if not found.
     */
    public User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Attempts to log in. Returns the User object on success, null on failure.
     */
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
    //  PROPERTY CRUD
    // =================================================================

    /**
     * CREATE - Add a new Residential property.
     */
    public ResidentialProperty addResidentialProperty(String location, double price, double sqft,
            ResidentialProperty.ResidentialType type, int bedrooms, int bathrooms, boolean furnished) {
        String id = "PROP" + String.format("%03d", propertyCount++);
        ResidentialProperty p = new ResidentialProperty(id, location, price, sqft, type, bedrooms, bathrooms, furnished);
        properties.add(p);
        System.out.println("  [SUCCESS] Property added with ID: " + id);
        return p;
    }

    /**
     * CREATE - Add a new Commercial property.
     */
    public CommercialProperty addCommercialProperty(String location, double price, double sqft,
            CommercialProperty.CommercialType type, int parking, boolean loadingDock) {
        String id = "PROP" + String.format("%03d", propertyCount++);
        CommercialProperty p = new CommercialProperty(id, location, price, sqft, type, parking, loadingDock);
        properties.add(p);
        System.out.println("  [SUCCESS] Property added with ID: " + id);
        return p;
    }

    /**
     * READ - Find a property by ID.
     */
    public Property findPropertyById(String id) {
        for (Property p : properties) {
            if (p.getPropertyId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    /**
     * READ - Display all properties.
     */
    public void listAllProperties() {
        System.out.println("\n  ========== ALL PROPERTIES ==========");
        if (properties.isEmpty()) {
            System.out.println("  No properties found.");
            return;
        }
        for (Property p : properties) {
            p.displayInfo();
            System.out.println("  ------------------------------------");
        }
    }

    /**
     * READ - Display only available properties.
     */
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

    /**
     * READ - Filter properties by keyword (e.g. "apartment", "villa", "office").
     */
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

    /**
     * UPDATE - Change a property's listing price.
     */
    public boolean updatePropertyPrice(String propertyId, double newPrice) {
        Property p = findPropertyById(propertyId);
        if (p == null) {
            System.out.println("  [ERROR] Property not found: " + propertyId);
            return false;
        }
        p.setListingPrice(newPrice);
        System.out.println("  [SUCCESS] Price updated for " + propertyId);
        return true;
    }

    /**
     * DELETE - Remove a property (only if not currently rented).
     */
    public boolean deleteProperty(String propertyId) {
        Property p = findPropertyById(propertyId);
        if (p == null) {
            System.out.println("  [ERROR] Property not found: " + propertyId);
            return false;
        }
        if (p.getStatus() == Property.PropertyStatus.RENTED) {
            System.out.println("  [ERROR] Cannot delete a rented property.");
            return false;
        }
        properties.remove(p);
        System.out.println("  [SUCCESS] Property deleted: " + propertyId);
        return true;
    }

    // =================================================================
    //  TENANT CRUD
    // =================================================================

    /**
     * CREATE - Register a new tenant.
     */
    public Tenant registerTenant(String name, String email, String phone, String ic,
                                  String username, String password) {
        // Check for duplicate IC number
        for (Tenant t : tenants) {
            if (t.getIcNumber().equals(ic)) {
                System.out.println("  [ERROR] A tenant with IC " + ic + " already exists.");
                return null;
            }
        }
        // Check duplicate username
        if (findUserByUsername(username) != null) {
            System.out.println("  [ERROR] Username '" + username + "' is already taken.");
            return null;
        }

        String id = "TEN" + String.format("%03d", tenantCount++);
        Tenant t = new Tenant(id, name, email, phone, ic);
        tenants.add(t);

        // Create a linked User account for the tenant
        User user = new User(username, password, User.Role.TENANT, t);
        users.add(user);

        System.out.println("  [SUCCESS] Tenant registered: " + id + " - " + name);
        System.out.println("  [SUCCESS] Login account created: username = " + username);
        return t;
    }

    /**
     * READ - Find a tenant by ID.
     */
    public Tenant findTenantById(String id) {
        for (Tenant t : tenants) {
            if (t.getPersonId().equalsIgnoreCase(id)) return t;
        }
        return null;
    }

    /**
     * READ - Display all tenants.
     */
    public void listAllTenants() {
        System.out.println("\n  ========== ALL TENANTS ==========");
        if (tenants.isEmpty()) {
            System.out.println("  No tenants registered.");
            return;
        }
        for (Tenant t : tenants) {
            t.displayInfo();
            System.out.println("  ------------------------------------");
        }
    }

    /**
     * UPDATE - Update tenant contact details.
     */
    public boolean updateTenantContact(String tenantId, String email, String phone) {
        Tenant t = findTenantById(tenantId);
        if (t == null) {
            System.out.println("  [ERROR] Tenant not found: " + tenantId);
            return false;
        }
        t.setEmail(email);
        t.setPhone(phone);
        System.out.println("  [SUCCESS] Tenant contact updated: " + tenantId);
        return true;
    }

    // =================================================================
    //  AGENT CRUD
    // =================================================================

    /**
     * CREATE - Register a new agent.
     */
    public Agent registerAgent(String name, String email, String phone, String license,
                                String username, String password) {
        if (findUserByUsername(username) != null) {
            System.out.println("  [ERROR] Username '" + username + "' is already taken.");
            return null;
        }
        String id = "AGT" + String.format("%03d", agentCount++);
        Agent a = new Agent(id, name, email, phone, license);
        agents.add(a);

        // Create linked User account for agent
        User user = new User(username, password, User.Role.AGENT, a);
        users.add(user);

        System.out.println("  [SUCCESS] Agent registered: " + id + " - " + name);
        System.out.println("  [SUCCESS] Login account created: username = " + username);
        return a;
    }

    /**
     * READ - Find agent by ID.
     */
    public Agent findAgentById(String id) {
        for (Agent a : agents) {
            if (a.getPersonId().equalsIgnoreCase(id)) return a;
        }
        return null;
    }

    /**
     * READ - Display all agents.
     */
    public void listAllAgents() {
        System.out.println("\n  ========== ALL AGENTS ==========");
        if (agents.isEmpty()) {
            System.out.println("  No agents registered.");
            return;
        }
        for (Agent a : agents) {
            a.displayInfo();
            System.out.println("  ------------------------------------");
        }
    }

    // =================================================================
    //  LEASE CRUD
    // =================================================================

    /**
     * CREATE - Create a new lease record.
     */
    public LeaseRecord createLease(String propertyId, String tenantId, String agentId,
                                    LocalDate startDate, LocalDate endDate, double monthlyRent) {
        Property p = findPropertyById(propertyId);
        Tenant   t = findTenantById(tenantId);
        Agent    a = findAgentById(agentId); // null is acceptable (no agent)

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

    /**
     * READ - Find lease by ID.
     */
    public LeaseRecord findLeaseById(String id) {
        for (LeaseRecord l : leases) {
            if (l.getLeaseId().equalsIgnoreCase(id)) return l;
        }
        return null;
    }

    /**
     * READ - Display all leases.
     */
    public void listAllLeases() {
        System.out.println("\n  ========== ALL LEASES ==========");
        if (leases.isEmpty()) { System.out.println("  No leases found."); return; }
        for (LeaseRecord l : leases) l.displayInfo();
    }

    /**
     * READ - Display leases belonging to a specific tenant.
     */
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
     * READ - Display all overdue leases.
     */
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

    /**
     * UPDATE/DELETE - Terminate an active lease.
     */
    public boolean terminateLease(String leaseId) {
        LeaseRecord l = findLeaseById(leaseId);
        if (l == null) { System.out.println("  [ERROR] Lease not found."); return false; }
        if (!l.isActive()) { System.out.println("  [ERROR] Lease already terminated."); return false; }
        l.terminateLease();
        System.out.println("  [SUCCESS] Lease terminated: " + leaseId);
        return true;
    }

    // =================================================================
    //  PAYMENT
    // =================================================================

    /**
     * CREATE - Record a payment for a lease.
     */
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

    /**
     * READ - Display all payments for a specific lease.
     */
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

    // =================================================================
    //  REPORTS (Admin only)
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
        if (total > 0) {
            System.out.printf("  Occupancy Rate   : %.1f%%%n", (rented * 100.0 / total));
        }
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
}
