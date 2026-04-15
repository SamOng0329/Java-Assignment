import java.time.LocalDate;
import java.util.Scanner;

public class TenantUI {
    public void tenantMenu(UserRole loggedUser) {
        PropertyFunction pf = PropertyFunction.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean logout = false;

        do {
            try {
                UiMaterial.displayoperationmenu(loggedUser); // Calling the operation menu from UiMaterial class
                System.out.print("Enter your choice:");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> pf.listAllProperties();
                    case 2 -> manageLease(sc, pf, loggedUser);
                    case 3 -> VisitRequest(sc, pf, loggedUser);
                    case 4 -> editTenantProfile(sc, pf, loggedUser);
                    case 5 -> showProfile(loggedUser);
                    case 6 -> logout = true;
                    default -> System.out.println("Error! ");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!logout);
    }

        public static void manageLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean back = false;
        do {
            UiMaterial.displayLeaseManagementMenu(loggedUser);
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> viewAllLease(sc, pf, loggedUser);
                case 2 -> createLease(sc, pf, loggedUser);
                case 3 -> viewPayment(sc, pf, loggedUser);

                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (!back);
    }

    public static void viewAllLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        String tenantId = loggedUser.getLinkedPerson().getPersonId();
        pf.listLeaseByTenantId(tenantId);
    }

    public static void createLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        try {
            System.out.print("\n--- Create Lease ---\n");
            System.out.print("Property ID: ");
            String propId = sc.nextLine();
            String tenantId = loggedUser.getLinkedPerson().getPersonId();
            System.out.print("Agent ID : ");
            String agentId = sc.nextLine();

            if (agentId.isEmpty()|| tenantId.isEmpty() || propId.isEmpty()) {
                System.out.println("Error: Property ID, Tenant ID and Agent ID cannot be empty.");
                return;
            }

            System.out.print("Start Date (YYYY-MM-DD): ");
            String startDate = sc.nextLine();
            LocalDate start = LocalDate.parse(startDate);
            System.out.print("END Date   (YYYY-MM-DD): ");
            String endDate = sc.nextLine();
            LocalDate end = LocalDate.parse(endDate);
            double rent = pf.findPropertyById(propId).getListingPrice();
            pf.createLeaseRecord(propId, tenantId, agentId, start, end, rent,true);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine();
        }

    }

    public static void viewPayment(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        String tenantId = loggedUser.getLinkedPerson().getPersonId();
        pf.listPaymentsByTenantId(tenantId);
    }


    
    public static void VisitRequest(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean back = false;
        do {
            try {
                UiMaterial.displayVisitRequestMenu(loggedUser);
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> ListVisitRequests(sc, pf, loggedUser);
                    case 2 -> CreateVisitRequest(sc, pf, loggedUser);
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
        String tenantId = loggedUser.getLinkedPerson().getPersonId();
        pf.listVisitRequestsByTenant(tenantId);
        
    }

    public static void CreateVisitRequest(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        try {
            System.out.print("\n--- Create Visit Request ---\n");
            System.out.print("Property ID: ");
            String propId = sc.nextLine();
            String tenantId = loggedUser.getLinkedPerson().getPersonId();
            System.out.print("Agent ID: ");
            String agentId = sc.nextLine();
            System.out.print("Preferred Date (YYYY-MM-DD): ");
            String dateInput = sc.nextLine();
            LocalDate preferredDate = LocalDate.parse(dateInput);
            pf.requestVisit(tenantId, propId, agentId,  preferredDate);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine();
        }
    }

    public static void editTenantProfile(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        int choice = 0;
        String TenId = loggedUser.getLinkedPerson().getPersonId();

       
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
        
    }

    public static void showProfile(UserRole loggedUser) {
        Tenant tenant = (Tenant) loggedUser.getLinkedPerson();
        System.out.println("\n--- Tenant Profile ---");
        System.out.println("ID: " + tenant.getPersonId());
        System.out.println("Name: " + tenant.getName());
        System.out.println("Email: " + tenant.getEmail());
        System.out.println("Phone: " + tenant.getPhone());
        System.out.println("IC Number: " + tenant.getIcNumber());
    }





    


}
