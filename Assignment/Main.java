package Assignment;

import java.util.Scanner;
import java.util.InputMismatchException;


public class Main{
    private static final String[] CUSTOMER_USERNAME = {"customer1", "customer2", "customer3"};
    private static final String[] CUSTOMER_PASSWORD = {"pass1", "pass2", "pass3"};



    public static void main(String[] args) {
        UserInfo[] adminList = new UserInfo[4];
        adminList[0] = new UserInfo("PS0001", "admin1pass", "AdminOne", "admin1@gmail.com", "60111111111");
        adminList[1] = new UserInfo("PS0002", "admin2pass", "AdminTwo", "admin2@gmail.com", "60122222222");
        adminList[2] = new UserInfo("PS0003", "admin3pass", "AdminThree", "admin3@gmail.com", "60133333333");
        adminList[3] = new UserInfo("PS0004", "admin4pass", "AdminFour", "admin4@gmail.com", "60144444444");

        boolean loginresult = false;
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (!loginresult) {
        

        System.out.println("  _  _                                               _                        _                   ");
        System.out.println(" | || |    ___    _  _     ___     ___      o O O   | |    __ _    _ _     __| |    ___      _ _  ");
        System.out.println(" | __ |   / _ \\  | +| |   (_-<    / -_)    o        | |   / _` |  | ' \\   / _` |   / -_)    | '_| ");
        System.out.println(" |_||_|   \\___/   \\_,_|   /__/_   \\___|   TS__[O]  _|_|_  \\__,_|  |_||_|  \\__,_|   \\___|   _|_|_  ");
        System.out.println("_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| ");
        System.out.println("\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'  ");
        System.out.println("\n ");
        System.out.println("Welcome to House lander v1.0");
        System.out.println("----------------------------\n");
        System.out.println("Login with :");
        System.out.println("1.Customer");
        System.out.println("2.Admin");
        System.out.println("3.Exit\n");
       
        System.out.print("Select your option: ");        
        try {
            option = scanner.nextInt();
            scanner.nextLine();
            if (option < 1 || option > 3) {
                System.out.println("Please select a valid option (1-3).\n");
                option = 0;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number (1, 2, or 3).\n");
            scanner.nextLine();
            option = 0;
        }
        if (option == 1) {
            System.out.println("Customer login selected.\n\n");
            System.out.print("Please enter your username: ");
            String custUsername = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String custPassword = scanner.nextLine();
            boolean isValidLogin = loginChecker(custUsername, custPassword, option, adminList);
            if (isValidLogin) {
                System.out.println("Customer login successful.");
                Customer customer = new Customer(custUsername);
                customer.showMenu(scanner);
                loginresult = true;
            } else {
                System.out.println("Customer login failed. Invalid username or password.");
            }



        } else if (option == 2) {
            System.out.println("Admin login selected.\n\n");
            System.out.print("Please enter your ID: ");
            String adminID = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String adminPassword = scanner.nextLine();
            boolean isValidLogin = loginChecker(adminID, adminPassword, option, adminList);
            if (isValidLogin) {
                System.out.println("Admin login successful.");
                UserInfo matchedAdmin = getAdminByUsername(adminID, adminList);
                Staff staff = new Staff(adminID, matchedAdmin.getPhone(), matchedAdmin.getPassword(), matchedAdmin.getEmail());
                staff.showMenu();
                loginresult = true;
            } else {
                System.out.println("Admin login failed. Invalid username or password.");
            }
            
        } else if (option == 3) {
            System.out.println("Exiting the application.");
            loginresult = true;
        } else {
            System.out.println("Invalid option selected.");
            
        }
        }
       
    }

  
    private static UserInfo getAdminByUsername(String username, UserInfo[] adminList) {
        for (UserInfo admin : adminList) {
            if (admin.getPersonId().equals(username) || admin.getName().equals(username)) {
                return admin;
            }
        }
        return null;
    }
    
 
    private static boolean loginChecker(String username, String password, int option, UserInfo[] adminList) {
        boolean isValid = false;
        if (option == 2) {
            for (int i = 0; i < adminList.length; i++) {
                if (adminList[i].getPersonId().equals(username) && adminList[i].getPassword().equals(password)) {
                    isValid = true;
                }
            }   
        }
        else if (option == 1) {
            for (int i = 0; i < CUSTOMER_USERNAME.length; i++) {
                if (CUSTOMER_USERNAME[i].equals(username) && CUSTOMER_PASSWORD[i].equals(password)) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
}

