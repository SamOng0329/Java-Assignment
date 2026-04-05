import java.util.Scanner;

public class LandLordUI {
    public void displayLandLordMenu(UserRole role) {
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
                    case 1 -> System.out.println("View Available Properties (Not Implemented)");
                    case 2 -> System.out.println("View Current Haved Properties (Not Implemented)");
                    case 3 -> System.out.println("Edit profile (Not Implemented)");
                    case 4 -> role.displayInfo();
                    case 5 -> logout = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                sc.nextLine();
            }
        } while (!logout);
    }
}
