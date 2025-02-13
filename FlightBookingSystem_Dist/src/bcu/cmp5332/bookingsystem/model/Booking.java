package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

public class Booking {
    private final Customer customer;
    private Flight flight;
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

    public void setFlight(Flight newFlight) {
        this.flight = newFlight;
    }
    public String getDetails() {
        return "Customer: " + customer.getName() +
               " | Flight: " + flight.getFlightNumber() +
               " | Booking Date: " + bookingDate;
    }
}
