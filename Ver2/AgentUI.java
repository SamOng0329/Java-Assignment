import java.util.Scanner;

public class AgentUI {
    public void displayAgentMenu(UserRole role) {
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
                    case 2 -> System.out.println("Update Property Status (Not Implemented)");
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
}
