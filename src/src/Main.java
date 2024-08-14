package src;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        
        library.populate();
        
        
        
        while (true) {
            if (currentUser == null) {
                System.out.println("Library System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");
                int authChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (authChoice) {
                    case 1: // Register
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        
                        System.out.print("Enter address: ");
                        String address = scanner.nextLine();
                        
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.nextLine();
                        
                        System.out.print("Enter your age: ");
                        int age = scanner.nextInt();
                        
                        if(library.doesUserExist(name, address, phoneNumber)) {
                        	System.out.println("Error: A user with the same name, address, and phone number already exists.");
                        } else {
                        	String libraryCardNumber = library.generateLibraryCardNumber();
                            String userId = library.generateUserId();
                            User newUser = new User(userId, name, address, phoneNumber, libraryCardNumber, age);
                            library.addUser(newUser);
                            
                            System.out.println("User registered successfully. Library Card Number: " + libraryCardNumber + ", User ID: " + userId);
                        }
                        
                        break;
                    case 2: // Login
                        System.out.print("Enter library card number: ");
                        String cardNumber = scanner.nextLine();
                        
                        currentUser = library.getUser(cardNumber);
                        
                        if (currentUser != null) {
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Invalid library card number. Please try again.");
                        }
                        break;
                    case 3: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Library System Menu:");
                System.out.println("1. Add Library Item");
                System.out.println("2. Check Out Item");
                System.out.println("3. Return Item");
                System.out.println("4. Renew Item");
                System.out.println("5. Request Item");
                System.out.println("6. Calculate Fines");
                System.out.println("7. Logout");
                System.out.print("Select an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1: // Add Library Item
                        System.out.println("Select item type:");
                        System.out.println("1. Book");
                        System.out.println("2. Audio/Video");
                        int itemType = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        System.out.print("Enter item ID: ");
                        String itemId = scanner.nextLine();
                        
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        
                        System.out.print("Enter value: ");
                        double value = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        
                        if (itemType == 1) {
                            System.out.print("Is it a best seller? (true/false): ");
                            boolean isBestSeller = scanner.nextBoolean();
                            scanner.nextLine(); // Consume newline
                            
                            Book newBook = new Book(itemId, title, author, isBestSeller, value);
                            
                            library.addItem(newBook);
                            
                        } else if (itemType == 2) {
                        	
                        	AudioVideo newAV = new AudioVideo(itemId, title, author, value);   	
                            library.addItem(newAV);
                        }
                        System.out.println("Item added successfully.");
                        break;
                    case 2: // Check Out Item, implement inventory list
                    	library.displayAvailableItems();
                    	
                        System.out.print("Enter item ID: ");
                        String checkOutItemId = scanner.nextLine();
                        library.checkOutItem(currentUser.getLibraryCardNumber(), checkOutItemId);
                        
                        break;
                    case 3: // Return Item
                        System.out.print("Enter item ID: ");
                        String returnItemId = scanner.nextLine();
                        library.returnItem(currentUser.getLibraryCardNumber(), returnItemId);
                        
                        System.out.println("Item returned successfully.");
                        
                        break;
                    case 4: // Renew Item
                        System.out.print("Enter item ID: ");
                        String renewItemId = scanner.nextLine();
                        
                        library.renewItem(currentUser.getLibraryCardNumber(), renewItemId);
                        System.out.println("Item renewed successfully.");
                        
                        break;
                    case 5: // Request Item
                        System.out.print("Enter item ID: ");
                        String requestItemId = scanner.nextLine();
                        library.requestItem(currentUser.getLibraryCardNumber(), requestItemId);
                        
                        System.out.println("Item requested successfully.");
                        
                        break;
                    case 6: // Calculate Fines
                        library.calculateFines(currentUser.getLibraryCardNumber());
                        
                        break;
                    case 7: // Logout
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
}