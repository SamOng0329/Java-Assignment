import java.util.Scanner;   
public class Login {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        PropertyFunction pf  = PropertyFunction.getInstance();
        PropertyFunction.clearscreen();
        Uimaterial.displaybanner();
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
                    PropertyFunction.clearscreen();
                    Uimaterial.displaybanner();
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    
                    boolean Loginstatus = pf.Login(username, password);
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
                            StaffUI staffUI = new StaffUI();
                            staffUI.displayStaffMenu(loggedUser);
                        }
                        else if (loggedUser.getRole().equals(UserRole.Role.AGENT)) {
                            AgentUI agentUI = new AgentUI();
                            agentUI.displayAgentMenu(loggedUser);
                        }
                        else if (loggedUser.getRole().equals(UserRole.Role.LANDLORD)) {
                            LandLordUI landLordUI = new LandLordUI();
                            landLordUI.displayLandLordMenu(loggedUser);
                        }
                        
                        else {
                            System.out.println("Unknown role. Access denied.");}

                        
                        Running = true;
                    } else {
                        PropertyFunction.clearscreen();
                        Uimaterial.displaybanner();
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
                    PropertyFunction.clearscreen();
                    Uimaterial.displaybanner();
                    if (newTenant != null) {
                        System.out.println("Registration successful! You can now log in with your credentials.");
                    } else {
                        System.out.println("Registration failed. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                    
            }


        }
            catch (Exception e){
                PropertyFunction.clearscreen();
                Uimaterial.displaybanner();
                System.out.println("Invalid input. Please try again.");
            }
        } while (Running != true);



        
    }
}