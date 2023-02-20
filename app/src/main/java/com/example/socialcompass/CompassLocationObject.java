/**
 * Represents a location object with a name, location, and UI controller for a compass app.
 */
package com.example.socialcompass;

import android.location.Location;

public class CompassLocationObject {
    private String locationName;
    private Location location;
    private CompassUIController controller;

    /**
     * Constructor for creating a CompassLocationObject
     * @param locationName name of the location
     * @param location location object containing latitude and longitude
     * @param controller controller object to handle updating UI for this location
     */
    public CompassLocationObject(String locationName, Location location, CompassUIController controller) {
        this.locationName = locationName;
        this.location = location;
        this.controller = controller;

        if (this.controller != null && this.controller.getTextView() != null){
            controller.getTextView().setText(this.locationName);
        }
    }

    /**
     * Returns the name of the location object.
     * @return the name of the location object
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the name of the location object.
     * @param locationName the new name for the location object
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Returns the location object.
     * @return the location object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location object.
     * @param location the new location for the location object
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the UI controller for the compass.
     * @return the UI controller for the compass
     */
    public CompassUIController getController() {
        return controller;
    }

    /**
     * Sets the UI controller for the compass.
     * @param controller the new UI controller for the compass
     */
    public void setController(CompassUIController controller) {
        this.controller = controller;
    }

    /**
     * Updates the name of the location object with the current text in the UI controller's TextView.
     */
    public void syncName() {
        this.locationName = controller.getTextView().getText().toString();
    }

}
