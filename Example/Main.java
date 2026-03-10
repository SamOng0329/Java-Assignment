package files1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main entry point for the Property Rental & Real Estate System.
 *
 * Roles supported:
 * - ADMIN    : full system management
 * - AGENT    : view properties, create leases, record payments
 * - TENANT   : browse properties, view own leases & payments
 * - LANDLORD : view/edit own properties, view profile (NEW)
 */
public class Main {

    private static PropertyManager manager = new PropertyManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("  [INFO] Booting system...");
        Database.load(manager, "database.txt");

        if (manager.getUsers().isEmpty()) {
            loadSampleData();
        }

        boolean running = true;
        while (running) {
            printWelcomeBanner();
            System.out.println("  1. Login");
            System.out.println("  2. Register as New Tenant");
            System.out.println("  0. Exit");
            int choice = readInt("  Enter choice: ");

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleTenantSelfRegister();
                case 0 -> {
                    System.out.println("\n  Saving database...");
                    Database.save(manager, "database.txt");
                    System.out.println("  Goodbye! Thank you for using our system.");
                    running = false;
                }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // =================================================================
    // LOGIN & REGISTRATION
    // =================================================================

    static void printWelcomeBanner() {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║   PROPERTY RENTAL & REAL ESTATE SYSTEM   ║");
        System.out.println("  ║         Welcome! Please log in.          ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    static void handleLogin() {
        System.out.println("\n  ========== LOGIN ==========");
        int attempts = 0;

        while (attempts < 3) {
            String username = readString("  Username: ");
            String password = readString("  Password: ");

            User loggedInUser = manager.login(username, password);

            if (loggedInUser != null) {
                switch (loggedInUser.getRole()) {
                    case ADMIN    -> adminMenu(loggedInUser);
                    case AGENT    -> agentMenu(loggedInUser);
                    case TENANT   -> tenantMenu(loggedInUser);
                    case LANDLORD -> landlordMenu(loggedInUser);  // NEW
                }
                return;
            }

            attempts++;
            int remaining = 3 - attempts;
            if (remaining > 0) {
                System.out.println("  [WARNING] " + remaining + " attempt(s) remaining.");
            }
        }
        System.out.println("  [ERROR] Too many failed attempts. Returning to main menu.");
    }

    static void handleTenantSelfRegister() {
        System.out.println("\n  ========== REGISTER AS TENANT ==========");
        try {
            String name     = readString("  Full Name      : ");
            String email    = readString("  Email          : ");
            String phone    = readString("  Phone Number   : ");
            String ic       = readString("  IC Number      : ");
            String username = readString("  Choose Username: ");
            String password = readString("  Choose Password: ");

            if (password.length() < 4) {
                System.out.println("  [ERROR] Password must be at least 4 characters.");
                return;
            }
            manager.registerTenant(name, email, phone, ic, username, password);

        } catch (Exception e) {
            System.out.println("  [ERROR] Registration failed: " + e.getMessage());
        }
    }

    // =================================================================
    // ADMIN MENU
    // =================================================================

    static void adminMenu(User admin) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║           ADMIN DASHBOARD                ║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. Property Management                  ║");
            System.out.println("  ║  2. Tenant Management                    ║");
            System.out.println("  ║  3. Agent Management                     ║");
            System.out.println("  ║  4. Lease Management                     ║");
            System.out.println("  ║  5. Payment Management                   ║");
            System.out.println("  ║  6. Reports                              ║");
            System.out.println("  ║  7. Landlord Management                  ║");
            System.out.println("  ║  0. Logout                               ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            int choice = readInt("  Enter choice: ");
            switch (choice) {
                case 1 -> adminPropertyMenu();
                case 2 -> adminTenantMenu();
                case 3 -> adminAgentMenu();
                case 4 -> adminLeaseMenu();
                case 5 -> adminPaymentMenu();
                case 6 -> adminReportsMenu();
                case 7 -> adminLandlordMenu();
                case 0 -> {
                    admin.logout();
                    System.out.println("  Logged out successfully.");
                    loggedIn = false;
                }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    static void adminPropertyMenu() {
        System.out.println("\n  --- PROPERTY MANAGEMENT ---");
        System.out.println("  1. Add Residential Property");
        System.out.println("  2. Add Commercial Property");
        System.out.println("  3. View All Properties");
        System.out.println("  4. View Available Properties");
        System.out.println("  5. Search Properties by Type");
        System.out.println("  6. Update Property Price");
        System.out.println("  7. Delete Property");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> addResidentialProperty();
            case 2 -> addCommercialProperty();
            case 3 -> manager.listAllProperties();
            case 4 -> manager.listAvailableProperties();
            case 5 -> {
                String kw = readString("  Enter type (apartment/villa/office/warehouse): ");
                manager.searchPropertiesByType(kw);
            }
            case 6 -> {
                String id  = readString("  Property ID: ");
                double price = readDouble("  New Price (RM): ");
                manager.updatePropertyPrice(id, price);
            }
            case 7 -> {
                String id = readString("  Property ID to delete: ");
                manager.deleteProperty(id);
            }
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    static void addResidentialProperty() {
        try {
            System.out.println("\n  -- Add Residential Property --");
            String location  = readString("  Location: ");
            double price     = readDouble("  Monthly Price (RM): ");
            double sqft      = readDouble("  Square Footage: ");
            System.out.println("  Sub-type: 1. Apartment   2. Villa");
            int t = readInt("  Choose: ");
            ResidentialProperty.ResidentialType type = (t == 1)
                    ? ResidentialProperty.ResidentialType.APARTMENT
                    : ResidentialProperty.ResidentialType.VILLA;
            int bedrooms  = readInt("  Bedrooms: ");
            int bathrooms = readInt("  Bathrooms: ");
            System.out.print("  Furnished? (y/n): ");
            boolean furnished = sc.next().trim().equalsIgnoreCase("y");
            sc.nextLine();
            manager.addResidentialProperty(location, price, sqft, type, bedrooms, bathrooms, furnished);
        } catch (InputMismatchException e) {
            System.out.println("  [ERROR] Invalid number entered.");
            sc.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    static void addCommercialProperty() {
        try {
            System.out.println("\n  -- Add Commercial Property --");
            String location = readString("  Location: ");
            double price    = readDouble("  Monthly Price (RM): ");
            double sqft     = readDouble("  Square Footage: ");
            System.out.println("  Sub-type: 1. Office   2. Warehouse");
            int t = readInt("  Choose: ");
            CommercialProperty.CommercialType type = (t == 1)
                    ? CommercialProperty.CommercialType.OFFICE
                    : CommercialProperty.CommercialType.WAREHOUSE;
            int parking = readInt("  Parking Spots: ");
            System.out.print("  Has Loading Dock? (y/n): ");
            boolean dock = sc.next().trim().equalsIgnoreCase("y");
            sc.nextLine();
            manager.addCommercialProperty(location, price, sqft, type, parking, dock);
        } catch (InputMismatchException e) {
            System.out.println("  [ERROR] Invalid number entered.");
            sc.nextLine();
        }
    }

    static void adminTenantMenu() {
        System.out.println("\n  --- TENANT MANAGEMENT ---");
        System.out.println("  1. Register New Tenant (by Admin)");
        System.out.println("  2. View All Tenants");
        System.out.println("  3. Update Tenant Contact");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> {
                String name     = readString("  Name: ");
                String email    = readString("  Email: ");
                String phone    = readString("  Phone: ");
                String ic       = readString("  IC Number: ");
                String username = readString("  Username: ");
                String password = readString("  Password: ");
                manager.registerTenant(name, email, phone, ic, username, password);
            }
            case 2 -> manager.listAllTenants();
            case 3 -> {
                String id = readString("  Tenant ID: ");
                Tenant toUpdate = manager.findTenantById(id);
                if (toUpdate == null) { System.out.println("  [ERROR] Tenant not found: " + id); break; }
                toUpdate.displayInfo();
                System.out.println("\n  Which detail do you want to update?");
                System.out.println("  1. Name  2. IC Number  3. Phone  4. Email  0. Back");
                int fieldChoice = readInt("  Enter choice: ");
                if (fieldChoice == 0) break;
                String newValue = readString("  New value: ");
                manager.updateTenantDetails(id, fieldChoice, newValue);
            }
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    static void adminAgentMenu() {
        System.out.println("\n  --- AGENT MANAGEMENT ---");
        System.out.println("  1. Register New Agent");
        System.out.println("  2. View All Agents");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> {
                String name     = readString("  Name: ");
                String email    = readString("  Email: ");
                String phone    = readString("  Phone: ");
                String license  = readString("  License Number: ");
                String username = readString("  Username: ");
                String password = readString("  Password: ");
                manager.registerAgent(name, email, phone, license, username, password);
            }
            case 2 -> manager.listAllAgents();
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    // NEW — Admin can register landlords and view their portfolios
    static void adminLandlordMenu() {
        System.out.println("\n  --- LANDLORD MANAGEMENT ---");
        System.out.println("  1. Register New Landlord");
        System.out.println("  2. View All Landlords");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> {
                String name        = readString("  Name: ");
                String email       = readString("  Email: ");
                String phone       = readString("  Phone: ");
                String username    = readString("  Username: ");
                String password    = readString("  Password: ");
                manager.registerLandlord(name, email, phone, username, password);
            }
            case 2 -> manager.listAllLandlords();
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    static void adminLeaseMenu() {
        System.out.println("\n  --- LEASE MANAGEMENT ---");
        System.out.println("  1. Create New Lease");
        System.out.println("  2. View All Leases");
        System.out.println("  3. Terminate Lease");
        System.out.println("  4. View Overdue Leases");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> createLease();
            case 2 -> manager.listAllLeases();
            case 3 -> { String id = readString("  Lease ID: "); manager.terminateLease(id); }
            case 4 -> manager.listOverdueLeases();
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    static void createLease() {
        try {
            System.out.println("\n  -- Create New Lease --");
            String propId  = readString("  Property ID: ");
            String tenId   = readString("  Tenant ID: ");
            String agtId   = readString("  Agent ID (press Enter to skip): ");
            if (agtId.isBlank()) agtId = null;
            String startStr = readString("  Start Date (YYYY-MM-DD): ");
            String endStr   = readString("  End Date   (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(startStr);
            LocalDate end   = LocalDate.parse(endStr);
            double rent     = readDouble("  Monthly Rent (RM): ");
            manager.createLease(propId, tenId, agtId, start, end, rent);
        } catch (DateTimeParseException e) {
            System.out.println("  [ERROR] Invalid date. Please use YYYY-MM-DD format.");
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    static void adminPaymentMenu() {
        System.out.println("\n  --- PAYMENT MANAGEMENT ---");
        System.out.println("  1. Record a Payment");
        System.out.println("  2. View Payment History for a Lease");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> {
                String id    = readString("  Lease ID: ");
                double amount = readDouble("  Amount Paid (RM): ");
                String method = readString("  Payment Method (Cash/Online/Bank Transfer): ");
                manager.recordPayment(id, amount, method);
            }
            case 2 -> { String id = readString("  Lease ID: "); manager.viewPaymentHistory(id); }
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    static void adminReportsMenu() {
        System.out.println("\n  --- REPORTS ---");
        System.out.println("  1. Occupancy Report");
        System.out.println("  2. Agent Performance Report");
        System.out.println("  3. Overdue Leases Report");
        System.out.println("  0. Back");

        int choice = readInt("  Enter choice: ");
        switch (choice) {
            case 1 -> manager.generateOccupancyReport();
            case 2 -> manager.generateAgentPerformanceReport();
            case 3 -> manager.listOverdueLeases();
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    // =================================================================
    // AGENT MENU
    // =================================================================

    static void agentMenu(User agentUser) {
        Agent agent = (Agent) agentUser.getLinkedPerson();
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║     AGENT PORTAL - " + String.format("%-22s", agent.getName()) + "║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. View Available Properties            ║");
            System.out.println("  ║  2. Search Properties by Type            ║");
            System.out.println("  ║  3. Create a Lease                       ║");
            System.out.println("  ║  4. View All Leases                      ║");
            System.out.println("  ║  5. Record a Payment                     ║");
            System.out.println("  ║  6. View My Profile                      ║");
            System.out.println("  ║  0. Logout                               ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            int choice = readInt("  Enter choice: ");
            switch (choice) {
                case 1 -> manager.listAvailableProperties();
                case 2 -> { String kw = readString("  Enter type: "); manager.searchPropertiesByType(kw); }
                case 3 -> createLease();
                case 4 -> manager.listAllLeases();
                case 5 -> {
                    String id    = readString("  Lease ID: ");
                    double amount = readDouble("  Amount (RM): ");
                    String method = readString("  Payment Method: ");
                    manager.recordPayment(id, amount, method);
                }
                case 6 -> { System.out.println("\n  ========== MY PROFILE =========="); agent.displayInfo(); }
                case 0 -> { agentUser.logout(); System.out.println("  Logged out successfully."); loggedIn = false; }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // =================================================================
    // TENANT MENU
    // =================================================================

    static void tenantMenu(User tenantUser) {
        Tenant tenant = (Tenant) tenantUser.getLinkedPerson();
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║     TENANT PORTAL - " + String.format("%-22s", tenant.getName()) + "║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. Browse Available Properties          ║");
            System.out.println("  ║  2. Search Properties by Type            ║");
            System.out.println("  ║  3. View My Lease Agreements             ║");
            System.out.println("  ║  4. View My Payment History              ║");
            System.out.println("  ║  5. View My Profile                      ║");
            System.out.println("  ║  0. Logout                               ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            int choice = readInt("  Enter choice: ");
            switch (choice) {
                case 1 -> manager.listAvailableProperties();
                case 2 -> { String kw = readString("  Enter type: "); manager.searchPropertiesByType(kw); }
                case 3 -> manager.listLeasesByTenant(tenant.getPersonId());
                case 4 -> {
                    manager.listLeasesByTenant(tenant.getPersonId());
                    String leaseId = readString("  Enter Lease ID to view payments: ");
                    manager.viewPaymentHistory(leaseId);
                }
                case 5 -> { System.out.println("\n  ========== MY PROFILE =========="); tenant.displayInfo(); }
                case 0 -> { tenantUser.logout(); System.out.println("  Logged out successfully."); loggedIn = false; }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // =================================================================
    // LANDLORD MENU  (NEW)
    // =================================================================

    static void landlordMenu(User landlordUser) {
        Landlord landlord = (Landlord) landlordUser.getLinkedPerson();
        boolean loggedIn  = true;

        while (loggedIn) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║   LANDLORD PORTAL - " + String.format("%-21s", landlord.getName()) + "║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. View My Properties                   ║");
            System.out.println("  ║  2. Edit My Property                     ║");
            System.out.println("  ║  3. View My Profile                      ║");
            System.out.println("  ║  0. Logout                               ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            int choice = readInt("  Enter choice: ");
            switch (choice) {
                case 1 -> viewLandlordProperties(landlord);
                case 2 -> editLandlordProperty(landlord);
                case 3 -> {
                    System.out.println("\n  ========== MY PROFILE ==========");
                    landlord.displayInfo();
                }
                case 0 -> {
                    landlordUser.logout();
                    System.out.println("  Logged out successfully.");
                    loggedIn = false;
                }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // ── LANDLORD FEATURE 1: View all owned properties ──────────────────────

    static void viewLandlordProperties(Landlord landlord) {
        System.out.println("\n  ========== MY PROPERTIES ==========");

        if (landlord.getOwnedProperties().isEmpty()) {
            System.out.println("  You have no properties registered.");
            return;
        }

        for (Property p : landlord.getOwnedProperties()) {
            p.displayInfo();
            System.out.println("  ------------------------------------");
        }

        // Portfolio summary
        long rented    = landlord.getOwnedProperties().stream()
                .filter(p -> p.getStatus() == Property.PropertyStatus.RENTED).count();
        long available = landlord.getOwnedProperties().stream()
                .filter(p -> p.getStatus() == Property.PropertyStatus.AVAILABLE).count();
        long sold      = landlord.getOwnedProperties().stream()
                .filter(p -> p.getStatus() == Property.PropertyStatus.SOLD).count();

        System.out.println("\n  Portfolio Summary:");
        System.out.println("  Total: " + landlord.getOwnedProperties().size()
                + "  |  Available: " + available
                + "  |  Rented: "    + rented
                + "  |  Sold: "      + sold);
    }

    // ── LANDLORD FEATURE 2: Edit one of their owned properties ─────────────

    static void editLandlordProperty(Landlord landlord) {
        if (landlord.getOwnedProperties().isEmpty()) {
            System.out.println("  You have no properties to edit.");
            return;
        }

        // Show their list first so they know the IDs
        viewLandlordProperties(landlord);

        String inputId = readString("\n  Enter Property ID to edit (or 0 to cancel): ");
        if (inputId.equals("0")) return;

        // Security check — must belong to THIS landlord
        Property target = landlord.findOwnedPropertyById(inputId);
        if (target == null) {
            System.out.println("  [ERROR] Property not found in your portfolio.");
            return;
        }

        if (target instanceof ResidentialProperty rp) {
            editResidential(rp);
        } else if (target instanceof CommercialProperty cp) {
            editCommercial(cp);
        }
    }

    static void editResidential(ResidentialProperty rp) {
        System.out.println("\n  Editing: " + rp.getPropertyId() + " - " + rp.getLocation());
        System.out.println("  1. Location      2. Price (RM)    3. Status");
        System.out.println("  0. Back");

        int choice = readInt("  Choose field: ");
        switch (choice) {
            case 1 -> {
                String newLoc = readString("  New location: ");
                rp.setLocation(newLoc);
                System.out.println("  [SUCCESS] Location updated.");
            }
            case 2 -> {
                double newPrice = readDouble("  New price (RM): ");
                rp.setListingPrice(newPrice);
                System.out.println("  [SUCCESS] Price updated.");
            }
            case 3 -> {
                System.out.println("  1. AVAILABLE   2. RENTED   3. SOLD");
                int s = readInt("  Choose: ");
                switch (s) {
                    case 1 -> rp.setStatus(Property.PropertyStatus.AVAILABLE);
                    case 2 -> rp.setStatus(Property.PropertyStatus.RENTED);
                    case 3 -> rp.setStatus(Property.PropertyStatus.SOLD);
                    default -> System.out.println("  [ERROR] Invalid status.");
                }
                System.out.println("  [SUCCESS] Status updated.");
            }
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid choice.");
        }
    }

    static void editCommercial(CommercialProperty cp) {
        System.out.println("\n  Editing: " + cp.getPropertyId() + " - " + cp.getLocation());
        System.out.println("  1. Location      2. Price (RM)    3. Status");
        System.out.println("  0. Back");

        int choice = readInt("  Choose field: ");
        switch (choice) {
            case 1 -> {
                String newLoc = readString("  New location: ");
                cp.setLocation(newLoc);
                System.out.println("  [SUCCESS] Location updated.");
            }
            case 2 -> {
                double newPrice = readDouble("  New price (RM): ");
                cp.setListingPrice(newPrice);
                System.out.println("  [SUCCESS] Price updated.");
            }
            case 3 -> {
                System.out.println("  1. AVAILABLE   2. RENTED   3. SOLD");
                int s = readInt("  Choose: ");
                switch (s) {
                    case 1 -> cp.setStatus(Property.PropertyStatus.AVAILABLE);
                    case 2 -> cp.setStatus(Property.PropertyStatus.RENTED);
                    case 3 -> cp.setStatus(Property.PropertyStatus.SOLD);
                    default -> System.out.println("  [ERROR] Invalid status.");
                }
                System.out.println("  [SUCCESS] Status updated.");
            }
            case 0 -> { }
            default -> System.out.println("  [ERROR] Invalid choice.");
        }
    }

    // =================================================================
    // SAMPLE DATA LOADER
    // =================================================================

    static void loadSampleData() {
        System.out.println("\n  Loading sample data...");

        // Admin
        manager.addUser(new User("admin", "admin123", User.Role.ADMIN));

        // Agents
        manager.registerAgent("Alice Wong", "alice@realty.com", "0121234567", "REN12345", "alice", "alice123");

        // Tenants
        manager.registerTenant("Bob Tan",   "bob@email.com",   "0197654321", "880101-01-1234", "bob",   "bob123");
        manager.registerTenant("Carol Lim", "carol@email.com", "0139876543", "920202-02-5678", "carol", "carol123");

        // Landlords (NEW)
        Landlord ahmad = manager.registerLandlord("Ahmad Razali", "ahmad@mail.com",
                "0121112222", "ahmad", "ahmad123");
        Landlord siti  = manager.registerLandlord("Siti Noraini", "siti@mail.com",
                "0197778888", "siti", "siti123");

        // Properties — some linked to landlords
        ResidentialProperty p1 = manager.addResidentialProperty(
                "Johor Bahru City Centre", 1500, 850,
                ResidentialProperty.ResidentialType.APARTMENT, 3, 2, true);
        if (ahmad != null) ahmad.addOwnedProperty(p1);

        ResidentialProperty p2 = manager.addResidentialProperty(
                "Iskandar Puteri, Johor", 3500, 3200,
                ResidentialProperty.ResidentialType.VILLA, 5, 4, true);
        if (ahmad != null) ahmad.addOwnedProperty(p2);

        ResidentialProperty p3 = manager.addResidentialProperty(
                "Taman Molek, JB", 900, 600,
                ResidentialProperty.ResidentialType.APARTMENT, 2, 1, false);
        if (siti != null) siti.addOwnedProperty(p3);

        CommercialProperty p4 = manager.addCommercialProperty(
                "Taman Pelangi, JB", 5000, 2000,
                CommercialProperty.CommercialType.OFFICE, 10, false);
        if (siti != null) siti.addOwnedProperty(p4);

        manager.addCommercialProperty("Pasir Gudang Industrial Area", 8000, 10000,
                CommercialProperty.CommercialType.WAREHOUSE, 20, true);

        // Leases
        manager.createLease("PROP001", "TEN001", "AGT001",
                LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 1500.0);
        manager.createLease("PROP004", "TEN002", "AGT001",
                LocalDate.of(2025, 6, 1), LocalDate.of(2026, 6, 1), 5000.0);

        // Payments
        manager.recordPayment("LEASE001", 1500.0, "Online Banking");
        manager.recordPayment("LEASE001", 1500.0, "Bank Transfer");
        manager.recordPayment("LEASE002", 5000.0, "Online Banking");

        System.out.println("  Sample data loaded!\n");
        System.out.println("  ==========================================");
        System.out.println("   DEMO ACCOUNTS:");
        System.out.println("   Admin    -> username: admin  | password: admin123");
        System.out.println("   Agent    -> username: alice  | password: alice123");
        System.out.println("   Tenant   -> username: bob    | password: bob123");
        System.out.println("   Tenant   -> username: carol  | password: carol123");
        System.out.println("   Landlord -> username: ahmad  | password: ahmad123");
        System.out.println("   Landlord -> username: siti   | password: siti123");
        System.out.println("  ==========================================");
    }

    // =================================================================
    // INPUT HELPERS
    // =================================================================

    static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = sc.nextInt();
                sc.nextLine();
                return val;
            } catch (InputMismatchException e) {
                System.out.println("  [ERROR] Please enter a whole number.");
                sc.nextLine();
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = sc.nextDouble();
                sc.nextLine();
                return val;
            } catch (InputMismatchException e) {
                System.out.println("  [ERROR] Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

    static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
