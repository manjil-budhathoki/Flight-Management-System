package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

public class Booking {
    private final Customer customer;
    private final Flight flight;
    private final LocalDate bookingDate;

    public Booking(Customer customer, Flight flight, LocalDate bookingDate) {
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getDetailsShort() {
        return "Booking for Flight #" + flight.getId() + " - " + flight.getFlightNumber();
    }
}
