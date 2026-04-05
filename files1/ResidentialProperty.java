package files1;

/**
 * Represents a Residential property (Apartment or Villa).
 *
 * OOP Concepts:
 * - Inheritance: Extends Property
 * - Polymorphism: Overrides getPropertyType() and displayInfo()
 */
public class ResidentialProperty extends Property {

    // Inner enum for residential subtypes
    public enum ResidentialType {
        APARTMENT, VILLA
    }

    private ResidentialType residentialType;
    private int numBedrooms;
    private int numBathrooms;
    private boolean isFurnished;

    // Constructor
    public ResidentialProperty(String propertyId, String location, double listingPrice,
            double squareFootage, ResidentialType residentialType,
            int numBedrooms, int numBathrooms, boolean isFurnished) {
        super(propertyId, location, listingPrice, squareFootage); // Call parent constructor
        this.residentialType = residentialType;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.isFurnished = isFurnished;
    }

    // Override abstract method from Property (Polymorphism)
    @Override
    public String getPropertyType() {
        return "Residential - " + residentialType;
    }

    // ===================== Getters & Setters =====================

    public ResidentialType getResidentialType() {
        return residentialType;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public void setFurnished(boolean furnished) {
        this.isFurnished = furnished;
    }

    // Override displayInfo to add residential-specific details
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent's displayInfo first
        System.out.println("  Bedrooms    : " + numBedrooms);
        System.out.println("  Bathrooms   : " + numBathrooms);
        System.out.println("  Furnished   : " + (isFurnished ? "Yes" : "No"));
    }
}
