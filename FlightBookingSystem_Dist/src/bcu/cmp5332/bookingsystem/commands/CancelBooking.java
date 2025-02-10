package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command {
    private final int customerId;
    private final int flightId;

    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem fbs) {
        Customer customer = fbs.getCustomerById(customerId);
        Flight flight = fbs.getFlightById(flightId);

        customer.cancelBookingForFlight(flight);
        flight.removePassenger(customer);

        System.out.println("Booking cancelled successfully for customer " + customer.getName() +
                " on Flight " + flight.getFlightNumber());
    }
}
