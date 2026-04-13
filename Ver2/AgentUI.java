import java.time.LocalDate;
import java.util.Scanner;

import files1.User;

public class AgentUI {
   public void agentMenu(UserRole loggedUser) {
        PropertyFunction pf = PropertyFunction.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean logout = false;

        do {
            try {
                Uimaterial.displayoperationmenu(loggedUser); // Calling the operation menu from UiMaterial class
                System.out.print("Enter your choice:");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();

                switch (choice) {
                    case 1 -> manageProperty(sc, pf);
                    case 2 -> manageLease(sc, pf, loggedUser);
                    case 3 -> manageVisitRequest(sc, pf, loggedUser);
                    case 4 -> managePayment(sc, pf);
                    case 5 -> userManagement(loggedUser, sc, pf);
                    case 6 -> editAgentProfile(sc, pf, loggedUser);
                    case 7 -> logout = true;
                    default -> System.out.println("Error! ");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!logout);
    }


    // Property management
    // ==========================================================
        public static void manageProperty(Scanner sc, PropertyFunction pf) {
        boolean back = false;
        do {
            try {
                Uimaterial.displayPropertyManagementMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();
                switch (choice) {
                    case 1 -> pf.listAllProperties();
                    case 2 -> addResidentialProperty(sc, pf);
                    case 3 -> addCommercialProperty(sc, pf);
                    case 4 -> updatePropertyPrice(sc, pf);
                    case 5 -> updatePropertyStatus(sc, pf);
                    case 6 -> deleteProperty(sc, pf);
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    public static void addResidentialProperty(Scanner sc, PropertyFunction pf) {
        try {
            System.out.println("\n--- Add Residential Property ---");
            System.out.print("Location: ");
            String location = sc.nextLine();
            System.out.print("Rent Price: ");
            double price = sc.nextDouble();
            System.out.print("Size (sqft): ");
            double sqft = sc.nextDouble();
            sc.nextLine();

            ResidentialProperty.ResidentialType resType = null;
            do {
                System.out.print("Type (1. APARTMENT, 2. HOUSE): ");
                int typeChoice = sc.nextInt();
                resType = switch (typeChoice) {
                    case 1 -> ResidentialProperty.ResidentialType.APARTMENT;
                    case 2 -> ResidentialProperty.ResidentialType.HOUSE;
                    default -> {
                        System.out.println("\nInvalid choice. Please try again.\n");
                        yield null;
                    }
                };
            } while (resType == null);

            System.out.print("Bedrooms: ");
            int bed = sc.nextInt();
            System.out.print("Bathrooms: ");
            int bath = sc.nextInt();
            System.out.print("Furnished (true/false): ");
            boolean furnished = sc.nextBoolean();
            sc.nextLine();

            pf.addResidentialProperty(location, price, sqft, resType, bed, bath, furnished);
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void addCommercialProperty(Scanner sc, PropertyFunction pf) {
        try {
            System.out.println("\n--- Add Commercial Property ---");
            System.out.print("Location: ");
            String location = sc.nextLine();
            System.out.print("Rent Price: ");
            double price = sc.nextDouble();
            System.out.print("Size (sqft): ");
            double sqft = sc.nextDouble();
            sc.nextLine();

            CommercialProperty.CommercialType commType = null;
            do{
                System.out.print("Type (1. OFFICE, 2. RETAIL, 3. INDUSTRIAL): ");
                int typeChoice = sc.nextInt();
                commType = switch (typeChoice) {
                    case 1 -> CommercialProperty.CommercialType.OFFICE;
                    case 2 -> CommercialProperty.CommercialType.RETAIL;
                    case 3 -> CommercialProperty.CommercialType.INDUSTRIAL;
                    default -> {System.out.println("\nInvalid choice. Please try again.\n");
                        yield null;}
                };
            } while (commType == null) ;

            System.out.print("Parking Spaces: ");
            int parking = sc.nextInt();
            System.out.print("Loading Dock (true/false): ");
            boolean loadingDock = sc.nextBoolean();
            sc.nextLine();

            pf.addCommercialProperty(location, price, sqft, commType, parking, loadingDock);
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void updatePropertyPrice(Scanner sc, PropertyFunction pf) {
        try {
            System.out.println("\n--- Update Property Rent Price ---");
            System.out.print("Property ID: ");
            String id = sc.nextLine();
            System.out.print("New Rent Price: ");
            double newPrice = sc.nextDouble();
            sc.nextLine();
            if (pf.updatePropertyPrice(id, newPrice)) {
                System.out.println("Rent price updated successfully!");
            } else {
                System.out.println("Update failed. Property not found.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void updatePropertyStatus(Scanner sc, PropertyFunction pf) {
        System.out.println("\n--- Update Property Status ---");
        System.out.print("Property ID: ");
        String id = sc.nextLine();
        System.out.print("Status (1. AVAILABLE, 2. RENTED, 3. SOLD): ");
        try {
            int statChoice = sc.nextInt();
            sc.nextLine();
            Property.PropertyStatus status = switch (statChoice) {
                case 1 -> Property.PropertyStatus.AVAILABLE;
                case 2 -> Property.PropertyStatus.RENTED;
                case 3 -> Property.PropertyStatus.SOLD;
                default -> null;
            };
            if (status != null) {
                pf.updatePropertyStatus(id, status);
            } else {
                System.out.println("Invalid status!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void deleteProperty(Scanner sc, PropertyFunction pf) {
        System.out.println("\n--- Delete Property ---");
        System.out.print("Property ID: ");
        String id = sc.nextLine();
        pf.deleteProperty(id);
    }





    // Lease management
    // ==========================================================
    public static void manageLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean back = false;
        do {
            Uimaterial.displayLeaseManagementMenu();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            Uimaterial.clearscreen();
            switch (choice) {
                case 1 -> createLease(sc, pf);
                case 2 -> viewAllLease(sc, pf, loggedUser);
                case 3 -> viewOverdueLease(sc, pf, loggedUser);
                case 4 -> terminateLease(sc, pf, (Agent) loggedUser.getLinkedPerson());
                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (!back);
    }

    // Create lease
    public static void createLease(Scanner sc, PropertyFunction pf) {
        try {
            System.out.print("\n--- Create Lease ---\n");
            System.out.print("Property ID: ");
            String propId = sc.nextLine();
            System.out.print("Tenant ID: ");
            String tenantId = sc.nextLine();
            System.out.print("Agent ID (press Enter to skip): ");
            String agentId = sc.nextLine();

            if (agentId.isEmpty()) {
                agentId = null;
            }

            System.out.print("Start Date (YYYY-MM-DD): ");
            String startDate = sc.nextLine();
            LocalDate start = LocalDate.parse(startDate);
            System.out.print("END Date   (YYYY-MM-DD): ");
            String endDate = sc.nextLine();
            LocalDate end = LocalDate.parse(endDate);
            double rent = pf.findPropertyById(propId).getListingPrice();
            pf.createLeaseRecord(propId, tenantId, agentId, start, end, rent);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine();
        }

    }

    // View All Leases
    public static void viewAllLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean found = false;
        for (LeaseRecord lease : pf.getLeaseRecordList()) {

            if (lease.getAgent() != null && lease.getAgent().getPersonId().equals(loggedUser.getLinkedPerson().getPersonId())) {
                 lease.displayInfo();
                 found = true;
            }
        }
        if (!found) {
            System.out.println("No leases found.");
        }
    }

    // View Overdue Leases
    public static void viewOverdueLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean found = false;
        LocalDate today = LocalDate.now();
        for (LeaseRecord lease : pf.getLeaseRecordList()) {
            if (!lease.getLeaseStatus() && lease.getEndDate().isBefore(today)) {
                if (lease.getAgent() != null && lease.getAgent().getPersonId().equals(loggedUser.getLinkedPerson().getPersonId())) {
                    System.out.println("\n[OVERDUE LEASE]");
                    lease.displayInfo();
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No overdue leases found.");
        }
    }

    // Terminate Lease
    public static void terminateLease(Scanner sc, PropertyFunction pf, Agent agent) {
        System.out.print("Enter Lease ID to terminate: ");
        String leaseId = sc.nextLine();
        LeaseRecord lease = pf.findLeaseById(leaseId);
        if (lease == null) {
            System.out.println("Lease not found.");
            return;
        }
        if (lease.getLeaseStatus()) {
            System.out.println("Lease is already terminated.");
            return;
        }
        pf.AgentLeaseTermination(leaseId, agent);
        System.out.println("[SUCCESS] Lease " + leaseId + " has been terminated.");
    }




    public static void manageVisitRequest(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean back = false;
        do {
            try {
                Uimaterial.displayVisitRequestMenu(loggedUser);
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();
                switch (choice) {
                    case 1 -> ListVisitRequests(sc, pf, loggedUser);
                    case 2 -> {
                        System.out.print("Enter Visit Request ID for operation: ");
                        String requestId = sc.nextLine();
                        System.out.print("Enter approval decision (true/false): ");
                        boolean isApproved = sc.nextBoolean();
                        sc.nextLine();
                        pf.processVisitRequest(requestId, isApproved, loggedUser);
                    }
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }


    public static void ListVisitRequests(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean found = false;
        for (VisitRequest vr : pf.getVisitRequestList()) {
            if (vr.getAgent() != null && vr.getAgent().getPersonId().equals(loggedUser.getLinkedPerson().getPersonId())) {
                vr.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No visit requests found.");
        }
    }


    public static void managePayment(Scanner sc, PropertyFunction pf) {
        boolean back = false;
        do {
            try {
                Uimaterial.displayPaymentManagementMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();
                switch (choice) {
                    case 1 -> pf.listAllPayments();
                    case 2 -> createPayment(sc, pf);
                    case 3 -> viewPaymentByLease(sc, pf);
                    case 4 -> viewPaymentByTenant(sc, pf);
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    public static void createPayment(Scanner sc, PropertyFunction pf) {
        try {
            System.out.println("\n--- Create Payment ---");
            System.out.print("Lease ID: ");
            String leaseId = sc.nextLine();
            LeaseRecord lease = pf.findLeaseById(leaseId);
            if (lease == null) {
                System.out.println("Lease not found.");
                return;
            }
            // Show lease summary to help staff confirm
            System.out.println("Lease found — Tenant: " + lease.getTenant().getName()
                    + " | Monthly Rent: RM " + String.format("%.2f", lease.getMonthlyRent()));
            System.out.print("Amount (RM): ");
            double amount = sc.nextDouble();
            sc.nextLine();
            System.out.println("Payment Method:");
            System.out.println("  1. Cash");
            System.out.println("  2. Online Transfer");
            System.out.println("  3. Cheque");
            System.out.print("Enter choice: ");
            int methodChoice = sc.nextInt();
            sc.nextLine();
            String paymentMethod = switch (methodChoice) {
                case 1 -> "Cash";
                case 2 -> "Online Transfer";
                case 3 -> "Cheque";
                default -> "Other";
            };
            pf.createPaymentRecord(leaseId, amount, paymentMethod);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine();
        }
    }

    // View Payment Records by Lease ID
    public static void viewPaymentByLease(Scanner sc, PropertyFunction pf) {
        System.out.print("Enter Lease ID: ");
        String leaseId = sc.nextLine();
        if (pf.findLeaseById(leaseId) == null) {
            System.out.println("Lease not found.");
            return;
        }
        pf.listPaymentsByLeaseId(leaseId);
    }

    // View Payment Records by Tenant ID
    public static void viewPaymentByTenant(Scanner sc, PropertyFunction pf) {
        System.out.print("Enter Tenant ID: ");
        String tenantId = sc.nextLine();
        if (pf.findTenantById(tenantId) == null) {
            System.out.println("Tenant not found.");
            return;
        }
        pf.listPaymentsByTenantId(tenantId);
    }



    public static void userManagement(UserRole loggedUser, Scanner sc, PropertyFunction pf) {
        boolean back = false;

        do {
            try {
                Uimaterial.displayUserManagementMenu(loggedUser);
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();

                switch (choice) {
                    case 1 -> manageTenant(sc, pf);
                    case 2 -> manageLandlord(sc, pf);
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }
    // ==========================================================

    // Function for Staff
    // Manage Tenant Function
    // ==========================================================
    public static void manageTenant(Scanner sc, PropertyFunction pf) {
        boolean back = false;

        do {
            try {
                Uimaterial.displayManageTenantMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();

                switch (choice) {
                    case 1 -> pf.listAllTenants(); // direct call the funciton
                    case 2 -> registerTenant(sc, pf); // Register Tenant
                    case 3 -> editTenantProfile(sc, pf); // Edit Tenant Profile
                    case 4 -> deleteTenantProfile(sc, pf); // Delete Tenant Profile
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    // Register Tenant
    public static void registerTenant(Scanner sc, PropertyFunction pf) {
        System.out.println("Please follow the instruction: ");
        System.out.println("=".repeat(20));
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("IC: ");
        String ic = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        pf.registerTenant(name, email, phone, ic, username, password);
    }

    // Edit Tenant Profile
    public static void editTenantProfile(Scanner sc, PropertyFunction pf) {
        System.out.println("Tenant ID: ");
        String TenId = sc.nextLine();
        Tenant tenant = pf.findTenantById(TenId);
        int choice = 0;
        if (tenant != null) {
            do{
                System.out.println("\nWhich detail you want to update?");
                System.out.println("=".repeat(20));
                System.out.println("| 1. Name          |\n| 2. IC Number     |\n| 3. Phone         |\n| 4. Email         |");
                System.out.println("=".repeat(20));
                System.out.print("| Enter Choice: ");
                choice = sc.nextInt();
                System.out.println("=".repeat(20));
                sc.nextLine();
                
            }while(choice >= 5 || choice <= 0);

            System.out.print("Enter the value you want to update: ");
            String newValue = sc.nextLine();
            pf.updateTenantDetails(TenId, choice, newValue);
        } else {
            System.out.println("\nTenant not found. ");
        }
    }

    // Delete Tenant Profile
    public static void deleteTenantProfile(Scanner sc, PropertyFunction pf) {
        boolean back = false;
        do {
            System.out.println("Tenant ID: ");
            String TenId = sc.nextLine();
            Tenant tenant = pf.findTenantById(TenId);
            if (tenant != null) {
                pf.deleteTenant(TenId);
                back = true;
            } else {
                System.out.println("Tenant not found.");
                back = true;
            }
        } while (!back);
    }
    // ==========================================================

    // Manage Landlord Function
    // ==========================================================
    public static void manageLandlord(Scanner sc, PropertyFunction pf) {
        boolean back = false;

        do {
            try {
                Uimaterial.displayManageLandlordMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                Uimaterial.clearscreen();

                switch (choice) {
                    case 1 -> pf.listAllLandlord();
                    case 2 -> registerLandlord(sc, pf);
                    case 3 -> editLandlordProfile(sc, pf);
                    case 4 -> deleteLandlordProfile(sc, pf);
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    // Register Landlord
    public static void registerLandlord(Scanner sc, PropertyFunction pf) {
        System.out.println("Please follow the instruction: ");
        System.out.println("=".repeat(20));
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        pf.registerLandlord(name, email, phone, username, password);
    }

    // Edit Landlord Profile
    public static void editLandlordProfile(Scanner sc, PropertyFunction pf) {
        System.out.print("Enter Landlord ID: ");
        String id = sc.nextLine();
        Landlord landlord = pf.findLandlordById(id);
        if (landlord == null) {
            System.out.println("Landlord not found.");
            return;
        }
        System.out.println("Which detail you want to update?");
        System.out.println("1. Name\n2. Email\n3. Phone");
        System.out.print("Enter Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new value: ");
        String newValue = sc.nextLine();
        pf.updateLandlordDetails(id, choice, newValue);
    }

    // Delete Landlord Profile
    public static void deleteLandlordProfile(Scanner sc, PropertyFunction pf) {
        System.out.print("Enter Landlord ID: ");
        String id = sc.nextLine();
        Landlord landlord = pf.findLandlordById(id);
        if (landlord != null) {
            pf.deleteLandlord(id);
        } else {
            System.out.println("Landlord not found.");
        }
    }

    public static void editAgentProfile(Scanner sc, PropertyFunction pf,UserRole loggedUser) {
        
        String id = loggedUser.getLinkedPerson().getPersonId();
        Agent agent = pf.findAgentById(id);
        if (agent == null) {
            System.out.println("Agent not found.");
            return;
        }
        System.out.println("Which detail you want to update?");
        System.out.println("1. Name\n2. Email\n3. Phone");
        System.out.print("Enter Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new value: ");
        String newValue = sc.nextLine();
        pf.updateAgentDetails(id, choice, newValue);
    }

  

}
