package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Flight {
    private final int id;
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final Set<Customer> passengers = new HashSet<>();

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    public int getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Set<Customer> getPassengers() {
        return passengers;
    }

    public void addPassenger(Customer customer) {
        passengers.add(customer);
    }

    public void removePassenger(Customer customer) {
        passengers.remove(customer);
    }

    public String getDetailsShort() {
        return "Flight #" + id + ": " + flightNumber + " from " + origin + " to " + destination +
               " departing on " + departureDate;
    }
}
