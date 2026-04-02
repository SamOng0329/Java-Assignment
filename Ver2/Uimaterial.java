public class Uimaterial {
    public static void displaybanner(){
        System.out.println("  _  _                                               _                        _                   ");
        System.out.println(" | || |    ___    _  _     ___     ___      o O O   | |    __ _    _ _     __| |    ___      _ _  ");
        System.out.println(" | __ |   / _ \\  | +| |   (_-<    / -_)    o        | |   / _` |  | ' \\   / _` |   / -_)    | '_| ");
        System.out.println(" |_||_|   \\___/   \\_,_|   /__/_   \\___|   TS__[O]  _|_|_  \\__,_|  |_||_|  \\__,_|   \\___|   _|_|_  ");
        System.out.println("_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| ");
        System.out.println("\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'  ");
        System.out.println("\n ");
        System.out.println("Welcome to House lander v1.0");
        System.out.println("----------------------------\n"); 
    }

    public static void displayoperationmenu(UserRole role){

        System.out.println("Choose an option:");
        if (role.getRole().equals(UserRole.Role.TENANT)) {
            System.out.println("1. View Available Properties");
            System.out.println("2. View My Rental status");
            System.out.println("3. Show profile");
            System.out.println("4. Edit profile");
            System.out.println("5. Logout");
        }
        else if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("1. Manage Properties");
            System.out.println("2. Manage Users");
            System.out.println("3. View Reports");
            System.out.println("4. Show profile");
            System.out.println("5. Logout");
        }
        else if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("1. View Assigned Properties");
            System.out.println("2. Update Property Status");
            System.out.println("3. Show profile");
            System.out.println("4. Logout");
        }
        else if (role.getRole().equals(UserRole.Role.LANDLORD)) {
            System.out.println("1. View Available Properties");
            System.out.println("2. View Current Haved Properties");
            System.out.println("3. Show profile");
            System.out.println("4. Edit profile");
            System.out.println("5. Logout");
        }
   
    }

    public static void RegisterMenu(UserRole role){
  

         if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("1. Register for Agent");
            System.out.println("2. Register for Landlord");
            System.out.println("3. Register for Tenant");
        }
        else if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("1. Register for Landlord");
            System.out.println("2. Register for Tenant");
        }
    }

  

    

    public static void editprofileFirstMenu(UserRole role){
       
    
        
        System.out.println("Choose an option:");
        if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("1. Edit Tenant Profile");
            System.out.println("2. Edit Landlord Profile");
            System.out.println("3. Edit Agent Profile");
        }
        else if (role.getRole().equals(UserRole.Role.AGENT)) {
            System.out.println("1. Edit Tenant Profile");
            System.out.println("2. Edit Landlord Profile");
            System.out.println("3. Edit Own Profile");
            

        }
    }

    public static void editprofileSecondMenu(UserRole.Role role){
     
        System.out.println("Choose an option:");
        
        if (role.equals(UserRole.Role.AGENT)) {
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Email");
            System.out.println("3. Edit Phone Number");
            System.out.print("Choice(4 to go back): ");

        }
        else if (role.equals(UserRole.Role.TENANT)) {
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
    public static void clearscreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        displaybanner();
    }
    
}
