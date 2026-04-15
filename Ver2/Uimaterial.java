public class UiMaterial {

    // Banner
    public static void displayBanner() {
        System.out.println(
                "  _  _                                               _                        _                   ");
        System.out.println(
                " | || |    ___    _  _     ___     ___      o O O   | |    __ _    _ _     __| |    ___      _ _  ");
        System.out.println(
                " | __ |   / _ \\  | +| |   (_-<    / -_)    o        | |   / _` |  | ' \\   / _` |   / -_)    | '_| ");
        System.out.println(
                " |_||_|   \\___/   \\_,_|   /__/_   \\___|   TS__[O]  _|_|_  \\__,_|  |_||_|  \\__,_|   \\___|   _|_|_  ");
        System.out.println(
                "_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| ");
        System.out.println(
                "\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'  ");
        System.out.println("\n ");
        System.out.println("Welcome to House lander v1.0");
        System.out.println("----------------------------\n");
    }

    // Operation Menu for each user
    public static void displayoperationmenu(UserRole role) {

        System.out.println("\nChoose an option: ");
        if (role.getRole().equals(UserRole.Role.TENANT)) {
            System.out.println("=".repeat(30));
            System.out.println("1. View Available Properties");
            System.out.println("2. Manage Rental status");
            System.out.println("3. Manage Visit Requests");
            System.out.println("4. Edit profile");
            System.out.println("5. Show profile");
            System.out.println("6. Logout");
            System.out.println("=".repeat(30));
        } else if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("=".repeat(30));
            System.out.println("1. Manage Properties");
            System.out.println("2. Manage Users");
            System.out.println("3. Manage Payment");
            System.out.println("4. Manage Lease");
            System.out.println("5. View Reports");
            System.out.println("6. Manage Visit Requests");
            System.out.println("7. Show profile");
            System.out.println("8. Logout");
            System.out.println("=".repeat(30));
        } else if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("=".repeat(30));
            System.out.println("1. Property Management");
            System.out.println("2. Manage Lease");
            System.out.println("3. Manage Visit Requests");
            System.out.println("4. Manage Payment");
            System.out.println("5. User management");
            System.out.println("6. Edit profile");
            System.out.println("7. Show profile");
            System.out.println("8. Logout");
            System.out.println("=".repeat(30));
        } else if (role.getRole().equals(UserRole.Role.LANDLORD)) {
            System.out.println("=".repeat(30));
            System.out.println("1. Property Management");
            System.out.println("2. Manage Lease");
            System.out.println("3. Manage Payment");
            System.out.println("4. Edit profile");
            System.out.println("5. Show profile");
            System.out.println("6. Logout");
            System.out.println("=".repeat(30));
        }
    }

    // =========================================================================================
    // User Management Menu
    public static void displayUserManagementMenu(UserRole role) {
        if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("\n===== User Management =====");
            System.out.println("1. Manage Tenant");
            System.out.println("2. Manage Landlord");
            System.out.println("3. Manage Agent");
            System.out.println("0. Back to Main Menu");
        }

        else if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("\n===== User Management =====");
            System.out.println("1. View Tenant List");
            System.out.println("2. View Landlord List");
            System.out.println("0. Back to Main Menu");
        }
    }

    // Manage Tenant Menu
    public static void displayManageTenantMenu() {
        System.out.println("\n===== Manage Tenant =====");
        System.out.println("1. List of Tenant");
        System.out.println("2. Register Tenant");
        System.out.println("3. Edit Tenant Profile");
        System.out.println("4. Delete Tenant Profile");
        System.out.println("0. Back to User Management Menu");
    }

    // Manage Landlord Menu
    public static void displayManageLandlordMenu() {
        System.out.println("\n===== Manage Landlord =====");
        System.out.println("1. List of Landlord");
        System.out.println("2. Register Landlord");
        System.out.println("3. Edit Landlord Profile");
        System.out.println("4. Delete Landlord Profile");
        System.out.println("0. Back to User Management Menu");
    }

    // Manage Agent Menu
    public static void displayManageAgentMenu() {
        System.out.println("\n===== Manage Agent =====");
        System.out.println("1. List of Agent");
        System.out.println("2. Register Agent");
        System.out.println("3. Edit Agent Profile");
        System.out.println("4. Delete Agent Profile");
        System.out.println("0. Back to User Management Menu");
    }

    // Registration Menu (used in StaffMain)
    public static void RegisterMenu(UserRole role) {
        System.out.println("\n===== Register User =====");
        System.out.println("1. Register Agent");
        System.out.println("2. Register Landlord");
        System.out.println("3. Register Tenant");
        System.out.println("4. Back to Main Menu");
    }

    // Edit Profile First Menu (used in StaffMain)
    public static void editprofileFirstMenu(UserRole role) {
        System.out.println("\n===== Edit Profile =====");
        System.out.println("1. Edit Tenant Profile");
        System.out.println("2. Edit Landlord Profile");
        System.out.println("3. Edit Agent Profile");
        System.out.println("4. Back to Main Menu");
    }

    // =========================================================================================

    // Edit Profile Menu for each user
    public static void editprofileSecondMenu(UserRole.Role role) {

        System.out.println("Choose an option:");

        if (role.equals(UserRole.Role.AGENT)) {
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Email");
            System.out.println("3. Edit Phone Number");
            System.out.print("Choice(4 to go back): ");

        } else if (role.equals(UserRole.Role.TENANT)) {
            System.out.println("1. Edit Name");
            System.out.println("2. Edit IC Number");
            System.out.println("3. Edit Email");
            System.out.println("4. Edit Phone Number");
            System.out.print("Choice(5 to go back): ");
        }

        else if (role.equals(UserRole.Role.LANDLORD)) {
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Email");
            System.out.println("3. Edit Phone Number");
            System.out.print("Choice(4 to go back): ");
        }
    }

    // =========================================================================================
    public static void displayPaymentManagementMenu(UserRole loggedUser) {

        if(loggedUser.getRole().equals(UserRole.Role.AGENT)||loggedUser.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("\n--- PAYMENT MANAGEMENT ---");
            System.out.println("1. View all Payment History");
            System.out.println("2. Record a Payment");
            System.out.println("3. View Payment History for a Lease");
            System.out.println("4. View Payment History for a Tenant");
            System.out.println("0. Back");
        }

        else if(loggedUser.getRole().equals(UserRole.Role.LANDLORD)) {
            System.out.println("\n--- PAYMENT MANAGEMENT ---");
            System.out.println("1. View Payment History for My Properties");
            System.out.println("0. Back");
        }
    }
    // =========================================================================================

    // Property Management Menu
    // =========================================================================================
    public static void displayPropertyManagementMenu() {
        System.out.println("\n--- PROPERTY MANAGEMENT ---");
        System.out.println("1. View all Properties");
        System.out.println("2. Add Residential Property");
        System.out.println("3. Add Commercial Property");
        System.out.println("4. Update Property Details");
        System.out.println("5. Update Property Status");
        System.out.println("6. Delete Property");
        System.out.println("0. Back");
    }
    // =========================================================================================

    // Lease Management Menu
    // =========================================================================================
    public static void displayLeaseManagementMenu(UserRole role) {
        if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("\n--- LEASE MANAGEMENT ---");
            System.out.println("1. Create a Lease");
            System.out.println("2. View all Lease");
            System.out.println("3. View Overdue Lease");
            System.out.println("4. Terminate a Lease");
            System.out.println("5. Accept Lease Request");
            System.out.println("0. Back");
        } 
        else if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("\n--- LEASE MANAGEMENT ---");
            System.out.println("1. Create a Lease");
            System.out.println("2. View all Lease");
            System.out.println("3. View Overdue Lease");
            System.out.println("4. Terminate a Lease");
            System.out.println("0. Back");
        }

        else if (role.getRole().equals(UserRole.Role.LANDLORD)) {
            System.out.println("\n--- LEASE MANAGEMENT ---");
            System.out.println("1. View Current Leases for My Properties");
            System.out.println("2. View Overdue Leases for My Properties");
            System.out.println("0. Back");
        }
        else if (role.getRole().equals(UserRole.Role.TENANT)) {
            System.out.println("\n--- LEASE MANAGEMENT ---");
            System.out.println("1. View My Lease");
            System.out.println("2. New Rental Application");
            System.out.println("3. View Payment History for My Lease");
            System.out.println("0. Back");
        }
    }
    // =========================================================================================

    // Payment Management Menu
    // =========================================================================================

    // =========================================================================================

    // Report Menu
    // =========================================================================================
    public static void displayReportMenu() {
        System.out.println("\n--- REPORT MANAGEMENT ---");
        System.out.println("1. Property Report");
        System.out.println("2. Agent Performance Report");
        System.out.println("0. Back");
    }
    // =========================================================================================


    public static void displayVisitRequestMenu(UserRole role) {
        if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("\n--- VISIT REQUEST MANAGEMENT ---");
            System.out.println("1. View all Visit Requests");
            System.out.println("2. Delete a Visit Request");
            System.out.println("0. Back");
        } else if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("\n--- VISIT REQUEST MANAGEMENT ---");
            System.out.println("1. View all Visit Requests");
            System.out.println("2. Approve/Reject a Visit Request");
            System.out.println("0. Back");
        } else if(role.getRole().equals(UserRole.Role.TENANT)) {
            System.out.println("\n--- VISIT REQUEST MANAGEMENT ---");
            System.out.println("1. View My Visit Requests");
            System.out.println("2. Create a Visit Request");
            System.out.println("0. Back");
        }


    }


    public static void displayVisitRequestStatus(VisitRequest visitRequest) {
        System.out.println("\n--- VISIT REQUEST STATUS ---");
        System.out.println("Request ID: " + visitRequest.getRequestId());
        System.out.println("Tenant ID: " + visitRequest.getTenant().getTenantId());
        System.out.println("Property ID: " + visitRequest.getProperty().getPropertyId());
        System.out.println("Visit Date: " + visitRequest.getVisitDate());
        System.out.println("Status: " + visitRequest.getStatus());
    }


    
}
