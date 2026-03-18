import java.util.LinkedList;
import java.util.Queue;

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

class RoomInventory {
    private int singleRooms;

    public RoomInventory(int singleRooms) {
        this.singleRooms = singleRooms;
    }

    public synchronized boolean allocateRoom(String roomType) {
        if (roomType.equals("Single") && singleRooms > 0) {
            singleRooms--;
            return true;
        }
        return false;
    }

    public synchronized int getAvailableRooms() {
        return singleRooms;
    }
}

class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class ConcurrentBookingProcessor extends Thread {
    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(String name, BookingQueue bookingQueue, RoomInventory inventory) {
        super(name);
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation reservation;

            synchronized (bookingQueue) {
                if (!bookingQueue.hasRequests()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            if (reservation != null) {
                boolean success = inventory.allocateRoom(reservation.getRoomType());

                if (success) {
                    System.out.println(getName() + " processed booking for "
                            + reservation.getGuestName()
                            + " - Room allocated");
                } else {
                    System.out.println(getName() + " processed booking for "
                            + reservation.getGuestName()
                            + " - No rooms available");
                }
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingQueue bookingQueue = new BookingQueue();
        RoomInventory inventory = new RoomInventory(2);

        bookingQueue.addRequest(new Reservation("Ashi", "Single"));
        bookingQueue.addRequest(new Reservation("Suba", "Single"));
        bookingQueue.addRequest(new Reservation("Yamarth", "Single"));

        ConcurrentBookingProcessor t1 =
                new ConcurrentBookingProcessor("Thread-1", bookingQueue, inventory);
        ConcurrentBookingProcessor t2 =
                new ConcurrentBookingProcessor("Thread-2", bookingQueue, inventory);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        System.out.println("Remaining Single Rooms: " + inventory.getAvailableRooms());
    }
}