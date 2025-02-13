package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.data.BookingDataManager;

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

            // ✅ Prevent duplicate bookings
            for (Booking booking : customer.getBookings()) {
                if (booking.getFlight().getId() == flightId) {
                    throw new IllegalArgumentException("Customer " + customer.getName() + " has already booked Flight " + flight.getFlightNumber() + ".");
                }
            }

            // ✅ Prevent overbooking
            if (flight.getPassengers().size() >= flight.getCapacity()) {
                throw new IllegalArgumentException("Flight " + flight.getFlightNumber() + " is fully booked. Cannot add more passengers.");
            }

            // ✅ Add the booking
            Booking booking = new Booking(customer, flight, fbs.getSystemDate());
            customer.addBooking(booking);
            flight.addPassenger(customer);

            // ✅ Save immediately after adding a booking
            try {
                BookingDataManager dataManager = new BookingDataManager();
                dataManager.storeData(fbs);
            } catch (Exception e) {
                System.err.println("Error saving booking data: " + e.getMessage());
            }

            System.out.println("Booking successfully created for " + customer.getName() + " on Flight " + flight.getFlightNumber());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
