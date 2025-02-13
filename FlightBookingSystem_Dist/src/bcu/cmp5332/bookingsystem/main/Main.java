package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.gui.MainWindow;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        FlightBookingSystem fbs;

        try {
            // Load system state
            fbs = FlightBookingSystemData.load();
            System.out.println("Flight Booking System loaded successfully.");
        } catch (IOException ex) {
            System.err.println("Error loading system data: " + ex.getMessage());
            return;
        }

        System.out.println("Welcome to the Flight Booking System!");
        System.out.println("Please choose an option:");
        System.out.println("1. Command-Line Interface (CUI)");
        System.out.println("2. Graphical User Interface (GUI)");
        System.out.print("Enter your choice (1 or 2): ");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String choice = reader.readLine().trim();

            if (choice.equals("1")) {
                runCUI(fbs, reader);
            } else if (choice.equals("2")) {
                runGUI(fbs);
            } else {
                System.out.println("Invalid choice. Exiting...");
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private static void runCUI(FlightBookingSystem fbs, BufferedReader reader) {
        String input;

        System.out.println("Type 'help' to see the list of available commands.");
        try {
            while (true) {
                System.out.print("> ");
                input = reader.readLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    Command command = CommandParser.parse(input);
                    command.execute(fbs);
                } catch (Exception ex) {
                    System.err.println("Error: " + ex.getMessage());
                }
            }

            // Save system state
            FlightBookingSystemData.store(fbs);
            System.out.println("System state saved. Goodbye!");

        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private static void runGUI(FlightBookingSystem fbs) {
        SwingUtilities.invokeLater(() -> new MainWindow(fbs));
    }
}
