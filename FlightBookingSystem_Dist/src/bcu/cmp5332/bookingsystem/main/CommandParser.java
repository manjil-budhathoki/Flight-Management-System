// CommandParser.java
package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CommandParser {
    public static Command parse(String input) throws Exception {
        Scanner scanner = new Scanner(input);
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Invalid command.");
        }

        String command = scanner.next().toLowerCase();
        switch (command) {
        
	        case "addcustomer":
	            if (!scanner.hasNext()) throw new IllegalArgumentException("Usage: addcustomer [name] [phone] [email]");
	            
	            String name = scanner.next();
	            String phone = scanner.next();
	            
	            if (!scanner.hasNext()) throw new IllegalArgumentException("Usage: addcustomer [name] [phone] [email]");
	            String email = scanner.next();
	
	            return new AddCustomer(name, phone, email);

            
            case "listcustomers":
                return new ListCustomers();
            
            case "addflight":
                if (!scanner.hasNext()) throw new IllegalArgumentException("Usage: addflight [flightNumber] [origin] [destination] [date] [capacity] [price]");
                
                String flightNumber = scanner.next();
                String origin = scanner.next();
                String destination = scanner.next();
                
                if (!scanner.hasNext()) throw new IllegalArgumentException("Usage: addflight [flightNumber] [origin] [destination] [date] [capacity] [price]");
                String dateStr = scanner.next();

                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Capacity must be an integer.");
                int capacity = scanner.nextInt();

                if (!scanner.hasNextDouble()) throw new IllegalArgumentException("Price must be a decimal number.");
                double price = scanner.nextDouble();

                try {
                    LocalDate departureDate = LocalDate.parse(dateStr);
                    return new AddFlight(flightNumber, origin, destination, departureDate, capacity, price);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
                }

            
            case "listflights":
                return new ListFlights();
                
            case "listbookings":
                return new ListBookings();

            
            case "addbooking":
                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Usage: addbooking [customerId] [flightId]");
                int customerId = scanner.nextInt();
                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Usage: addbooking [customerId] [flightId]");
                int flightId = scanner.nextInt();
                return new AddBooking(customerId, flightId);
            
                
            case "editbooking":
                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Usage: editbooking [customerId] [newFlightId]");
                int customerId1 = scanner.nextInt();

                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Usage: editbooking [customerId] [newFlightId]");
                int newFlightId = scanner.nextInt();

                return new EditBooking(customerId1, newFlightId);

            
            case "cancelbooking":
                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Usage: cancelbooking [customerId] [flightId]");
                int custId = scanner.nextInt();
                if (!scanner.hasNextInt()) throw new IllegalArgumentException("Usage: cancelbooking [customerId] [flightId]");
                int fltId = scanner.nextInt();
                return new CancelBooking(custId, fltId);
            
            case "help":
                printHelp();
                return new NoOpCommand();
            
            case "exit":
                System.exit(0);
            
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("listflights                               print all flights");
        System.out.println("listcustomers                             print all customers");
        System.out.println("addflight [flightNumber] [origin] [destination] [date]");
        System.out.println("addcustomer [name] [phone]");
        System.out.println("showflight [flight id]                    show flight details");
        System.out.println("showcustomer [customer id]                show customer details");
        System.out.println("addbooking [customer id] [flight id]      add a new booking");
        System.out.println("cancelbooking [customer id] [flight id]   cancel a booking");
        System.out.println("editbooking [booking id] [flight id]      update a booking");
        System.out.println("loadgui                                   loads the GUI version of the app");
        System.out.println("help                                      prints this help message");
        System.out.println("exit                                      exits the program");
    }
}
