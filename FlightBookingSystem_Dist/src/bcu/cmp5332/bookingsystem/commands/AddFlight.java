package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.LocalDate;

public class AddFlight implements Command {
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity;
    private final double price;

    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public void execute(FlightBookingSystem fbs) {
        int newId = fbs.getFlights().size() + 1;
        Flight flight = new Flight(newId, flightNumber, origin, destination, departureDate, capacity, price);
        fbs.addFlight(flight);
        System.out.println("Flight added: " + flight.getDetailsShort());
    }
}
