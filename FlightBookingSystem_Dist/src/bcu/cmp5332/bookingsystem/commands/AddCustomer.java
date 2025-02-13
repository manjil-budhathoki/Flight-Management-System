package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddCustomer implements Command {
    private final String name;
    private final String phone;
    private final String email;

    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(FlightBookingSystem fbs) {
        int newId = fbs.getCustomers().size() + 1; // Generate a new ID
        Customer customer = new Customer(newId, name, phone, email);
        fbs.addCustomer(customer);
        System.out.println("Customer added: " + customer.getName());
    }
}
