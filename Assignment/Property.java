package Assignment;

public class Property {
    
    public enum PropertyStatus {
        AVAILABLE, RENTED, SOLD
    }

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

    public String getPropertyId() { return propertyId; }
    public String getLocation() { return location; }
    public double getListingPrice() { return listingPrice; }
    public double getSquareFootage() { return squareFootage; }
    public PropertyStatus getStatus() { return status; }

    public void setStatus(PropertyStatus status) { this.status = status; }
    public void setListingPrice(double listingPrice) {this.listingPrice = listingPrice;}
    public void setLocation(String location) {this.location = location;}
    public void setSquareFootage(double squareFootage) {this.squareFootage = squareFootage;}

    public void displayInfo() {
        System.out.println("  Property ID : " + propertyId);
        System.out.println("  Location    : " + location);
        System.out.println("  Price       : RM " + String.format("%.2f", listingPrice));
        System.out.println("  Size        : " + squareFootage + " sqft");
        System.out.println("  Status      : " + status);
    }
}
