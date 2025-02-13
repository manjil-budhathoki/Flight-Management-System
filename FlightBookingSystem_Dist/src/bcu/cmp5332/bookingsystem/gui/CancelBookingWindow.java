package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelBookingWindow extends JFrame implements ActionListener {
    private MainWindow mw;
    private JComboBox<String> customerDropdown;
    private JComboBox<String> flightDropdown;

    private JButton cancelBookingBtn = new JButton("Cancel Booking");
    private JButton closeBtn = new JButton("Close");

    public CancelBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("Cancel a Booking");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Fetch customers and flights from the system
        FlightBookingSystem fbs = mw.getFlightBookingSystem();

        // Populate customer dropdown
        customerDropdown = new JComboBox<>();
        for (Customer customer : fbs.getCustomers()) {
            customerDropdown.addItem(customer.getId() + " - " + customer.getName());
        }

        // Populate flight dropdown
        flightDropdown = new JComboBox<>();
        for (Flight flight : fbs.getFlights()) {
            flightDropdown.addItem(flight.getId() + " - " + flight.getFlightNumber());
        }

        JPanel topPanel = new JPanel(new GridLayout(3, 2));
        topPanel.add(new JLabel("Customer: "));
        topPanel.add(customerDropdown);
        topPanel.add(new JLabel("Flight: "));
        topPanel.add(flightDropdown);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(cancelBookingBtn);
        bottomPanel.add(closeBtn);

        cancelBookingBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBookingBtn) {
            cancelBooking();
        } else if (e.getSource() == closeBtn) {
            this.setVisible(false);
        }
    }

    private void cancelBooking() {
        try {
            // Get the selected customer and flight IDs
            String selectedCustomer = (String) customerDropdown.getSelectedItem();
            String selectedFlight = (String) flightDropdown.getSelectedItem();

            // Extract IDs from the selected items
            int customerId = Integer.parseInt(selectedCustomer.split(" - ")[0]);
            int flightId = Integer.parseInt(selectedFlight.split(" - ")[0]);

            // Create and execute the CancelBooking command
            Command cancelBooking = new CancelBooking(customerId, flightId);
            cancelBooking.execute(mw.getFlightBookingSystem());

            // Save data with rollback support
            mw.getFlightBookingSystem().storeDataWithRollback();

            JOptionPane.showMessageDialog(this, "Booking canceled successfully!");
            this.setVisible(false);
        } catch (FlightBookingSystemException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
