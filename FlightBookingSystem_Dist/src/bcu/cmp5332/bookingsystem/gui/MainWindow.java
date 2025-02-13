package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu flightsMenu;
    private JMenu customersMenu;
    private JMenu bookingsMenu;
    private JMenuItem flightsView;
    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsCancel;
    private JPanel topPanel;
    private JPanel chartPanel;
    private FlightBookingSystem fbs;

    public MainWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    private void initialize() {
        setTitle("Flight Booking Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Bar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Flights menu
        flightsMenu = new JMenu("Flights");
        flightsView = new JMenuItem("View");
        flightsMenu.add(flightsView);
        flightsView.addActionListener(this);
        menuBar.add(flightsMenu);

        // Customers menu
        customersMenu = new JMenu("Customers");
        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        customersMenu.add(custView);
        customersMenu.add(custAdd);
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        menuBar.add(customersMenu);

        // Bookings menu
        bookingsMenu = new JMenu("Bookings");
        bookingsIssue = new JMenuItem("Add Booking");
        bookingsCancel = new JMenuItem("Cancel Booking");
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsCancel);
        bookingsIssue.addActionListener(this);
        bookingsCancel.addActionListener(this);
        menuBar.add(bookingsMenu);

        // Top Panel for Summary Cards
        topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Charts
        chartPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(chartPanel, BorderLayout.CENTER);

        // Initial Refresh
        refreshDashboard();

        setVisible(true);
    }

    private void refreshDashboard() {
        // Clear existing components
        topPanel.removeAll();
        chartPanel.removeAll();

        // Add refreshed cards
        topPanel.add(createSummaryCard("Total Customers", String.valueOf(fbs.getCustomers().size())));
        topPanel.add(createSummaryCard("Total Flights", String.valueOf(fbs.getFlights().size())));
        topPanel.add(createSummaryCard("Total Earnings", "$" + calculateTotalEarnings()));

        // Add refreshed charts
        chartPanel.add(createChartCard("Flight Share (Pie Chart)", createFlightSharePieChart()));
        chartPanel.add(createChartCard("Bookings Per Flight (Bar Chart)", createBookingsBarChart()));
        chartPanel.add(createChartCard("Earnings Trend (Line Chart)", createEarningsLineChart()));

        // Revalidate and repaint to reflect updates
        topPanel.revalidate();
        topPanel.repaint();
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private JPanel createSummaryCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(240, 248, 255)); // Light blue
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createChartCard(String title, JFreeChart chart) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(chartPanel, BorderLayout.CENTER);
        return card;
    }

    private JFreeChart createFlightSharePieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        List<Flight> flights = fbs.getFlights();
        for (Flight flight : flights) {
            dataset.setValue(flight.getFlightNumber(), flight.getPassengers().size());
        }

        return ChartFactory.createPieChart(
            "Flight Share by Bookings", dataset, true, true, false);
    }

    private JFreeChart createBookingsBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Flight> flights = fbs.getFlights();
        for (Flight flight : flights) {
            dataset.addValue(flight.getPassengers().size(), "Bookings", flight.getFlightNumber());
        }

        return ChartFactory.createBarChart(
            "Bookings Per Flight", "Flight Number", "Bookings", dataset);
    }

    private JFreeChart createEarningsLineChart() {
        XYSeries series = new XYSeries("Earnings");

        List<Flight> flights = fbs.getFlights();
        int month = 1;
        for (Flight flight : flights) {
            series.add(month++, flight.getPrice() * flight.getPassengers().size());
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return ChartFactory.createXYLineChart(
            "Earnings Trend", "Month", "Earnings ($)", dataset);
    }

    private double calculateTotalEarnings() {
        return fbs.getFlights().stream()
            .mapToDouble(flight -> flight.getPrice() * flight.getPassengers().size())
            .sum();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == flightsView) {
            new FlightListWindow(this);
        } else if (e.getSource() == custView) {
            new CustomerListWindow(fbs);
        } else if (e.getSource() == custAdd) {
            new AddCustomerWindow(this);
            refreshDashboard(); // Refresh after adding a customer
        } else if (e.getSource() == bookingsIssue) {
            new AddBookingWindow(this);
            refreshDashboard(); // Refresh after adding a booking
        } else if (e.getSource() == bookingsCancel) {
            new CancelBookingWindow(this);
            refreshDashboard(); // Refresh after canceling a booking
        }
    }

    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight No", "Origin", "Destination", "Departure Date", "Capacity", "Price"};

        Object[][] data = new Object[flightsList.size()][columns.length];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
            data[i][4] = flight.getCapacity();
            data[i][5] = flight.getPrice();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }
}
