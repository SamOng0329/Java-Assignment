import java.util.Scanner;   
public class Login {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        PropertyFunction pf  = PropertyFunction.getInstance();
        Uimaterial.clearscreen();

        boolean Running = false;
        do {
            try{
            System.out.println("Choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    Uimaterial.clearscreen();

                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    
                    boolean Loginstatus = pf.login(username, password);
                    if (Loginstatus) {
                        UserRole loggedUser = pf.isUsernameExists(username);
                        if (loggedUser.getLinkedPerson() != null) {
                            System.out.println("Login successful! Welcome, " + loggedUser.getLinkedPerson().getName() + " (" + loggedUser.getRole() + ")");
                        }
                        else {
                            System.out.println("Login successful! Welcome, " + loggedUser.getRole());
                        }

                        if (loggedUser.getRole().equals(UserRole.Role.TENANT)) {
                            TenantUI tenantUI = new TenantUI();
                            tenantUI.displayTenantMenu(loggedUser);
                        }
                        else if (loggedUser.getRole().equals(UserRole.Role.STAFF)) {
                            StaffFunction staffUI = new StaffFunction();
                            staffUI.staffMenu(loggedUser);
                        }
                        else if (loggedUser.getRole().equals(UserRole.Role.AGENT)) {
                            AgentUI agentUI = new AgentUI();
                            agentUI.agentMenu(loggedUser);
                        }
                        else if (loggedUser.getRole().equals(UserRole.Role.LANDLORD)) {
                            LandLordUI landLordUI = new LandLordUI();
                            landLordUI.displayLandLordMenu(loggedUser);
                        }
                        
                        else {
                            System.out.println("Unknown role. Access denied.");}

                        
                        
                    } else {
                        Uimaterial.clearscreen();
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter IC number: ");
                    String ic = scanner.nextLine();
                    System.out.print("Enter username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String newPassword = scanner.nextLine();
                    
                    Tenant newTenant = pf.registerTenant(name, email, phone, ic, newUsername, newPassword);
                    Uimaterial.clearscreen();

                    if (newTenant != null) {
                        System.out.println("Registration successful! You can now log in with your credentials.");
                    } else {
                        System.out.println("Registration failed. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    Running = true;
                    break;
                    
            }
        }
            catch (Exception e){
                Uimaterial.clearscreen();
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }

        } while (Running != true);
        scanner.close();
    }
}
