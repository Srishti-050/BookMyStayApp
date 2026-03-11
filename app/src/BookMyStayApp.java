import java.util.*;

/*
 ============================================================
 CLASS - Reservation
 ============================================================
*/

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/*
 ============================================================
 CLASS - BookingRequestQueue
 ============================================================
*/

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


/*
 ============================================================
 CLASS - RoomInventory
 ============================================================
*/

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
    }
}


/*
 ============================================================
 CLASS - RoomAllocationService
 ============================================================
*/

class RoomAllocationService {

    /* Prevent duplicate room assignments */
    private Set<String> allocatedRoomIds;

    /* Stores assigned rooms grouped by room type */
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /*
     Confirms booking request and allocates room
    */
    public void confirmReservation(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("No available rooms for " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        inventory.decrementRoom(roomType);

        System.out.println(
                "Reservation confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room Type: "
                        + roomType
                        + ", Assigned Room ID: "
                        + roomId
        );
    }

    /*
     Generates unique room ID
    */
    private String generateRoomId(String roomType) {

        String roomId;

        do {
            int number = new Random().nextInt(900) + 100;
            roomId = roomType.substring(0,1).toUpperCase() + number;
        }
        while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}


/*
 ============================================================
 MAIN CLASS - UseCase6RoomAllocation
 ============================================================
*/

public class UseCase6RoomAllocation {

    public static void main(String[] args) {

        System.out.println("Room Allocation System\n");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocator = new RoomAllocationService();

        /* Booking requests */
        queue.addRequest(new Reservation("Ashi", "Single"));
        queue.addRequest(new Reservation("Suba", "Double"));
        queue.addRequest(new Reservation("Yamarth", "Suite"));

        /* Process requests FIFO */
        while (queue.hasPendingRequests()) {

            Reservation request = queue.getNextRequest();
            allocator.confirmReservation(request, inventory);

        }
    }
}