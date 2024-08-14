package src;
import java.util.Date;

/**
 * The Checkout class represents the checkout of an item by a user.
 * It keeps track of the user, the item, the checkout and due dates,
 * whether the item has been returned, and the number of renewals.
 */

public class Checkout {
	private static int idCounter = 1;	// Static counter to assign unique IDs to each checkout
	private int checkoutId;			// Unique ID for this checkout
	private User user;			// The user who checked out the item
	private Item item;			// The item that was checked out
	private Date checkoutDate;		// The date the item is due back
	private Date dueDate;			// Whether the item has been returned
	private boolean returned;		// Number of items the item has been renewed
	private int renewalCount;

	/**
	 * Constructor to create a new checkout.
	 *
	 * @param user: the user who is checking out the item
	 * @param checkoutDate: the date the item is checked out
	 * @param dueDate: the date the item is due back
	 */
	public Checkout(User user, Item item, Date checkoutDate, Date dueDate) {
		this.checkoutId = idCounter++;	// Assign a unique ID to this checkout
		this.user = user;
		this.item = item;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.returned = false;
		this.renewalCount = 0;		// Initialize the renewal count to 0
    	}

    	// Getter methods for various attributes
	public int getCheckoutId() {
        	return checkoutId;
    	}

    	public User getUser() {
        	return user;
    	}

    	public Item getItem() {
        	return item;
    	}

    	public Date getCheckoutDate() {
        	return checkoutDate;
    	}

    	public Date getDueDate() {
        	return dueDate;
    	}

    	public void setDueDate(Date date) {
        	dueDate = date;
    	}

    	public boolean isReturned() {
        	return returned;
    	}

    	public void returnItem() {
        	this.returned = true;
    	}

    	public int getRenewalCount() {
        	return renewalCount;
    	}

    	public void incrementRenewalCount() {
        	this.renewalCount++;
    	}
}
