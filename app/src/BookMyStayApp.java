import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Reservation {
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

class AddOnService {
    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;
        List<AddOnService> services = getServices(reservationId);

        for (AddOnService service : services) {
            total += service.getServiceCost();
        }

        return total;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection\n");

        Reservation reservation = new Reservation("R101", "Ashi", "Single");

        AddOnService wifi = new AddOnService("WiFi", 500.0);
        AddOnService breakfast = new AddOnService("Breakfast", 800.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200.0);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservation.getReservationId(), wifi);
        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), airportPickup);

        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("Room Type: " + reservation.getRoomType());
        System.out.println("\nSelected Add-On Services:");

        List<AddOnService> services = manager.getServices(reservation.getReservationId());
        for (AddOnService service : services) {
            System.out.println(service.getServiceName() + " - " + service.getServiceCost());
        }

        System.out.println("\nTotal Add-On Cost: " +
                manager.calculateTotalServiceCost(reservation.getReservationId()));
    }
}