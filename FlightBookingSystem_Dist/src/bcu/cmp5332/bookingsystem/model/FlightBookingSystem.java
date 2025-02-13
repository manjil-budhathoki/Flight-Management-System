package bcu.cmp5332.bookingsystem.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javax.swing.JOptionPane;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

public class FlightBookingSystem {
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final Map<Integer, Flight> flights = new HashMap<>();
    private LocalDate systemDate = LocalDate.now();

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(LocalDate systemDate) {
        this.systemDate = systemDate;
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    public Customer getCustomerById(int id) {
        if (!customers.containsKey(id)) {
            throw new IllegalArgumentException("Customer ID " + id + " not found.");
        }
        return customers.get(id);
    }

    public void addCustomer(Customer customer) {
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer ID already exists.");
        }
        customers.put(customer.getId(), customer);
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights.values());
    }

    public Flight getFlightById(int id) {
        if (!flights.containsKey(id)) {
            throw new IllegalArgumentException("Flight ID " + id + " not found.");
        }
        return flights.get(id);
    }

    public void addFlight(Flight flight) {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Flight ID already exists.");
        }
        flights.put(flight.getId(), flight);
    }

    // ✅ Fix: Use customers.values() to iterate over the map correctly
    public List<Booking> getAllBookings() {
        List<Booking> allBookings = new ArrayList<>();

        for (Customer customer : customers.values()) {  // ✅ Corrected iteration
            allBookings.addAll(customer.getBookings());
        }

        return allBookings;
    }
    
    public void storeDataWithRollback() {
        try {
            FlightBookingSystemData.store(this); // Save data to files
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Data saving failed: " + ex.getMessage());
            rollbackChanges(); // Restore in-memory data
        }
    }

    private void rollbackChanges() {
        try {
            // Reload data from files to undo in-memory changes
            FlightBookingSystem reloadedSystem = FlightBookingSystemData.load();
            this.customers.clear();
            this.customers.putAll(reloadedSystem.customers);
            this.flights.clear();
            this.flights.putAll(reloadedSystem.flights);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error during rollback: " + ex.getMessage(), "Critical Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Rollback failed: " + ex.getMessage());
        }
    }
    }

