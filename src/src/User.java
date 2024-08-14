package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
	private String userID;
    private String name;
    private String address;
    private String phoneNumber;
    private String libraryCardNumber;
    private int age;
    private List<Item> checkedOutItems;
    private static final int CHILD_MAX_ITEMS = 5;
    private static final int ADULT_MAX_ITEMS = 10; // Assuming a max limit for adults as well
    private static final int CHILD_AGE_LIMIT = 12;

    public User(String userID, String name, String address, String phoneNumber, String libraryCardNumber, int age) {
        this.userID = userID;
    	this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.libraryCardNumber = libraryCardNumber;
        this.age = age;
        this.checkedOutItems = new ArrayList<>();
    }

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public boolean canCheckOut() {
    	
        return checkedOutItems.size() < getMaxItems();
    }

    private int getMaxItems() {
        return age <= CHILD_AGE_LIMIT ? CHILD_MAX_ITEMS : ADULT_MAX_ITEMS;
    }

    public void checkOutItem(Item item) {
        if (canCheckOut()) {
            checkedOutItems.add(item);
        }
    }

    public void returnItem(Item item) {
        checkedOutItems.remove(item);
    }

    public List<Item> getCheckedOutItems() {
        return checkedOutItems;
    }

    public void renewItem(Item item) {
        if (item.canBeRenewed()) {
            item.renew();
        }
    }
    
    public double calculateFines() {
        double totalFines = 0.0;
        LocalDate today = LocalDate.now();

        for (Item item : checkedOutItems) {
            if (item.getDueDate().isBefore(today)) {
                // Calculate overdue days
                long overdueDays = today.toEpochDay() - item.getDueDate().toEpochDay();
                
                // Calculate fine per item
                double itemFine = 0.10 * overdueDays;
                
                // Cap the fine to the value of the item
                itemFine = Math.min(itemFine, item.getValue());
                
                // Add to total fines
                totalFines += itemFine;
            }
        }
        
        return totalFines;
    }

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}
}