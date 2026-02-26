package Example;

/**
 * Abstract base class for all property types.
 *
 * OOP Concepts:
 * - Abstraction: Cannot be instantiated, forces subclasses to implement getPropertyType()
 * - Encapsulation: All attributes are private
 * - Polymorphism: displayInfo() and getPropertyType() are overridden by subclasses
 */
public abstract class Property {

    // Enum for property status
    public enum PropertyStatus {
        AVAILABLE, RENTED, SOLD
    }

    // Private attributes (Encapsulation)
    private String propertyId;
    private String location;
    private double listingPrice;
    private double squareFootage;
    private PropertyStatus status;

    // Constructor
    public Property(String propertyId, String location, double listingPrice, double squareFootage) {
        this.propertyId = propertyId;
        this.location = location;
        this.listingPrice = listingPrice;
        this.squareFootage = squareFootage;
        this.status = PropertyStatus.AVAILABLE; // Default status
    }

    // Abstract method - subclasses MUST implement this (Abstraction + Polymorphism)
    public abstract String getPropertyType();

    // ===================== Getters & Setters =====================

    public String getPropertyId() { return propertyId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getListingPrice() { return listingPrice; }
    public void setListingPrice(double listingPrice) {
        if (listingPrice <= 0) {
            throw new IllegalArgumentException("Listing price must be greater than zero.");
        }
        this.listingPrice = listingPrice;
    }

    public double getSquareFootage() { return squareFootage; }

    public PropertyStatus getStatus() { return status; }
    public void setStatus(PropertyStatus status) { this.status = status; }

    // Display basic property info (extended by subclasses)
    public void displayInfo() {
        System.out.println("  Property ID : " + propertyId);
        System.out.println("  Type        : " + getPropertyType());
        System.out.println("  Location    : " + location);
        System.out.println("  Price       : RM " + String.format("%.2f", listingPrice) + "/month");
        System.out.println("  Size        : " + squareFootage + " sqft");
        System.out.println("  Status      : " + status);
    }
}
