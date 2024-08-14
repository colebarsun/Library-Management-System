package src;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private static List<Request> requestQueue = new ArrayList<>();
    private String itemId;
    private String userId;

    public Request(String itemId, String userId) {
        this.itemId = itemId;
        this.userId = userId;
        requestQueue.add(this);
    }

    public static boolean hasOutstandingRequest(String itemId) {
        return requestQueue.stream().anyMatch(request -> request.itemId.equals(itemId));
    }

    public static void fulfillRequest(String itemId) {
        requestQueue.removeIf(request -> request.itemId.equals(itemId));
    }

    public static List<String> getUserRequests(String userId) {
        List<String> userRequests = new ArrayList<>();
        for (Request request : requestQueue) {
            if (request.userId.equals(userId)) {
                userRequests.add(request.itemId);
            }
        }
        return userRequests;
    }
}
