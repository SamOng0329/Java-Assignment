import java.util.Scanner;

public class AgentUI {
    public void displayAgentMenu(UserRole role) {
        PropertyFunction pf = PropertyFunction.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean logout = false;
        do {
            try {
                UiMaterial.displayoperationmenu(role);
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                UiMaterial.clearscreen();
                switch (choice) {
                    case 1 -> System.out.println("View Assigned Properties (Not Implemented)");
                    case 2 -> updatePropertyStatus(sc, pf);
                    case 3 -> role.displayInfo();
                    case 4 -> logout = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                sc.nextLine();
            }
        } while (!logout);
    }

    public static void updatePropertyStatus(Scanner sc, PropertyFunction pf) {
        System.out.println("\n--- Update Property Status ---");
        System.out.print("Property ID: ");
        String id = sc.nextLine();
        System.out.print("Status (1. RENTED, 2. SOLD): ");
        try {
            int statChoice = sc.nextInt();
            sc.nextLine();
            Property.PropertyStatus status = switch (statChoice) {
                case 1 -> Property.PropertyStatus.RENTED;
                case 2 -> Property.PropertyStatus.SOLD;
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
}
