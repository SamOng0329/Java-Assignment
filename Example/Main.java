package Example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main entry point for the Property Rental & Real Estate System.
 *
 * Features:
 * - Login page with 3 roles: ADMIN, AGENT, TENANT
 * - Role-based menus (each role sees different options)
 * - Full CRUD for all modules
 * - Exception handling throughout
 */
public class Main {

    private static PropertyManager manager = new PropertyManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData(); // Load demo data on startup

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
                case 0 -> { System.out.println("\n  Goodbye! Thank you for using our system."); running = false; }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // =================================================================
    //  LOGIN & REGISTRATION
    // =================================================================

    static void printWelcomeBanner() {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║   PROPERTY RENTAL & REAL ESTATE SYSTEM   ║");
        System.out.println("  ║         Welcome! Please log in.          ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    /**
     * Handles the login process. Shows a role-specific menu on success.
     * Allows 3 attempts before returning to main menu.
     */
    static void handleLogin() {
        System.out.println("\n  ========== LOGIN ==========");
        int attempts = 0;

        while (attempts < 3) {
            String username = readString("  Username: ");
            String password = readString("  Password: ");

            User loggedInUser = manager.login(username, password);

            if (loggedInUser != null) {
                // Direct to the correct menu based on role
                switch (loggedInUser.getRole()) {
                    case ADMIN  -> adminMenu(loggedInUser);
                    case AGENT  -> agentMenu(loggedInUser);
                    case TENANT -> tenantMenu(loggedInUser);
                }
                return; // Go back to main menu after logout
            }

            attempts++;
            int remaining = 3 - attempts;
            if (remaining > 0) {
                System.out.println("  [WARNING] " + remaining + " attempt(s) remaining.");
            }
        }
        System.out.println("  [ERROR] Too many failed attempts. Returning to main menu.");
    }
        
    /**
     * Allows a new user to register as a Tenant from the main menu.
     */
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
    //  ADMIN MENU
    // =================================================================

    /**
     * Full-access menu for Admin users.
     * Admin can manage properties, tenants, agents, leases, and view reports.
     */
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
                case 0 -> { admin.logout(); System.out.println("  Logged out successfully."); loggedIn = false; }
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
                String id    = readString("  Property ID: ");
                double price = readDouble("  New Price (RM): ");
                manager.updatePropertyPrice(id, price);
            }
            case 7 -> {
                String id = readString("  Property ID to delete: ");
                manager.deleteProperty(id);
            }
            case 0 -> {}
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    static void addResidentialProperty() {
        try {
            System.out.println("\n  -- Add Residential Property --");
            String location = readString("  Location: ");
            double price    = readDouble("  Monthly Price (RM): ");
            double sqft     = readDouble("  Square Footage: ");

            System.out.println("  Sub-type: 1. Apartment   2. Villa");
            int t = readInt("  Choose: ");
            ResidentialProperty.ResidentialType type =
                    (t == 1) ? ResidentialProperty.ResidentialType.APARTMENT
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
            CommercialProperty.CommercialType type =
                    (t == 1) ? CommercialProperty.CommercialType.OFFICE
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
                String id    = readString("  Tenant ID: ");
                String email = readString("  New Email: ");
                String phone = readString("  New Phone: ");
                manager.updateTenantContact(id, email, phone);
            }
            case 0 -> {}
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
            case 0 -> {}
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
            case 0 -> {}
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
                String id     = readString("  Lease ID: ");
                double amount = readDouble("  Amount Paid (RM): ");
                String method = readString("  Payment Method (Cash/Online/Bank Transfer): ");
                manager.recordPayment(id, amount, method);
            }
            case 2 -> { String id = readString("  Lease ID: "); manager.viewPaymentHistory(id); }
            case 0 -> {}
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
            case 0 -> {}
            default -> System.out.println("  [ERROR] Invalid option.");
        }
    }

    // =================================================================
    //  AGENT MENU (Limited access)
    // =================================================================

    /**
     * Agent can: view/search properties, create leases, view their own deals.
     */
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
                case 2 -> {
                    String kw = readString("  Enter type (apartment/villa/office/warehouse): ");
                    manager.searchPropertiesByType(kw);
                }
                case 3 -> createLease();
                case 4 -> manager.listAllLeases();
                case 5 -> {
                    String id     = readString("  Lease ID: ");
                    double amount = readDouble("  Amount (RM): ");
                    String method = readString("  Payment Method: ");
                    manager.recordPayment(id, amount, method);
                }
                case 6 -> {
                    System.out.println("\n  ========== MY PROFILE ==========");
                    agent.displayInfo();
                }
                case 0 -> { agentUser.logout(); System.out.println("  Logged out successfully."); loggedIn = false; }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // =================================================================
    //  TENANT MENU (Restricted access)
    // =================================================================

    /**
     * Tenant can: browse properties, view own leases and payments.
     * Tenants CANNOT create leases or manage agents/other tenants.
     */
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
                case 2 -> {
                    String kw = readString("  Enter type (apartment/villa/office/warehouse): ");
                    manager.searchPropertiesByType(kw);
                }
                case 3 -> manager.listLeasesByTenant(tenant.getPersonId());
                case 4 -> {
                    // Let tenant enter a lease ID to view its payments
                    manager.listLeasesByTenant(tenant.getPersonId());
                    String leaseId = readString("  Enter your Lease ID to view payments: ");
                    manager.viewPaymentHistory(leaseId);
                }
                case 5 -> {
                    System.out.println("\n  ========== MY PROFILE ==========");
                    tenant.displayInfo();
                }
                case 0 -> { tenantUser.logout(); System.out.println("  Logged out successfully."); loggedIn = false; }
                default -> System.out.println("  [ERROR] Invalid option.");
            }
        }
    }

    // =================================================================
    //  SAMPLE DATA LOADER
    // =================================================================

    static void loadSampleData() {
        System.out.println("\n  Loading sample data...");

        // Create Admin account (no linked person needed)
        manager.addUser(new User("admin", "admin123", User.Role.ADMIN));

        // Register Agents (creates linked user accounts automatically)
        Agent alice = manager.registerAgent("Alice Wong", "alice@realty.com",
                "0121234567", "REN12345", "alice", "alice123");

        // Register Tenants (creates linked user accounts automatically)
        Tenant bob = manager.registerTenant("Bob Tan", "bob@email.com",
                "0197654321", "880101-01-1234", "bob", "bob123");
        Tenant carol = manager.registerTenant("Carol Lim", "carol@email.com",
                "0139876543", "920202-02-5678", "carol", "carol123");

        // Add Properties
        manager.addResidentialProperty("Johor Bahru City Centre", 1500, 850,
                ResidentialProperty.ResidentialType.APARTMENT, 3, 2, true);
        manager.addResidentialProperty("Iskandar Puteri, Johor", 3500, 3200,
                ResidentialProperty.ResidentialType.VILLA, 5, 4, true);
        manager.addResidentialProperty("Taman Molek, JB", 900, 600,
                ResidentialProperty.ResidentialType.APARTMENT, 2, 1, false);
        manager.addCommercialProperty("Taman Pelangi, JB", 5000, 2000,
                CommercialProperty.CommercialType.OFFICE, 10, false);
        manager.addCommercialProperty("Pasir Gudang Industrial Area", 8000, 10000,
                CommercialProperty.CommercialType.WAREHOUSE, 20, true);

        // Create Leases
        manager.createLease("PROP001", "TEN001", "AGT001",
                LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 1500.0);
        manager.createLease("PROP004", "TEN002", "AGT001",
                LocalDate.of(2025, 6, 1), LocalDate.of(2026, 6, 1), 5000.0);

        // Record Payments
        manager.recordPayment("LEASE001", 1500.0, "Online Banking");
        manager.recordPayment("LEASE001", 1500.0, "Bank Transfer");
        manager.recordPayment("LEASE002", 5000.0, "Online Banking");

        System.out.println("  Sample data loaded!\n");
        System.out.println("  ==========================================");
        System.out.println("   DEMO ACCOUNTS:");
        System.out.println("   Admin  -> username: admin   | password: admin123");
        System.out.println("   Agent  -> username: alice   | password: alice123");
        System.out.println("   Tenant -> username: bob     | password: bob123");
        System.out.println("   Tenant -> username: carol   | password: carol123");
        System.out.println("  ==========================================");
    }

    // =================================================================
    //  INPUT HELPERS
    // =================================================================

    /**
     * Reads an integer from the user. Loops until valid input is entered.
     */
    static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = sc.nextInt();
                sc.nextLine(); // Clear buffer
                return val;
            } catch (InputMismatchException e) {
                System.out.println("  [ERROR] Please enter a whole number.");
                sc.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Reads a double from the user. Loops until valid input is entered.
     */
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

    /**
     * Reads a non-empty string from the user.
     */
    static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
