package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;

import javax.swing.*;
import java.awt.*;

public class BookingDetailsWindow extends JFrame {
    public BookingDetailsWindow(Customer customer) {
        setTitle("Booking Details for " + customer.getName());
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultListModel<String> bookingListModel = new DefaultListModel<>();
        for (Booking booking : customer.getBookings()) {
            bookingListModel.addElement(
                "Booking Date: " + booking.getBookingDate() +
                " | Flight: " + booking.getFlight().getFlightNumber() +
                " | From: " + booking.getFlight().getOrigin() +
                " | To: " + booking.getFlight().getDestination()
            );
        }

        JList<String> bookingList = new JList<>(bookingListModel);
        JScrollPane scrollPane = new JScrollPane(bookingList);

        if (customer.getBookings().isEmpty()) {
            JLabel noBookingsLabel = new JLabel("No bookings available for this customer.");
            noBookingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(noBookingsLabel, BorderLayout.CENTER);
        } else {
            add(scrollPane, BorderLayout.CENTER);
        }

        setVisible(true);
        setLocationRelativeTo(null);
    }
}
