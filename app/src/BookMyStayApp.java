import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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
}

class SystemState implements Serializable {
    private Map<String, Integer> inventory;
    private Map<String, Reservation> bookings;

    public SystemState() {
        inventory = new HashMap<>();
        bookings = new HashMap<>();
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public Map<String, Reservation> getBookings() {
        return bookings;
    }
}

class PersistenceService {
    public void saveState(SystemState state, String fileName) {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(state);
            outputStream.close();
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public SystemState loadState(String fileName) {
        try {
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(fileName));
            SystemState state = (SystemState) inputStream.readObject();
            inputStream.close();
            System.out.println("System state restored successfully.");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            return null;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        String fileName = "bookmystay_state.ser";

        SystemState currentState = new SystemState();
        currentState.getInventory().put("Single", 5);
        currentState.getInventory().put("Double", 3);
        currentState.getInventory().put("Suite", 2);

        Reservation reservation =
                new Reservation("R101", "Ashi", "Single");
        currentState.getBookings().put(reservation.getReservationId(), reservation);

        PersistenceService persistenceService = new PersistenceService();

        System.out.println("Saving system state...");
        persistenceService.saveState(currentState, fileName);

        System.out.println("\nSimulating system restart...\n");

        SystemState restoredState = persistenceService.loadState(fileName);

        if (restoredState != null) {
            System.out.println("Recovered Inventory:");
            for (String roomType : restoredState.getInventory().keySet()) {
                System.out.println(roomType + ": " +
                        restoredState.getInventory().get(roomType));
            }

            System.out.println("\nRecovered Bookings:");
            for (Reservation booking : restoredState.getBookings().values()) {
                System.out.println("Reservation ID: " + booking.getReservationId());
                System.out.println("Guest Name: " + booking.getGuestName());
                System.out.println("Room Type: " + booking.getRoomType());
                System.out.println();
            }
        }
    }
}