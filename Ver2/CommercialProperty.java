// package Assignment;

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
        super(propertyId, Location, listingPrice, squareFootage, PropertyType.COMMERCIAL);
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

    public boolean getHasLoadingDock() {
        return hasLoadingDock;
    }

    public void setCommercialType(CommercialType commercialType) {
        this.commercialType = commercialType;
    }

    public void setNumParkingSpots(int numParkingSpots) {
        this.numParkingSpots = numParkingSpots;
    }

    public void setHasLoadingDock(boolean hasLoadingDock) {
        this.hasLoadingDock = hasLoadingDock;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("%-16s : %d\n", "Parking Spots", numParkingSpots);
        System.out.printf("%-16s : %s\n", "Loading Dock", (hasLoadingDock ? "Yes" : "No"));
    }

}
