import java.util.LinkedList;
import java.util.Queue;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * This class represents a booking request made by a guest.
 * At this stage, a reservation only captures intent,
 * not confirmation or room allocation.
 *
 * @version 5.0
 */

class Reservation {

    /** Name of the guest making the booking. */
    private String guestName;

    /** Requested room type. */
    private String roomType;

    /**
     * Creates a new booking request.
     *
     * @param guestName name of the guest
     * @param roomType requested room type
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /** Returns guest name */
    public String getGuestName() {
        return guestName;
    }

    /** Returns requested room type */
    public String getRoomType() {
        return roomType;
    }
}


/**
 * ============================================================
 * CLASS - BookingRequestQueue
 * ============================================================
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * Manages booking requests using a queue
 * to ensure first-come-first-served order.
 *
 * @version 5.0
 */

class BookingRequestQueue {

    /** Queue that stores booking requests */
    private Queue<Reservation> requestQueue;

    /** Initializes an empty booking queue */
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the queue.
     *
     * @param reservation booking request
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    /**
     * Retrieves and removes next booking request.
     *
     * @return reservation request
     */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /**
     * Checks if there are pending booking requests.
     *
     * @return true if queue is not empty
     */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


/**
 * ============================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ============================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are accepted
 * and processed in FIFO order using a queue.
 *
 * @version 5.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        // Display application header
        System.out.println("Booking Request Queue");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Ashi", "Single");
        Reservation r2 = new Reservation("Suba", "Double");
        Reservation r3 = new Reservation("Yamarth", "Suite");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process booking requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {

            Reservation next = bookingQueue.getNextRequest();

            System.out.println(
                    "Processing booking for Guest: "
                            + next.getGuestName()
                            + ", Room Type: "
                            + next.getRoomType()
            );
        }
    }
}