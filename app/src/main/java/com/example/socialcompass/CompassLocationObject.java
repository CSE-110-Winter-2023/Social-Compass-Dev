package com.example.socialcompass;

import android.location.Location;

public class CompassLocationObject {
    private String locationName;
    private Location location;
    private CompassUIController controller;

    public CompassLocationObject(String locationName, Location location, CompassUIController controller) {
        this.locationName = locationName;
        this.location = location;
        this.controller = controller;

        if (this.controller != null && this.controller.getTextView() != null){
            controller.getTextView().setText(this.locationName);
        }
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CompassUIController getController() {
        return controller;
    }

    public void setController(CompassUIController controller) {
        this.controller = controller;
    }

    public void syncName() {
        this.locationName = controller.getTextView().getText().toString();
    }

}
