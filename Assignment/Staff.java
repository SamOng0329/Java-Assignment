package Assignment;
import java.util.Scanner;

import Example.User;

import java.util.InputMismatchException;
import java.io.IOException;
import java.io.File; 
import java.io.FileWriter;


public class Staff {
    private String username;
    private String phone;
    private String staffID;
    private String email;
    

    public Staff(String username, String phone, String staffID, String email) {
            this.username = username;
            this.phone = phone;
            this.staffID = staffID;
            this.email = email;
    }
   
    
    public void showMenu() {
        String[] propertiesname = new String[1000];
        String[] propertylocation = new String[1000];
        String[] propertytype = new String[1000];
        String[] propertycategory = new String[1000];
        Double[] sqrft= new Double[1000];
        String[] bedrooms = new String[1000];
        String[] bathrooms = new String[1000];
        String[] description = new String[1000];
        String[] Furnishing = new String[1000];
        Double[] propertyprice = new Double[1000];
        String[] propertyid = new String[1000];
        String[] status = new String[1000];
        
        File propertiesList = new File("PropertiesList.txt");
        initializePropertyFile(propertiesList);
        loadPropertiesFromFile(propertiesList, propertiesname, propertylocation, propertytype, propertycategory, sqrft, bedrooms, bathrooms, description, Furnishing, propertyprice, propertyid, status);
        

      
            boolean logout = false;
            Scanner scanner = new Scanner(System.in);
            while (!logout) {
                
                System.out.println("Properties List");
                System.out.println("---------------");
                if(propertiesname[0]== null){
                    System.out.println("No properties available.");
                }else{
                String header = String.format("%-5s %-20s %-20s %-15s %-10s %-10s", "ID", "Name", "Location", "Type", "Category", "Price", "Status");
                System.out.println(header);
                for (int i = 0 ; i < propertiesname.length; i++) {
                    if (propertiesname[i] != null && propertylocation[i] != null && propertytype[i] != null && propertycategory[i] != null && sqrft[i] != null && bedrooms[i] != null && bathrooms[i] != null && description[i] != null && Furnishing[i] != null && propertyprice[i] != null && propertyid[i] != null && status[i] != null) {
                        String priceStr = String.format("%.2f", propertyprice[i]);
                        String row = String.format("%-5s %-20s %-20s %-15s %-10s $%-10s %-10s", propertyid[i], propertiesname[i], propertylocation[i], propertytype[i], propertycategory[i], priceStr, status[i]);
                        System.out.println(row);
                        
                    }
                }
            
            }
                
                System.out.println("\nStaff Menu:");
                System.out.println("1. View profile");
                System.out.println("2. Add properties");
                System.out.println("3. Edit properties");
                System.out.println("4. Search properties");
                System.out.println("5. Logout");
                System.out.print("Choose: ");
                int choice = -1;
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice == 1) {
                    show_profile();
                } 
                else if (choice == 2) {
                    addProperties(scanner, propertiesname, propertylocation, propertytype, propertycategory, sqrft, bedrooms, bathrooms, description, Furnishing, propertyprice, propertiesList, propertyid, status);
                } 
                else if (choice == 3) {
                    System.out.println("\n");
                    editProperties(scanner,propertiesList, propertiesname, propertylocation, propertytype, propertycategory, sqrft, bedrooms, bathrooms, description, Furnishing, propertyprice, propertyid, status);
                }
                else if(choice == 4){
                    searcher(scanner, propertiesname, propertylocation, propertytype, propertycategory, sqrft, bedrooms, bathrooms, description, Furnishing, propertyprice, propertyid, status);
                }
                else if(choice == 5){
                    System.out.println("Logging out...");
                    logout = true;
                }
                else {
                    System.out.println("Please select a valid option.");
                }

                
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // clear invalid token
               
                    
                }
            
            
            }
            Main main = new Main();
            main.main(null);

     
    }

    public void show_profile() {
        
        if (username != null) {
            System.out.println("\nStaff Profile:");
            System.out.println("Username: " + username);
            System.out.println("Staff ID: " + staffID);
            System.out.println("Email:" + email);
            System.out.println("Phone: " + phone);
        } else {
            System.out.println("Error retrieving profile information.");
        }
    }
    public void editProperties(Scanner scanner,File propertiesList, String name[], String location[],String type[], String category[], Double sqrft[], String bedrooms[], String bathrooms[], String description[], String furnishing[], Double price[], String id[], String status[]) {
        try{
        boolean found = false;
        String editid = "";
        while(!found){
        System.out.print("Enter the Property ID to edit(type 1 to exit): ");
        editid = scanner.nextLine();
        if (editid.equals("1")){
            return;
        }
          
        for (int i = 0; i < id.length; i++) {
            if (id[i] != null && id[i].equals(editid)) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Property ID not found. Please try again.");
        }
    }
        System.out.println("\n");
        System.out.println("Enter category to edit ");
        System.out.println("1. Name");
        System.out.println("2. Location");
        System.out.println("3. Type");
        System.out.println("4. Category");
        System.out.println("5. Square Footage");
        System.out.println("6. Bedrooms");
        System.out.println("7. Bathrooms");
        System.out.println("8. Description");
        System.out.println("9. Furnishing");
        System.out.println("10. Price");
        System.out.print("Choose: ");
        int editchoice = scanner.nextInt();
        scanner.nextLine();
        String StrOfId = editid.substring(1);
        int indexOfId = Integer.parseInt(StrOfId) - 1;

        if (editchoice == 1) {
            System.out.print("Enter new Name: ");
            name[indexOfId] = scanner.nextLine();
        } else if (editchoice == 2) {
            System.out.print("Enter new Location: ");
            location[indexOfId]  = scanner.nextLine();
        } else if (editchoice == 3) {
            System.out.print("Enter new Type: ");
            type[indexOfId] = scanner.nextLine();
        } else if (editchoice == 4) {
            System.out.print("Enter new Category: ");
            category[indexOfId] = scanner.nextLine();
        } else if (editchoice == 5) {
            System.out.print("Enter new Square Footage: ");
            sqrft[indexOfId] = scanner.nextDouble();
            scanner.nextLine();
        } else if (editchoice == 6) {
            System.out.print("Enter new Bedrooms: ");
            bedrooms[indexOfId] = scanner.nextLine();
        } else if (editchoice == 7) {
            System.out.print("Enter new Bathrooms: ");
            bathrooms[indexOfId] = scanner.nextLine();
        } else if (editchoice == 8) {
            System.out.print("Enter new Description: ");
            description[indexOfId] = scanner.nextLine();
        } else if (editchoice == 9) {
            System.out.print("Enter new Furnishing: ");
            furnishing[indexOfId] = scanner.nextLine();
        } else if (editchoice == 10) {
            System.out.print("Enter new Price: ");
            price[indexOfId] = scanner.nextDouble();
            scanner.nextLine();
        } else {
            System.out.println("Invalid choice.");
            return;
        }
        
        
        } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter the correct data type.");
        return;
    }

        // Rewrite the entire file with updated data
        try (FileWriter writer = new FileWriter(propertiesList, false)) {
            for (int i = 0; i < name.length; i++) {
                if (name[i] != null) {
                    String line = String.format("%s|%s|%s|%s|%s|%.2f|%s|%s|%s|%s|%.2f|%s", id[i], name[i], location[i], type[i], category[i], sqrft[i] == null ? 0.0 : sqrft[i], bedrooms[i], bathrooms[i], description[i], furnishing[i], price[i] == null ? 0.0 : price[i], status[i]);
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating properties file: " + e.getMessage());
        }
    }
    
    public void writePropertiesToFile(File propertiesList, String name, String location, String type, String category,Double sqrft, String bedrooms, String bathrooms, String description, String furnishing, Double price, String id, String status) {
        if (name == null) return;
        try (FileWriter writer = new FileWriter(propertiesList, true)) {
            String line = String.format("%s|%s|%s|%s|%s|%.2f|%s|%s|%s|%s|%.2f|%s", id, name, location, type, category,sqrft == null ? 0.0 : sqrft, bedrooms,bathrooms,description,furnishing,price == null ? 0.0 : price,status);
            writer.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error writing to properties file: " + e.getMessage());
        }
    }

    public void searcher(Scanner scanner, String name[], String location[],String type[], String category[], Double sqrft[], String bedrooms[], String bathrooms[], String description[], String furnishing[], Double price[], String id[], String status[]){
       boolean leave = false;
       while (!leave) {
           System.out.print("Enter the id of property: ");
           String searchId = scanner.nextLine();
           boolean found = false;
           for (int i = 0; i < id.length; i++) {
               if (id[i] != null && id[i].equals(searchId)) {
                   found = true;
                   String typeCategory = type[i] + " - " + category[i];
                   String priceStr = String.format("RM %.2f/month", price[i]);

                   // Draw the Box
                   System.out.println("┌───────────────────────────────────────────────────┐");
                   System.out.printf("│ %-15s : %-31s │%n", "Property ID", id[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Name", name[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Type", typeCategory);
                   System.out.printf("│ %-15s : %-31s │%n", "Location", location[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Square Footage", sqrft[i] + " sqft");
                   System.out.printf("│ %-15s : %-31s │%n", "Bedrooms", bedrooms[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Bathrooms", bathrooms[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Description", description[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Furnishing", furnishing[i]);
                   System.out.printf("│ %-15s : %-31s │%n", "Price", priceStr);
                   System.out.printf("│ %-15s : %-31s │%n", "Status", status[i]);
                   System.out.println("└───────────────────────────────────────────────────┘");
                   break;
               }
           }
           if (!found) {
               System.out.println("Property with ID " + searchId + " not found.");
           }
           System.out.print("Do you want to search for another property? (yes/no): ");
           String response = scanner.nextLine();
           if (response.equalsIgnoreCase("no")) {
               leave = true;
           }
       }
    }
    

    public boolean initializePropertyFile(File propertiesList) {
        
        boolean filedatas = false;
        try {
            if (!propertiesList.exists()) {
                propertiesList.createNewFile();
            }

            if (propertiesList.length() == 0) {
                filedatas= false;
            }
            else{
                filedatas= true;
            }
        } catch (IOException e) {
            // fail silently — do not print or call other methods
            return false;
        }
          try (Scanner s = new Scanner(propertiesList)) {
            while (s.hasNextLine()) {
                s.nextLine();
            }
        } catch (Exception e) {
            
        }
        return filedatas;

    }
    public void loadPropertiesFromFile(File propertiesList, String[] names, String[] locations, String[] types, String[]category,Double[] sqrft, String[] bedrooms, String[] bathrooms, String[] description, String[] furnishing, Double[] prices,  String[] ids, String[] statuses) {
    if (!propertiesList.exists()) return;

    try (Scanner fileScanner = new Scanner(propertiesList)) {
        int i = 0;
        while (fileScanner.hasNextLine() && i < names.length) {
            String line = fileScanner.nextLine();
            
            // split by "|" - we use \\| because | is a special regex character
            String[] parts = line.split("\\|");

            if (parts.length >= 7) {
                ids[i] = parts[0];
                names[i] = parts[1];
                locations[i] = parts[2];
                types[i] = parts[3];
                category[i] = parts[4];
                sqrft[i] = Double.parseDouble(parts[5]);
                bedrooms[i] = parts[6];
                bathrooms[i] = parts[7];
                description[i] = parts[8];
                furnishing[i] = parts[9];
                prices[i] = Double.parseDouble(parts[10]);
                statuses[i] = parts[11];
                i++;
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Error loading data: " + e.getMessage());
    }
}

    public void addProperties(Scanner scanner,String[] propertiesname, String[] propertylocation, String[] propertytype,String[] propertycategory, Double[] sqrft, String[] bedrooms, String[] bathrooms, String[] description, String[] furnishing, Double[] propertyprice, File propertiesList, String[] propertyid, String[] status) {
        boolean escape = false;
        int index = 0;
        while (index < propertiesname.length && propertiesname[index] != null) {
            index++;
        }
        try {
            while (!escape && index < propertiesname.length) {
                System.out.print("Enter Property Name: ");
                propertiesname[index] = scanner.nextLine();
                System.out.print("Enter Property Location: ");
                propertylocation[index] = scanner.nextLine();
                System.out.print("Enter Property Type: ");
                propertytype[index] = scanner.nextLine();
                System.out.print("Enter Property Category: ");
                propertycategory[index] = scanner.nextLine();
                System.out.print("Enter Property Square Footage: ");
                sqrft[index] = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter Property Bedrooms: ");
                bedrooms[index] = scanner.nextLine();
                System.out.print("Enter Property Bathrooms: ");
                bathrooms[index] = scanner.nextLine();
                System.out.print("Enter Property Description: ");
                description[index] = scanner.nextLine();
                System.out.print("Enter Property Furnishing: ");
                furnishing[index] = scanner.nextLine();
                System.out.print("Enter Property Price: ");
                propertyprice[index] = scanner.nextDouble();
                scanner.nextLine(); 
                propertyid[index] = "P" + String.format("%04d", index + 1);
                status[index] = "Available";
                
                // append the newly added property to the file
                writePropertiesToFile(propertiesList, propertiesname[index], propertylocation[index], propertytype[index], propertycategory[index], sqrft[index], bedrooms[index], bathrooms[index], description[index], furnishing[index], propertyprice[index], propertyid[index], status[index]);
                System.out.print("Do you want to add another property? (yes/no): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("no")) {
                    escape = true;
                } else {
                    index++;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct data type.");
            scanner.nextLine(); // clear invalid token
            if (propertiesname[index] == null || propertylocation[index] == null || propertytype[index] == null || propertycategory[index] == null || sqrft[index] == null || bedrooms[index] == null || bathrooms[index] == null || description[index] == null || furnishing[index] == null || propertyprice[index] == null || propertyid[index] == null || status[index] == null) {
                propertiesname[index] = null;
                propertylocation[index] = null;
                propertytype[index] = null;
                propertycategory[index] = null;
                sqrft[index] = null;
                bedrooms[index] = null;
                bathrooms[index] = null;
                description[index] = null;
                furnishing[index] = null;
                propertyprice[index] = null;
                propertyid[index] = null;
                status[index] = null;
                
            }
            
            return;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
}


