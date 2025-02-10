package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.*;

import java.time.LocalDate;

public class AddBooking implements Command {
    private final int customerId;
    private final int flightId;

    public AddBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem fbs) {
        try {
            Customer customer = fbs.getCustomerById(customerId);
            Flight flight = fbs.getFlightById(flightId);

            if (customer == null) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
            }
            if (flight == null) {
                throw new IllegalArgumentException("Flight with ID " + flightId + " not found.");
            }

            Booking booking = new Booking(customer, flight, fbs.getSystemDate());
            customer.addBooking(booking);
            flight.addPassenger(customer);

            System.out.println("Booking successfully created for " + customer.getName() +
                    " on Flight " + flight.getFlightNumber());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
