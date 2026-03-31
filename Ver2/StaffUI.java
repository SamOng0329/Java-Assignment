import java.util.Scanner;
public class StaffUI {
    public void displayStaffMenu(UserRole role){
        Scanner scanner = new Scanner(System.in);
        PropertyFunction.clearscreen();
        Uimaterial.displayoperationmenu(role);
        int choice = scanner.nextInt();
        scanner.nextLine();
        
    }
}
