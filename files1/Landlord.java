package files1;

import java.util.ArrayList;

/**
 * Represents a Landlord who owns properties in the system.
 *
 * OOP Concepts:
 * - Inheritance   : extends UserInfo (Landlord IS-A UserInfo)
 * - Polymorphism  : overrides getRole() and displayInfo()
 * - Encapsulation : ownedProperties list is private
 * - Composition   : Landlord owns an ArrayList of Property objects
 */
public class Landlord extends UserInfo {

    private ArrayList<Property> ownedProperties; // Composition
    private String bankAccount; // Bank account number (optional, for payment purposes)

    // Constructor - matches UserInfo(personId, name, email, phone)
    public Landlord(String landlordId, String name, String email, String phone) {
        super(landlordId, name, email, phone);
        this.ownedProperties = new ArrayList<>();
        this.bankAccount = "";
    }

    // Constructor used when loading from database (includes bankAccount)
    public Landlord(String landlordId, String name, String email, String phone, String bankAccount) {
        super(landlordId, name, email, phone);
        this.ownedProperties = new ArrayList<>();
        this.bankAccount = (bankAccount != null) ? bankAccount : "";
    }

    // ===================== Abstract method from UserInfo =====================

    @Override
    public String getRole() {
        return "Landlord";
    }

    // ===================== Property Ownership =====================

    /**
     * Assigns a property to this landlord's portfolio.
     * Called when a property is registered under this landlord.
     */
    public void addOwnedProperty(Property p) {
        ownedProperties.add(p);
    }

    /**
     * Checks if this landlord owns the given property ID.
     * Used as a security check before allowing edits.
     */
    public boolean ownsProperty(String propertyId) {
        for (Property p : ownedProperties) {
            if (p.getPropertyId().equalsIgnoreCase(propertyId)) return true;
        }
        return false;
    }

    public Property findOwnedPropertyById(String propertyId) {
        for (Property p : ownedProperties) {
            if (p.getPropertyId().equalsIgnoreCase(propertyId)) return p;
        }
        return null;
    }

    // ===================== Getters & Setters =====================

    public ArrayList<Property> getOwnedProperties() { return ownedProperties; }
    public String getBankAccount() { return bankAccount; }

    // ===================== Display =====================

    @Override
    public void displayInfo() {
        System.out.println("  ==========================================");
        super.displayInfo(); // ID, Name, Role, Email, Phone
        System.out.println("  Properties owned: " + ownedProperties.size());
        System.out.println("  ==========================================");
    }
}
