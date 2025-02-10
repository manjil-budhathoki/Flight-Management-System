package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int id;
    private final String name;
    private final String phone;
    private final List<Booking> bookings = new ArrayList<>();

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        if (bookings.contains(booking)) {
            throw new IllegalArgumentException("Booking already exists for this customer.");
        }
        bookings.add(booking);
    }

    public void cancelBookingForFlight(Flight flight) {
        bookings.removeIf(booking -> booking.getFlight().equals(flight));
    }

    public String getDetailsShort() {
        return "Customer #" + id + ": " + name + " - " + phone;
    }

    public String getDetailsLong() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDetailsShort()).append("\nBookings:\n");
        for (Booking booking : bookings) {
            sb.append(" * ").append(booking.getDetailsShort()).append("\n");
        }
        return sb.toString();
    }
}
