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
        displaybanner();
        System.out.println("Choose an option:");
        if (role.getRole().equals(UserRole.Role.TENANT)) {
            System.out.println("1. View Available Properties");
            System.out.println("2. View My Bookings");
            System.out.println("3. Show profile");
            System.out.println("4. Logout");
        }
        else if (role.getRole().equals(UserRole.Role.STAFF)) {
            System.out.println("1. Manage Properties");
            System.out.println("2. Manage Tenants");
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
            System.out.println("4. Login");
        }
   
    }
    
}
