// FlightBookingSystemData
package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FlightBookingSystemData {

    private static final List<DataManager> DATA_MANAGERS = Arrays.asList(
            new FlightDataManager(),
            new CustomerDataManager(),
            new BookingDataManager()
    );

    public static FlightBookingSystem load() throws IOException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager manager : DATA_MANAGERS) {
            manager.loadData(fbs);
        }
        return fbs;
    }

    public static void store(FlightBookingSystem fbs) throws IOException {
        for (DataManager manager : DATA_MANAGERS) {
            manager.storeData(fbs);
        }
    }
}
