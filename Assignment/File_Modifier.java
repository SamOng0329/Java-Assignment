package Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class File_Modifier {
    
    private File file;
    public boolean initialize(String filename) {
        file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing file: " + e.getMessage());
            return false;
        }
        return file.length() > 0;
    }



 public ArrayList<Property> loadPropertiesData() {
        ArrayList<Property> properties = new ArrayList<>();

        if (file == null || !file.exists()) {
            System.out.println("File not found. Please initialize first.");
            return properties;
        }

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");

                if (parts.length == 8) {
                    CommercialProperty cp = new CommercialProperty(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]),
                        CommercialProperty.CommercialType.valueOf(parts[5]),
                        Integer.parseInt(parts[6]),
                        Boolean.parseBoolean(parts[7])
                    );
                    cp.setStatus(Property.PropertyStatus.valueOf(parts[4]));
                    properties.add(cp);

                } else if (parts.length == 9) {
   
                    ResidentialProperty rp = new ResidentialProperty(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2]),
                        ResidentialProperty.ResidentialType.valueOf(parts[5]),
                        Double.parseDouble(parts[3]),
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7]),
                        Boolean.parseBoolean(parts[8])
                    );
                    rp.setStatus(Property.PropertyStatus.valueOf(parts[4]));
                    properties.add(rp);

                } else {
                    System.out.println("Skipping malformed line: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading properties: " + e.getMessage());
        }

        return properties;
    }


public void writePropertiesData(ArrayList<Property> properties) {
        if (file == null) {
            System.out.println("File not initialized.");
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Property prop : properties) {
                if (prop == null) continue;

                if (prop.getType() == Property.PropertyType.COMMERCIAL) {
                    CommercialProperty cp = (CommercialProperty) prop;
                    writer.write(String.format("%s|%s|%.2f|%.2f|%s|%s|%d|%b%n",
                        cp.getPropertyId(),
                        cp.getLocation(),
                        cp.getListingPrice(),
                        cp.getSquareFootage(),
                        cp.getStatus(),
                        cp.getCommercialType(),
                        cp.getNumParkingSpots(),
                        cp.getHasLoadingDock()
                    ));

                } else if (prop.getType() == Property.PropertyType.RESIDENTIAL) {
                    ResidentialProperty rp = (ResidentialProperty) prop;
                    writer.write(String.format("%s|%s|%.2f|%.2f|%s|%s|%d|%d|%b%n",
                        rp.getPropertyId(),
                        rp.getLocation(),
                        rp.getListingPrice(),
                        rp.getSquareFootage(),
                        rp.getStatus(),
                        rp.getResidentialType(),
                        rp.getNumBedrooms(),
                        rp.getNumBathrooms(),
                        rp.hasGarden()
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing properties: " + e.getMessage());
        }
    }
}