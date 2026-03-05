package Assignment;

public abstract class Property {

    public enum PropertyStatus {
        AVAILABLE, RENTED, SOLD
    }
    public enum PropertyType {
        RESIDENTIAL, COMMERCIAL
    }

    private String propertyId;
    private String location;
    private double listingPrice;
    private double squareFootage;
    private PropertyStatus status;
    private PropertyType type;

    // Constructor
    public Property(String propertyId, String location, double listingPrice, double squareFootage,PropertyType type) {
        this.propertyId = propertyId;
        this.location = location;
        this.listingPrice = listingPrice;
        this.squareFootage = squareFootage;
        this.type = type; 
        this.status = PropertyStatus.AVAILABLE; // Default status
    }

    public abstract String getPropertyType();

    // Getters 
    public String getPropertyId() {
        return propertyId;
    }

    public String getLocation() {
        return location;
    }

    public double getListingPrice() {
        return listingPrice;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    public PropertyType getType(){
        return type;
    }
    // Setters
    public PropertyStatus getStatus() {
        return status;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }


    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public void setListingPrice(double listingPrice) {
        this.listingPrice = listingPrice;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

        public void setType(PropertyType type) {
        this.type = type;
    }


    public void displayInfo() {
        System.out.println("  Property ID : " + propertyId);
        System.out.println("  Type        : " + getPropertyType());
        System.out.println("  Location    : " + location);
        System.out.println("  Price       : RM " + String.format("%.2f", listingPrice));
        System.out.println("  Size        : " + squareFootage + " sqft");
        System.out.println("  Status      : " + status);
}}
