package Assignment;

import java.util.ArrayList;
import java.util.Scanner;


public class Uni_method_list {

    private ArrayList<Property> properties;
    private File_Modifier fm;

    public Uni_method_list(String filename) {
        fm = new File_Modifier();
        boolean hasData = fm.initialize(filename);
        if (hasData) {
            properties = fm.loadPropertiesData();
        } else {
            properties = new ArrayList<>();
        }
    }

    // ── Add ───────────────────────────────────────────────────────────────────
    public void addProperties(Scanner scanner) {
        boolean addMore = true;

        while (addMore) {
            System.out.println("\n[ ADD PROPERTY ]");
            System.out.println("1. Residential");
            System.out.println("2. Commercial");
            System.out.print("Choose type: ");
            String typeChoice = scanner.nextLine().trim();
            
            try{
                if (typeChoice.equals("1")){
                    String id = "R" + String.format("%04d", properties.size() + 1); 
                    ResidentialProperty.ResidentialType resType;
                    System.out.print("Enter location: ");
                    String location = scanner.nextLine().trim();
                    System.out.print("Enter listing price: ");
                    double price = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter square footage: ");
                    double sqft = Double.parseDouble(scanner.nextLine().trim());
                    System.out.println("Enter residential type (APARTMENT, HOUSE, CONDO): ");
                    System.out.println("1. APARTMENT");
                    System.out.println("2. HOUSE");
                    System.out.println("3. CONDO");
                    System.out.print("Choose: ");
                    String resTypeChoice = scanner.nextLine().trim();
                    if (resTypeChoice.equals("1")) {
                        resType= ResidentialProperty.ResidentialType.APARTMENT;
                    } else if (resTypeChoice.equals("2")) {
                        resType= ResidentialProperty.ResidentialType.HOUSE;
                    } else if (resTypeChoice.equals("3")) {
                        resType= ResidentialProperty.ResidentialType.CONDO;
                    } else {
                        System.out.println("Invalid residential type. Defaulting to APARTMENT.");
                        resType= null;
                    }

                    System.out.print("Enter number of bedrooms: ");
                    int bedrooms = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter number of bathrooms: ");
                    int bathrooms = Integer.parseInt(scanner.nextLine().trim());
                    
                    boolean hasGarden = false;
                    if (resType == ResidentialProperty.ResidentialType.APARTMENT || resType == ResidentialProperty.ResidentialType.CONDO) {
                        System.out.println("Apartments typically do not have gardens. Defaulting to no garden.");
                    }
                    else{
                        System.out.print("Does it have a garden? (yes/no): ");
                        hasGarden = scanner.nextLine().trim().equalsIgnoreCase("yes");
                    }
                    ResidentialProperty newResProp = new ResidentialProperty(id, location, price, resType, sqft, bedrooms, bathrooms, hasGarden);
                    properties.add(newResProp);
                    System.out.println("Residential property added successfully.");
                    fm.writePropertiesData(properties);

                    }

                    else if (typeChoice.equals("2")){
                        String id = "C" + String.format("%04d", properties.size() + 1);
                        CommercialProperty.CommercialType comType;
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine().trim();
                        System.out.print("Enter listing price: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Enter square footage: ");
                        double sqft = Double.parseDouble(scanner.nextLine().trim());
                        System.out.println("Enter commercial type (OFFICE, RETAIL, INDUSTRIAL): ");
                        System.out.println("1. OFFICE");
                        System.out.println("2. RETAIL");
                        System.out.println("3. INDUSTRIAL");
                        System.out.print("Choose: ");
                        String comTypeChoice = scanner.nextLine().trim();
                        if (comTypeChoice.equals("1")) {
                            comType= CommercialProperty.CommercialType.OFFICE;
                        } else if (comTypeChoice.equals("2")) {
                            comType= CommercialProperty.CommercialType.RETAIL;
                        } else if (comTypeChoice.equals("3")) {
                            comType= CommercialProperty.CommercialType.INDUSTRIAL;
                        } else {
                            System.out.println("Invalid commercial type. Defaulting to OFFICE.");
                            comType= null;
                        }
                        System.out.print("Enter number of parking spots: ");
                        int parkingSpots = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Does it have a loading dock? (yes/no): ");
                        boolean hasLoadingDock = scanner.nextLine().trim().equalsIgnoreCase("yes");
                        CommercialProperty newComProp = new CommercialProperty(id, location, price, sqft, comType, parkingSpots, hasLoadingDock);
                        properties.add(newComProp);
                        System.out.println("Commercial property added successfully.");
                        fm.writePropertiesData(properties);

                        } else {
                            System.out.println("Invalid property type selected.");
                        }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for price and square footage.");

                }
    
                System.out.print("Add another property? (yes/no): ");
                String choice = scanner.nextLine().trim();
                if  (choice.equalsIgnoreCase("yes"))
                    addMore = true;
                else
                    addMore = false;
            }
        }
        public void editProperties(Scanner scanner) {
        boolean found = false;
        Property target = null;
        int targetIndex = -1;

        while (!found) {
            System.out.print("\nEnter Property ID to edit (or 0 to cancel): ");
            String editId = scanner.nextLine().trim();

            if (editId.equals("0")) return;

            for (int i = 0; i < properties.size(); i++) {
                if (properties.get(i).getPropertyId().equals(editId)) {
                    target = properties.get(i);
                    targetIndex = i;
                    found = true;
                    break;
                }
            }
            if (!found) System.out.println("Property ID not found. Try again.");
        }
            if (target.getType() == Property.PropertyType.RESIDENTIAL){
                ResidentialProperty rp = (ResidentialProperty) target;
                System.out.println("1. Location");
                System.out.println("2. Residential Type");
                System.out.println("3. Price");
                System.out.println("4. Square Footage");
                System.out.println("5. Bedrooms");
                System.out.println("6. Bathrooms");
                System.out.println("7. Has Garden");
                System.out.println("8. Status");
                System.out.print("Choose field to edit: ");
                String choice = scanner.nextLine().trim();
                if (choice.equals("1")){
                    System.out.print("Enter new location: ");
                    String newLocation = scanner.nextLine().trim();
                    rp.setLocation(newLocation);
                }
                else if (choice.equals("2")){
                    System.out.println("Enter new residential type (APARTMENT, HOUSE, CONDO): ");
                    System.out.println("1. APARTMENT");
                    System.out.println("2. HOUSE");
                    System.out.println("3. CONDO");
                    System.out.print("Choose: ");
                    String resTypeChoice = scanner.nextLine().trim();
                   
                    if (resTypeChoice.equals("1")) {
                        rp.setResidentialType(ResidentialProperty.ResidentialType.APARTMENT);
                    } else if (resTypeChoice.equals("2")) {
                        
                        rp.setResidentialType(ResidentialProperty.ResidentialType.HOUSE);
                    
                    } else if (resTypeChoice.equals("3")) {
                        rp.setResidentialType(ResidentialProperty.ResidentialType.CONDO);
                    } else {
                        System.out.println("Invalid residential type, remain unchanged");
                    }
                    
                }
                else if (choice.equals("3")){
                    System.out.print("Enter new price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine().trim());
                    rp.setListingPrice(newPrice);
                }
                else if (choice.equals("4")){
                    System.out.print("Enter new square footage: ");
                    double newSqft = Double.parseDouble(scanner.nextLine().trim());
                    rp.setSquareFootage(newSqft);
                }
                else if (choice.equals("5")){
                    System.out.print("Enter new number of bedrooms: ");
                    int newBedrooms = Integer.parseInt(scanner.nextLine().trim());
                    rp.setNumBedrooms(newBedrooms);
                }
                else if (choice.equals("6")){
                    System.out.print("Enter new number of bathrooms: ");
                    int newBathrooms = Integer.parseInt(scanner.nextLine().trim());
                    rp.setNumBathrooms(newBathrooms);
                }
                else if (choice.equals("7")){
                    if (rp.getResidentialType().equals("APARTMENT") || rp.getResidentialType().equals("CONDO")) {
                        System.out.println("Apartments and condos typically do not have gardens. Cannot set garden status.");
                    }
                    else{
                    System.out.print("Does it have a garden? (yes/no): ");
                    boolean hasGarden = scanner.nextLine().trim().equalsIgnoreCase("yes");
                    rp.setHasGarden(hasGarden);
                    }
                }
                else if (choice.equals("8")){
                    System.out.println("Enter new status (AVAILABLE, PENDING, SOLD): ");
                    System.out.println("1. AVAILABLE");
                    System.out.println("2. PENDING");
                    System.out.println("3. SOLD");
                    System.out.print("Choose: ");
                    String statusChoice = scanner.nextLine().trim();
                    
                    if (statusChoice.equals("1")) {
                        rp.setStatus(Property.PropertyStatus.AVAILABLE);
                    } else if (statusChoice.equals("2")) {
                        rp.setStatus(Property.PropertyStatus.RENTED);
                    
                    } else if (statusChoice.equals("3")) {
                        rp.setStatus(Property.PropertyStatus.SOLD);
                    } else {
                        System.out.println("Invalid status, remain unchanged");
                    }
                    
                }
                else {
                    System.out.println("Invalid choice.");
                }
                properties.set(targetIndex, target);
                fm.writePropertiesData(properties);
            }
            
        }
     public void searchProperty(Scanner scanner) {
        boolean searchMore = true;

        while (searchMore) {
            System.out.print("\nEnter Property ID to search (or 0 to cancel): ");
            String searchId = scanner.nextLine().trim();

            if (searchId.equals("0")) return;

            boolean found = false;
            for (Property prop : properties) {
                if (prop.getPropertyId().equals(searchId)) {
                    found = true;

                    if (prop.getType() == Property.PropertyType.RESIDENTIAL) {
                        ResidentialProperty rp = (ResidentialProperty) prop;
                        System.out.println("┌───────────────────────────────────────────────────┐");
                        System.out.printf( "│ %-18s : %-28s │%n", "Property ID",    rp.getPropertyId());
                        System.out.printf( "│ %-18s : %-28s │%n", "Type",           "Residential - " + rp.getResidentialType());
                        System.out.printf( "│ %-18s : %-28s │%n", "Location",       rp.getLocation());
                        System.out.printf( "│ %-18s : %-28s │%n", "Price (RM)",     String.format("%.2f", rp.getListingPrice()));
                        System.out.printf( "│ %-18s : %-28s │%n", "Square Footage", rp.getSquareFootage() + " sqft");
                        System.out.printf( "│ %-18s : %-28s │%n", "Bedrooms",       rp.getNumBedrooms());
                        System.out.printf( "│ %-18s : %-28s │%n", "Bathrooms",      rp.getNumBathrooms());
                        System.out.printf( "│ %-18s : %-28s │%n", "Garden",         rp.hasGarden() ? "Yes" : "No");
                        System.out.printf( "│ %-18s : %-28s │%n", "Status",         rp.getStatus());
                        System.out.println("└───────────────────────────────────────────────────┘");

                    } else if (prop.getType() == Property.PropertyType.COMMERCIAL) {
                        CommercialProperty cp = (CommercialProperty) prop;
                        System.out.println("┌───────────────────────────────────────────────────┐");
                        System.out.printf( "│ %-18s : %-28s │%n", "Property ID",    cp.getPropertyId());
                        System.out.printf( "│ %-18s : %-28s │%n", "Type",           "Commercial - " + cp.getCommercialType());
                        System.out.printf( "│ %-18s : %-28s │%n", "Location",       cp.getLocation());
                        System.out.printf( "│ %-18s : %-28s │%n", "Price (RM)",     String.format("%.2f", cp.getListingPrice()));
                        System.out.printf( "│ %-18s : %-28s │%n", "Square Footage", cp.getSquareFootage() + " sqft");
                        System.out.printf( "│ %-18s : %-28s │%n", "Parking Spots",  cp.getNumParkingSpots());
                        System.out.printf( "│ %-18s : %-28s │%n", "Loading Dock",   cp.getHasLoadingDock() ? "Yes" : "No");
                        System.out.printf( "│ %-18s : %-28s │%n", "Status",         cp.getStatus());
                        System.out.println("└───────────────────────────────────────────────────┘");
                    }
                    break;
                }
            }
            if (!found) System.out.println("Property ID " + searchId + " not found.");

            System.out.print("Search another? (yes/no): ");
            searchMore = scanner.nextLine().trim().equalsIgnoreCase("yes");
        }
    
}
}

    

          
        
            