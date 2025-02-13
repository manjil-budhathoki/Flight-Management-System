package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerListWindow extends JFrame {
    private final JList<String> customerList;
    private final DefaultListModel<String> customerListModel;

    public CustomerListWindow(FlightBookingSystem fbs) {
        setTitle("Customer List");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        customerListModel = new DefaultListModel<>();
        for (Customer customer : fbs.getCustomers()) {
            customerListModel.addElement(customer.getId() + " - " + customer.getName() + " (Bookings: " + customer.getBookings().size() + ")");
        }

        customerList = new JList<>(customerListModel);
        JScrollPane scrollPane = new JScrollPane(customerList);

        customerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click to open booking details
                    String selectedCustomer = customerList.getSelectedValue();
                    showBookingDetails(selectedCustomer, fbs);
                }
            }
        });

        add(scrollPane);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void showBookingDetails(String customerInfo, FlightBookingSystem fbs) {
        String[] split = customerInfo.split(" - ");
        int customerId = Integer.parseInt(split[0]);
        Customer customer = fbs.getCustomerById(customerId);

        StringBuilder bookings = new StringBuilder("Bookings for " + customer.getName() + ":\n");
        if (customer.getBookings().isEmpty()) {
            bookings.append("No bookings for this customer.");
        } else {
            customer.getBookings().forEach(booking -> bookings.append("Flight: ").append(booking.getFlight().getFlightNumber()).append("\n"));
        }

        JOptionPane.showMessageDialog(this, bookings.toString(), "Booking Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
}
