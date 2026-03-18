import java.util.ArrayList;
import java.util.List;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalAmount;

    public Reservation(String reservationId, String guestName, String roomType, double totalAmount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalAmount = totalAmount;
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

    public double getTotalAmount() {
        return totalAmount;
    }
}

class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return confirmedReservations;
    }
}

class BookingReportService {
    public void displayBookingHistory(BookingHistory history) {
        List<Reservation> reservations = history.getAllReservations();

        System.out.println("Booking History Report\n");

        for (Reservation reservation : reservations) {
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Guest Name: " + reservation.getGuestName());
            System.out.println("Room Type: " + reservation.getRoomType());
            System.out.println("Total Amount: " + reservation.getTotalAmount());
            System.out.println();
        }
    }

    public void displaySummary(BookingHistory history) {
        List<Reservation> reservations = history.getAllReservations();
        double totalRevenue = 0;

        for (Reservation reservation : reservations) {
            totalRevenue += reservation.getTotalAmount();
        }

        System.out.println("Booking Summary");
        System.out.println("Total Confirmed Bookings: " + reservations.size());
        System.out.println("Total Revenue: " + totalRevenue);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R101", "Ashi", "Single", 1500.0);
        Reservation r2 = new Reservation("R102", "Suba", "Double", 2500.0);
        Reservation r3 = new Reservation("R103", "Yamarth", "Suite", 5000.0);

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService();

        reportService.displayBookingHistory(history);
        reportService.displaySummary(history);
    }
}