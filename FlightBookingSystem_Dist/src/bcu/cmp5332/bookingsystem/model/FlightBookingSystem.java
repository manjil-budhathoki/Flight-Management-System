package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.util.*;

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
}
