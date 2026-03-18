import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 4);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) + 1);
    }

    public int getAvailability(String roomType) {
        return availability.get(roomType);
    }
}

class BookingHistory {
    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }
}

class CancellationService {
    private Set<String> rollbackRoomIds;

    public CancellationService() {
        rollbackRoomIds = new HashSet<>();
    }

    public void cancelBooking(String reservationId, BookingHistory history, RoomInventory inventory) {
        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        if (!reservation.isActive()) {
            System.out.println("Cancellation failed: Reservation already cancelled.");
            return;
        }

        rollbackRoomIds.add(reservation.getRoomId());
        inventory.incrementRoom(reservation.getRoomType());
        reservation.cancel();

        System.out.println("Cancellation successful for Reservation ID: " + reservation.getReservationId());
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("Room Type: " + reservation.getRoomType());
        System.out.println("Released Room ID: " + reservation.getRoomId());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        Reservation r1 = new Reservation("R101", "Ashi", "Single", "S101");
        history.addReservation(r1);

        System.out.println("Before Cancellation - Single Rooms Available: "
                + inventory.getAvailability("Single"));

        cancellationService.cancelBooking("R101", history, inventory);

        System.out.println("After Cancellation - Single Rooms Available: "
                + inventory.getAvailability("Single"));
    }
}