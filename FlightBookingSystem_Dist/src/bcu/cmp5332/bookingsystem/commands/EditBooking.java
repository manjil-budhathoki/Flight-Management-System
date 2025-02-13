// EditBooking.java
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.*;
import bcu.cmp5332.bookingsystem.data.BookingDataManager;

public class EditBooking implements Command {
    private final int customerId;
    private final int newFlightId;

    public EditBooking(int customerId, int newFlightId) {
        this.customerId = customerId;
        this.newFlightId = newFlightId;
    }

    @Override
    public void execute(FlightBookingSystem fbs) {
        try {
            Customer customer = fbs.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
            }

            Booking bookingToEdit = null;
            Flight oldFlight = null;

            for (Booking booking : customer.getBookings()) {
                if (booking.getFlight().getId() != newFlightId) {
                    oldFlight = booking.getFlight();
                    bookingToEdit = booking;
                    break;
                }
            }

            if (bookingToEdit == null) {
                throw new IllegalArgumentException("No existing booking found for customer " + customer.getName());
            }

            Flight newFlight = fbs.getFlightById(newFlightId);
            if (newFlight == null) {
                throw new IllegalArgumentException("Flight with ID " + newFlightId + " not found.");
            }

            for (Booking existingBooking : customer.getBookings()) {
                if (existingBooking.getFlight().getId() == newFlightId) {
                    throw new IllegalArgumentException("Customer " + customer.getName() + " is already booked on Flight " + newFlight.getFlightNumber() + ".");
                }
            }

            if (newFlight.getPassengers().size() >= newFlight.getCapacity()) {
                throw new IllegalArgumentException("Flight " + newFlight.getFlightNumber() + " is fully booked. Cannot change booking.");
            }

            oldFlight.removePassenger(customer);
            newFlight.addPassenger(customer);
            bookingToEdit.setFlight(newFlight);

            try {
                BookingDataManager dataManager = new BookingDataManager();
                dataManager.storeData(fbs);
            } catch (Exception e) {
                System.err.println("Error saving updated booking: " + e.getMessage());
            }

            System.out.println("Booking updated: " + customer.getName() + " is now booked on Flight " + newFlight.getFlightNumber());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}