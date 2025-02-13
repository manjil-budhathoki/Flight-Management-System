package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class FlightListWindow extends JFrame implements ActionListener {
    private final MainWindow mw;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton clearButton;
    private final JTable flightTable;
    private final JScrollPane scrollPane;

    public FlightListWindow(MainWindow mw) {
        this.mw = mw;
        setTitle("Flight List");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel: Search and filter options
        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        searchButton.addActionListener(this);
        clearButton.addActionListener(this);

        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);

        // Table to display flights
        flightTable = new JTable();
        scrollPane = new JScrollPane(flightTable);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Display all flights on startup
        displayFlights(mw.getFlightBookingSystem().getFlights());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String searchText = searchField.getText().trim();
            FlightBookingSystem fbs = mw.getFlightBookingSystem();
            List<Flight> filteredFlights = fbs.getFlights().stream()
                .filter(f -> f.getFlightNumber().contains(searchText) ||
                             f.getOrigin().contains(searchText) ||
                             f.getDestination().contains(searchText))
                .collect(Collectors.toList());
            displayFlights(filteredFlights);
        } else if (e.getSource() == clearButton) {
            searchField.setText("");
            displayFlights(mw.getFlightBookingSystem().getFlights());
        }
    }

    private void displayFlights(List<Flight> flights) {
        String[] columns = {"Flight No", "Origin", "Destination", "Departure Date", "Capacity", "Price"};
        Object[][] data = new Object[flights.size()][columns.length];
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
            data[i][4] = flight.getCapacity();
            data[i][5] = flight.getPrice();
        }
        flightTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }
}
