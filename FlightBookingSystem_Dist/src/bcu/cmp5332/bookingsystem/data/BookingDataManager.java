// BookingDataManager.java
package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class BookingDataManager implements DataManager {

    private static final String FILE_NAME = "resources/data/bookings.txt";
    private static final String SEPARATOR = "::";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(SEPARATOR);

                if (data.length < 3) {
                    System.err.println("Skipping invalid booking entry: " + line);
                    continue; // Skip incomplete data
                }

                int customerId = Integer.parseInt(data[0]);
                int flightId = Integer.parseInt(data[1]);
                LocalDate bookingDate = LocalDate.parse(data[2]);

                Customer customer = fbs.getCustomerById(customerId);
                Flight flight = fbs.getFlightById(flightId);

                if (customer == null || flight == null) {
                    System.err.println("Skipping booking due to missing customer/flight: " + line);
                    continue; // Skip missing data
                }

                Booking booking = new Booking(customer, flight, bookingDate);
                customer.addBooking(booking);
                flight.addPassenger(customer);
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        File file = new File(FILE_NAME);
        file.getParentFile().mkdirs(); // Ensure directories exist

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Customer customer : fbs.getCustomers()) {
                for (Booking booking : customer.getBookings()) {
                    writer.println(customer.getId() + SEPARATOR +
                                   booking.getFlight().getId() + SEPARATOR +
                                   booking.getBookingDate());
                }
            }
        }
    }
}
