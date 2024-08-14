package src;

/**
 * The Fine class represents a fine for an overdue item.
 * It keeps track of the checkout associated with the fine and the amount of the fine.
 */

public class Fine {
	private static int idCounter = 1;	// Static counter to assign unique IDs to each fine
    	private int fineId;			// Unique ID for this fine
    	private Checkout checkout;		// The checkout associated with this fine
    	private double fineAmount;		// The amount of the fine

	/**
	 * Constructor to create a new fine.
	 * 
	 * @param checkout: the checkout associated with the fine
	 * @param fineAmount: the amount of the fine
	 */
	public Fine(Checkout checkout, double fineAmount) {
        	this.fineId = idCounter++;	// Assign a unique ID to this fine
        	this.checkout = checkout;
        	this.fineAmount = fineAmount;
    	}

	// Getter methods for various attributes
    	public int getFineId() {
        	return fineId;
    	}

    	public Checkout getCheckout() {
        	return checkout;
    	}

    	public double getFineAmount() {
        	return fineAmount;
    	}
}