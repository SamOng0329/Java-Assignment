import java.time.LocalDate;
import java.util.Scanner;

public class LandLordUI {
public void landLordMenu(UserRole loggedUser) {
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
                    case 1 -> manageProperty(sc, pf, loggedUser);
                    case 2 -> manageLease(sc, pf, loggedUser);
                    case 3 -> managePayment(sc, pf, loggedUser);
                    case 4 -> editLandlordProfile(sc, pf, loggedUser);
                    case 5 -> showProfile(loggedUser);
                    case 6 -> logout = true;
                    default -> System.out.println("Error! ");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!logout);
    }


    public static void manageProperty(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean back = false;
        do {
            try {
                UiMaterial.displayPropertyManagementMenu();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> listownedProperty(loggedUser);
                    case 2 -> addResidentialProperty(sc, pf, loggedUser);
                    case 3 -> addCommercialProperty(sc, pf, loggedUser);
                    case 4 -> updatePropertyPrice(sc, pf, loggedUser);
                    case 5 -> updatePropertyStatus(sc, pf, loggedUser);
                    case 6 -> deleteProperty(sc, pf, loggedUser);
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    public static void addResidentialProperty(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
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

            pf.addResidentialProperty(location, price, sqft, resType, bed, bath, furnished, (Landlord) loggedUser.getLinkedPerson() );
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void addCommercialProperty(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
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
            

            pf.addCommercialProperty(location, price, sqft, commType, parking, loadingDock,  (Landlord) loggedUser.getLinkedPerson());

            
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void updatePropertyPrice(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        try {
            System.out.println("\n--- Update Property Rent Price ---");
            System.out.print("Property ID: ");
            String id = sc.nextLine();
            if(!((Landlord) loggedUser.getLinkedPerson()).ownsProperty(id)){
                System.out.println("You do not have permission to edit this property!");
                return;
            }
            System.out.print("New Rent Price: ");
            double newPrice = sc.nextDouble();
            sc.nextLine();
            if (pf.updatePropertyPrice(id, newPrice)) {
                System.out.println("Rent Price updated successfully!");
            } else {
                System.out.println("Update failed. Property not found.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    public static void updatePropertyStatus(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        System.out.println("\n--- Update Property Status ---");
        System.out.print("Property ID: ");
        String id = sc.nextLine();
        if(!((Landlord) loggedUser.getLinkedPerson()).ownsProperty(id)){
            System.out.println("You do not have permission to edit this property!");
            return;
        }
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

    public static void deleteProperty(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        System.out.println("\n--- Delete Property ---");
        System.out.print("Property ID: ");
        String id = sc.nextLine();
        if(!((Landlord) loggedUser.getLinkedPerson()).ownsProperty(id)){
            System.out.println("You do not have permission to delete this property!");
            return;
        }
        pf.deleteProperty(id);

    }

    public static void listownedProperty(UserRole loggedUser) {
        Landlord landlord = (Landlord) loggedUser.getLinkedPerson();
        System.out.println("\n--- Your Owned Properties ---");
        if( landlord.getOwnedProperty().isEmpty()){
            System.out.println("You do not have any properties yet.");
            return;
        }
        for (Property p : landlord.getOwnedProperty()) {
            p.displayInfo();
        }
    }



        public static void manageLease(Scanner sc, PropertyFunction pf,UserRole loggedUser) {
        boolean back = false;
        do {
            try{
            UiMaterial.displayLeaseManagementMenu(loggedUser);
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> viewAllLease(sc, pf, loggedUser);
                case 2 -> viewOverdueLease(sc, pf, loggedUser);
                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    // View All Leases
    public static void viewAllLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean found = false;
        for (Property property : ((Landlord) loggedUser.getLinkedPerson()).getOwnedProperty()) {
            LeaseRecord lease = pf.findLeaseByPropertyId(property.getPropertyId());
            if (lease != null) {
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
        for (Property property : ((Landlord) loggedUser.getLinkedPerson()).getOwnedProperty()) {
            LeaseRecord lease = pf.findLeaseByPropertyId(property.getPropertyId());
            if (lease != null && lease.getEndDate().isBefore(today) && !lease.getLeaseStatus()) {
                lease.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No overdue leases found.");
        }
    }



    public static void managePayment(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean back = false;
        do {
            try {
                UiMaterial.displayPaymentManagementMenu(loggedUser);
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> viewAllPayments(sc, pf, loggedUser);
                    case 3 -> viewPaymentByLease(sc, pf, loggedUser);
                    case 4 -> viewPaymentByTenant(sc, pf, loggedUser);
                    case 0 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!back);
    }

    public static void viewAllPayments(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        boolean found = false;
        for (Property property : ((Landlord) loggedUser.getLinkedPerson()).getOwnedProperty()) {
            LeaseRecord lease = pf.findLeaseByPropertyId(property.getPropertyId());
            if (lease != null) {
                for (PaymentRecord payment : pf.findPaymentByLeaseArrayList(lease.getLeaseId())) {
                    payment.displayInfo();
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No payments found.");
        }
    }

    

    // View Payment Records by Lease ID
    public static void viewPaymentByLease(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        System.out.print("Enter Lease ID: ");
        boolean found = false;
        String leaseId = sc.nextLine();
        for (Property property : ((Landlord) loggedUser.getLinkedPerson()).getOwnedProperty()) {
            LeaseRecord lease = pf.findLeaseByPropertyId(property.getPropertyId());
            if (lease != null && lease.getLeaseId().equals(leaseId)) {
                pf.listPaymentsByLeaseId(leaseId);
                found = true;       
            }
        }
        if (!found) {
            System.out.println("Lease not found.");
        }
    }




    // View Payment Records by Tenant ID
    public static void viewPaymentByTenant(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        System.out.print("Enter Tenant ID: ");
        String tenantId = sc.nextLine();
        if (pf.findTenantById(tenantId) == null) {
            System.out.println("Tenant not found.");
            return;
        }
        pf.listPaymentsByTenantId(tenantId);
    }


  public static void editLandlordProfile(Scanner sc, PropertyFunction pf, UserRole loggedUser) {
        Landlord landlord = (Landlord) loggedUser.getLinkedPerson();

        if (landlord == null) {
            System.out.println("Landlord not found.");
            return;
        }
        System.out.println(landlord);
        int choice = 0;
        do {
            try {
                System.out.println("\nWhich detail you want to update?");
                System.out.println("=".repeat(40));
                System.out.printf("| 1. Name          : %s%n", landlord.getName());
                System.out.printf("| 2. Email         : %s%n", landlord.getEmail());
                System.out.printf("| 3. Phone         : %s%n", landlord.getPhone());
                System.out.println("=".repeat(40));
                System.out.print("| Enter Choice: ");
                choice = sc.nextInt();
                System.out.println("=".repeat(40));
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (choice >= 4 || choice <= 0);

        System.out.print("Enter new value: ");
        String newValue = sc.nextLine();
        pf.updateLandlordDetails(landlord.getPersonId(), choice, newValue);
    }




    public static void showProfile(UserRole loggedUser) {
        Landlord landlord = (Landlord) loggedUser.getLinkedPerson();
        System.out.println("\n--- Landlord Profile ---");
        System.out.println("ID: " + landlord.getPersonId());
        System.out.println("Name: " + landlord.getName());
        System.out.println("Email: " + landlord.getEmail());
        System.out.println("Phone: " + landlord.getPhone());
        System.out.println("Owned Properties : " + landlord.getOwnedProperty().size());
    }


}
