// ListBookings.java
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.util.List;

public class ListBookings implements Command {
    @Override
    public void execute(FlightBookingSystem fbs) {
        List<Booking> bookings = fbs.getAllBookings();
        
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("Listing all bookings:");
            for (Booking booking : bookings) {
                System.out.println("Customer: " + booking.getCustomer().getName() +
                                   " | Flight: " + booking.getFlight().getFlightNumber() +
                                   " | Date: " + booking.getBookingDate());
            }
            System.out.println("Total bookings: " + bookings.size());
        }
    }
}
