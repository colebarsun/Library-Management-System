package src;

import java.time.LocalDate;
import java.util.*;

class Library {
    private Map<String, User> users;
    private Map<String, Item> items;
    private int libraryCardNumberCounter;
    private static final int STARTING_CARD_NUMBER = 1000;

    public Library() {
        items = new HashMap<>();
        users = new HashMap<>();
        libraryCardNumberCounter = STARTING_CARD_NUMBER; // Starting value for card numbers
    }

    public void addUser(User user) {
        users.put(user.getLibraryCardNumber(), user);
    }

    public void addItem(Item item) {
        items.put(item.getItemId(), item);
    }

    public User getUser(String libraryCardNumber) {
        return users.get(libraryCardNumber);
    }

    public Item getItem(String itemId) {
        return items.get(itemId);
    }

    public void checkOutItem(String libraryCardNumber, String itemId) {
        User user = getUser(libraryCardNumber);
        Item item = getItem(itemId);

        if (user != null && item != null && item.isAvailable() && user.canCheckOut() && !Request.hasOutstandingRequest(itemId)) {
            user.checkOutItem(item);
            item.checkOut();
            System.out.println("Item checked out successfully.");
            System.out.println("Item due date: " + item.getDueDate());
        } else if (Request.hasOutstandingRequest(itemId)) {
            System.out.println("Item has an outstanding request and cannot be checked out.");
        } else {
            System.out.println("Cannot check out item. Either the user or item does not exist, the item is not available, or the user has reached their checkout limit.");
        }
    }

    public void returnItem(String libraryCardNumber, String itemId) {
        User user = getUser(libraryCardNumber);
        Item item = getItem(itemId);
        if (user != null && item != null) {
            user.returnItem(item);
            item.returnItem();
            System.out.println("Item returned successfully.");
        } else {
            System.out.println("Item or user not found.");
        }
    }

    public void renewItem(String libraryCardNumber, String itemId) {
        User user = getUser(libraryCardNumber);
        Item item = getItem(itemId);

        if (item == null || user == null) {
            System.out.println("Item or user not found.");
            return;
        }

        if (item.canBeRenewed() && !Request.hasOutstandingRequest(itemId)) {
            item.renew();
            System.out.println("Item renewed successfully.");
        } else {
            System.out.println("Item cannot be renewed.");
        }
    }

    public void requestItem(String libraryCardNumber, String itemId) {
        User user = getUser(libraryCardNumber);
        Item item = getItem(itemId);

        if (user == null || item == null) {
            System.out.println("Item or user not found.");
            return;
        }

        if (!item.isAvailable()) {
            new Request(itemId, libraryCardNumber);
            System.out.println("Item requested successfully.");
        } else {
            System.out.println("Item is available and does not need to be requested.");
        }
    }

    public double calculateFines(String libraryCardNumber) {
        User user = getUser(libraryCardNumber);
        if (user == null) {
            System.out.println("User not found.");
            return 0.0;
        }

        double totalFines = 0.0;
        LocalDate today = LocalDate.now();

        for (Item item : user.getCheckedOutItems()) {
            if (item.getDueDate().isBefore(today)) {
                long overdueDays = today.toEpochDay() - item.getDueDate().toEpochDay();
                double itemFine = 0.10 * overdueDays;
                itemFine = Math.min(itemFine, item.getValue());
                totalFines += itemFine;
            }
        }

        return totalFines;
    }

    // Generate a unique library card number
    public String generateLibraryCardNumber() {
        return String.valueOf(libraryCardNumberCounter++);
    }

    public String generateUserId() {
        return String.format("%04d", libraryCardNumberCounter - STARTING_CARD_NUMBER + 1);
    }

    // Check if a user with the same details already exists
    public boolean doesUserExist(String name, String address, String phoneNumber) {
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(name) &&
                user.getAddress().equalsIgnoreCase(address) &&
                user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public void displayAvailableItems() {
        System.out.println("Available items:");
        for (Item item : items.values()) {
            if (item.isAvailable() && !Request.hasOutstandingRequest(item.getItemId())) {
                System.out.println(item);
            }
        }
    }

    // Other methods and database population logic

    public void populate() {
        Book book1 = new Book("BK001", "Book1", "John Doe", true, 5.00);
        Book book2 = new Book("BK002", "Book2", "John Doe", false, 20.00);
        Book book3 = new Book("BK003", "Book3", "John Doe", true, 5.00);
        Book book4 = new Book("BK004", "Book4", "Jane Doe", false, 8.00);
        Book book5 = new Book("BK005", "Book5", "Jane Doe", true, 9.50);

        AudioVideo audio1 = new AudioVideo("AV001", "AV1", "John Doe", 15.00);
        AudioVideo audio2 = new AudioVideo("AV002", "AV2", "John Doe", 15.00);
        AudioVideo audio3 = new AudioVideo("AV003", "AV3", "John Doe", 15.00);
        AudioVideo audio4 = new AudioVideo("AV004", "AV4", "Jane Doe", 15.00);
        AudioVideo audio5 = new AudioVideo("AV005", "AV5", "Jane Doe", 15.00);

        Book ref1 = new Book("RB001", "Ref1", "John Doe", false, 2.00);
        Book ref2 = new Book("RB002", "Ref2", "John Doe", false, 2.00);
        Book ref3 = new Book("RB003", "Ref3", "John Doe", false, 2.00);
        Book ref4 = new Book("RB004", "Ref4", "John Doe", false, 2.00);
        Book ref5 = new Book("RB005", "Ref5", "John Doe", false, 2.00);

        this.addItem(book1);
        this.addItem(book2);
        this.addItem(book3);
        this.addItem(book4);
        this.addItem(book5);

        this.addItem(audio1);
        this.addItem(audio2);
        this.addItem(audio3);
        this.addItem(audio4);
        this.addItem(audio5);

        this.addItem(ref1);
        this.addItem(ref2);
        this.addItem(ref3);
        this.addItem(ref4);
        this.addItem(ref5);
    }
}
