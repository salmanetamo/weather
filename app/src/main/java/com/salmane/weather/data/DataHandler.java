package com.salmane.weather.data;

import com.salmane.weather.models.Location;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to initialize the weather information retrieved from the API
 * and stores all locations and their weather info inside a list.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class DataHandler {
    private static String[] locationIds = {"2648579", "2643743", "5128581", "287286",
            "934154", "1185241", "2440485"};

    public static ArrayList<Location> getLocations() throws ExecutionException, InterruptedException {
        ArrayList<Location> locations = new ArrayList<>();

        for(String locationId : locationIds){
            XMLParser xmlParser = new XMLParser(locationId);
            locations.add((Location) xmlParser.execute().get());
        }

        for(int i = 0; i < locations.size() - 1; i++){
            locations.get(i).setNextLocation(locations.get(i + 1));
        }

        return locations;
    }
}
