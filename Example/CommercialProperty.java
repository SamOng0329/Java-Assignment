package files1;

/**
 * Represents a Commercial property (Office or Warehouse).
 *
 * OOP Concepts:
 * - Inheritance: Extends Property
 * - Polymorphism: Overrides getPropertyType() and displayInfo()
 */
public class CommercialProperty extends Property {

    // Inner enum for commercial subtypes
    public enum CommercialType {
        OFFICE, WAREHOUSE
    }

    private CommercialType commercialType;
    private int numParkingSpots;
    private boolean hasLoadingDock;

    // Constructor
    public CommercialProperty(String propertyId, String location, double listingPrice,
                              double squareFootage, CommercialType commercialType,
                              int numParkingSpots, boolean hasLoadingDock) {
        super(propertyId, location, listingPrice, squareFootage); // Call parent constructor
        this.commercialType = commercialType;
        this.numParkingSpots = numParkingSpots;
        this.hasLoadingDock = hasLoadingDock;
    }

    // Override abstract method from Property (Polymorphism)
    @Override
    public String getPropertyType() {
        return "Commercial - " + commercialType;
    }

    // ===================== Getters & Setters =====================

    public CommercialType getCommercialType() { return commercialType; }
    public int getNumParkingSpots() { return numParkingSpots; }
    public boolean isHasLoadingDock() { return hasLoadingDock; }

    // Override displayInfo to add commercial-specific details
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent's displayInfo first
        System.out.println("  Parking     : " + numParkingSpots + " spots");
        System.out.println("  Loading Dock: " + (hasLoadingDock ? "Yes" : "No"));
    }
}
