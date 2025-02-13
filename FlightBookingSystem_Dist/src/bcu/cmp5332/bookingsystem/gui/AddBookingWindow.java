package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookingWindow extends JFrame implements ActionListener {
    private MainWindow mw;
    private JComboBox<String> customerDropdown;
    private JComboBox<String> flightDropdown;

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("Add a New Booking");
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
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            addBooking();
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addBooking() {
        try {
            // Get the selected customer and flight IDs
            String selectedCustomer = (String) customerDropdown.getSelectedItem();
            String selectedFlight = (String) flightDropdown.getSelectedItem();

            // Extract IDs from the selected items
            int customerId = Integer.parseInt(selectedCustomer.split(" - ")[0]);
            int flightId = Integer.parseInt(selectedFlight.split(" - ")[0]);

            // Create and execute the AddBooking command
            Command addBooking = new AddBooking(customerId, flightId);
            addBooking.execute(mw.getFlightBookingSystem());

            // Save data with rollback support
            mw.getFlightBookingSystem().storeDataWithRollback();

            JOptionPane.showMessageDialog(this, "Booking added successfully!");
            this.setVisible(false);
        } catch (FlightBookingSystemException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
