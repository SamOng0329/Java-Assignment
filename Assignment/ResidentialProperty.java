package Assignment;

public class ResidentialProperty extends Property {
    private int numBedrooms;
    private int numBathrooms;
    private boolean hasGarden;
    private ResidentialType residentialType;


    public enum ResidentialType {
        APARTMENT, HOUSE, CONDO
    }

    public ResidentialProperty(String propertyId, String Location, double listingPrice,ResidentialType residentialType,
            double squareFootage, int numBedrooms, int numBathrooms, boolean hasGarden){
        super(propertyId, Location, listingPrice, squareFootage, PropertyType.RESIDENTIAL);
        this.residentialType = residentialType;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.hasGarden = hasGarden;
    }
    public String getResidentialType() {
        return residentialType.name();
    }
    public int getNumBedrooms() {
        return numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    public void getResidentialType(ResidentialType residentialType) {
        this.residentialType = residentialType;
    }

    public void setResidentialType(ResidentialType residentialType) {
        this.residentialType = residentialType;
    }
    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }



    @Override
    public String getPropertyType() {
        return "Residential";
    }
}
