package files1;

/**
 * Custom exception for the Property Rental System.
 * Thrown when a business rule is violated.
 *
 * OOP Concepts:
 * - Inheritance: Extends Java's built-in Exception class
 */
public class PropertyRentalException extends Exception {

    // Constructor with message
    public PropertyRentalException(String message) {
        super(message);
    }
}
