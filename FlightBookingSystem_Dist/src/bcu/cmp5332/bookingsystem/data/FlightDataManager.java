package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class FlightDataManager implements DataManager {

    private static final String FILE_NAME = "resources/data/flights.txt";
    private static final String SEPARATOR = "::";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(SEPARATOR);

                int id = Integer.parseInt(data[0]);
                String flightNumber = data[1];
                String origin = data[2];
                String destination = data[3];
                LocalDate departureDate = LocalDate.parse(data[4]); // FIXED: Parsing LocalDate

                Flight flight = new Flight(id, flightNumber, origin, destination, departureDate);
                fbs.addFlight(flight);
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Flight flight : fbs.getFlights()) {
                writer.println(flight.getId() + SEPARATOR +
                               flight.getFlightNumber() + SEPARATOR +
                               flight.getOrigin() + SEPARATOR +
                               flight.getDestination() + SEPARATOR +
                               flight.getDepartureDate());
            }
        }
    }
}
