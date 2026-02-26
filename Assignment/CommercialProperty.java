package Assignment;

public class CommercialProperty extends Property {

    public enum CommercialType {
        OFFICE, RETAIL, INDUSTRIAL
    }

    private CommercialType commercialType;
    private int numParkingSpots;
    private boolean hasLoadingDock;

    // Constructor
    public CommercialProperty(String propertyId, String Location, double listingPrice,
            double squareFootage, CommercialType commercialType,
            int numParkingSpots, boolean hasLoadingDock) {
        super(propertyId, Location, listingPrice, squareFootage);
        this.commercialType = commercialType;
        this.numParkingSpots = numParkingSpots;
        this.hasLoadingDock = hasLoadingDock;
    }

    @Override
    public String getPropertyType() {
        return "Commercial - " + commercialType;
    }

    // Getters
    public CommercialType getCommercialType() {
        return commercialType;
    }

    public int getNumParkingSpots() {
        return numParkingSpots;
    }

    public boolean isHasLoadingDock() {
        return hasLoadingDock;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Parking Spots: " + numParkingSpots);
        System.out.println("Loading Dock : " + (hasLoadingDock ? "Yes" : "No"));
    }

}
