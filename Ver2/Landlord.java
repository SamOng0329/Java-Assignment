import java.util.ArrayList;

public class Landlord extends UserInfo {

    private ArrayList<Property> ownedProperty;

    public Landlord(String landLordId, String name, String email, String phone) {
        super(landLordId, name, email, phone);
        this.ownedProperty = new ArrayList<Property>();
    }

    @Override
    public String getRole() {
        return "Landlord";
    }

    public void addProperty(Property p) {
        ownedProperty.add(p);
    }

    // Check owned property for landlord then give the permission to edit
    public boolean ownsProperty(String propertyId) {
        for (Property p : ownedProperty) {
            if (p.getPropertyId().equalsIgnoreCase(propertyId))
                return true;
        }
        return false;
    }

    public Property findOwnedPropertyById(String propertyId) {
        for (Property p : ownedProperty) {
            if (p.getPropertyId().equalsIgnoreCase(propertyId))
                return p;
        }
        return null;
    }

    // Getter
    public ArrayList<Property> getOwnedProperty() {
        return ownedProperty;
    }
    public void removeProperty(Property p) {
        ownedProperty.remove(p);
    }

    // toString method add later
    @Override
    public String toString(){
        return super.toString()
        + String.format("%-16s : %s\n", "Owned Property", ownedProperty.size());
    }

}
