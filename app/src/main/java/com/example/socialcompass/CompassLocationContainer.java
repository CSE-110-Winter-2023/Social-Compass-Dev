package com.example.socialcompass;

import android.location.Location;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompassLocationContainer implements Iterable<CompassLocationObject> {
    private List<CompassLocationObject> locationList;

    public CompassLocationContainer() {
        locationList = new ArrayList<>();
    }

    public void addLocation(CompassLocationObject location) {
        locationList.add(location);
    }

    public int getLocationCount() {
        return locationList.size();
    }

    public CompassLocationObject getLocationAt(int index) {
        return locationList.get(index);
    }

    public void removeLocationAt(int index) {
        locationList.remove(index);
    }

    public void clearLocations() {
        locationList.clear();
    }

    public List<CompassLocationObject> getAllLocations() {
        return locationList;
    }

    public void createAndAddLocation(String locationName, double latitude, double longitude, CompassUIController controller) {
        Location location = new Location(locationName);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        CompassLocationObject compassLocation = new CompassLocationObject(locationName, location, controller);
        locationList.add(compassLocation);
    }

    @Override
    public Iterator<CompassLocationObject> iterator() {
        return locationList.iterator();
    }
}
