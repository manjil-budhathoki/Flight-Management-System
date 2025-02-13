package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerWindow extends JFrame implements ActionListener {
    private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JTextField emailText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("Add a New Customer");
        setSize(350, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        topPanel.add(new JLabel("Name: "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Phone: "));
        topPanel.add(phoneText);
        topPanel.add(new JLabel("Email: "));
        topPanel.add(emailText);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            addCustomer();
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addCustomer() {
        try {
            String name = nameText.getText();
            String phone = phoneText.getText();
            String email = emailText.getText();

            // Create and execute the AddCustomer command
            Command addCustomer = new AddCustomer(name, phone, email);
            addCustomer.execute(mw.getFlightBookingSystem());

            // Save data with rollback support
            mw.getFlightBookingSystem().storeDataWithRollback();

            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
