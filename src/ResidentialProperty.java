public class ResidentialProperty extends Property {
    private int numBedrooms;
    private int numBathrooms;
    private boolean isFurnished;
    private ResidentialType residentialType;

    public enum ResidentialType {
        APARTMENT, HOUSE // unnecessary to have condo since it is also a type of house
    }

    public ResidentialProperty(String propertyId, String Location, double listingPrice, ResidentialType residentialType,
            double squareFootage, int numBedrooms, int numBathrooms, boolean isFurnished) {
        super(propertyId, Location, listingPrice, squareFootage, PropertyType.RESIDENTIAL);
        this.residentialType = residentialType;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.isFurnished = isFurnished;
    }

    @Override
    public String getPropertyType() {
        return "Residential - " + residentialType;
    }

    // Getter 
    public ResidentialType getResidentialType() {
        return residentialType;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public boolean getIsFurnished() {
        return isFurnished;
    }

    // Setters
    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void setIsFurnished(boolean isFurnished) {
        this.isFurnished = isFurnished;
    }

    @Override   
    public void displayInfo(){
        super.displayInfo();
        System.out.printf("| %-16s : %d\n", "Bedrooms", numBedrooms);
        System.out.printf("| %-16s : %d\n", "Bathrooms", numBathrooms);
        System.out.printf("| %-16s : %s\n", "Furnished", (isFurnished ? "Yes" : "No"));
        System.out.println("=".repeat(40));
    }
}
