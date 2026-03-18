class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }
}

class InvalidBookingValidator {
    public void validate(Reservation reservation) {
        if (reservation.getGuestName() == null || reservation.getGuestName().trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be empty.");
        }

        if (reservation.getRoomType() == null || reservation.getRoomType().trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty.");
        }

        if (!reservation.getRoomType().equalsIgnoreCase("Single")
                && !reservation.getRoomType().equalsIgnoreCase("Double")
                && !reservation.getRoomType().equalsIgnoreCase("Suite")) {
            throw new IllegalArgumentException("Invalid room type. Allowed: Single, Double, Suite.");
        }

        if (reservation.getNights() <= 0) {
            throw new IllegalArgumentException("Number of nights must be greater than 0.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        InvalidBookingValidator validator = new InvalidBookingValidator();

        Reservation validReservation = new Reservation("Ashi", "Single", 2);
        Reservation invalidReservation = new Reservation("", "Luxury", -1);

        try {
            validator.validate(validReservation);
            System.out.println("Valid booking for " + validReservation.getGuestName());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }

        try {
            validator.validate(invalidReservation);
            System.out.println("Valid booking for " + invalidReservation.getGuestName());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }

        System.out.println("System continues running safely.");
    }
}