package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

abstract class Item {
    private String itemId;
    private String title;
    private String author;
    //private String type;
    private double value;
    private LocalDate dueDate;
    private boolean isBestSeller;
    private boolean isAvailable;
    private boolean isRenewed;
    private List<Request> requestQueue;

    public Item(String itemId, String title, String author, boolean isBestSeller, double value) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
        this.isBestSeller = isBestSeller; 
        this.value = value;
        
        this.dueDate = null;
        this.isRenewed = false;
        this.isAvailable = true;
        
        this.requestQueue = new ArrayList<>();
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
    	return author; 
    }
    
    public double getValue() {
        return value;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailability() {
    	isAvailable = false;
    }

    public boolean isRenewed() {
        return isRenewed;
    }

    public boolean hasOutstandingRequest() {
        return !requestQueue.isEmpty();
    }

    public void addRequest(Request newRequest) {
        requestQueue.add(newRequest);
    }

    public void checkOut() {
        isAvailable = false;
        
        // Set dueDate based on item type
        if(this instanceof Book)
        {
        	if(isBestSeller)
        	{
        		dueDate = LocalDate.now().plusWeeks(2);
        	}
        	else
        	{
        		dueDate = LocalDate.now().plusWeeks(3);
        	}
        }
        else
        {
        	dueDate = LocalDate.now().plusWeeks(2);
        }
    }

    public void returnItem() {
        isAvailable = true;
        isRenewed = false;
        dueDate = null;
        
        if(!requestQueue.isEmpty()) {
        	handleRequests();
        }
    }
    
    private void handleRequests() {
        // Process requests: Notify users or update the request system
        for (Request request : requestQueue) {
            // Notify the user who made the request (implementation can vary)
            System.out.println("Notifying user " + request.getUser().getName() + " about available item " + itemId);
            // Optionally remove or update the request
        }
        // Clear requests after handling
        //requestQueue.remove(0);
    }
    
    public void renew() {
        if (canBeRenewed()) {
            isRenewed = true;
            
            if(this instanceof Book){
            	
            	if(isBestSeller){
            		dueDate = LocalDate.now().plusWeeks(2);
            	}
            	else{
            		dueDate = LocalDate.now().plusWeeks(3);
            	}
            	
            }
            else{
            	dueDate = LocalDate.now().plusWeeks(2);
            }   
            
        } else {
            System.out.println("Item cannot be renewed.");
        }
    }
    
    // Check if the item can be renewed
    public boolean canBeRenewed() {
        // Check if the item has not been renewed yet
        // and if there are no outstanding requests for the item
        return !isRenewed && !hasOutstandingRequest();
    }
    
    @Override
    public String toString() {
        return "LibraryItem{" +
                "itemId='" + itemId + '\'' +
                ", title='" + title + '\'' +
                ", value=" + value +
                ", dueDate=" + dueDate +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

	class Book extends Item {
		private boolean isBestSeller;

		public Book(String itemId, String title, String author, boolean isBestSeller, double value) {
			super(itemId, title, author, isBestSeller, value);
			this.isBestSeller = isBestSeller;
		}

		public void refMaterial() {
			this.setAvailability();
			
		}
	}

	class AudioVideo extends Item {
		public AudioVideo(String itemId, String title, String author, double value) {
			super(itemId, title, author, false, value);
		}
	}



   