/**
 * LocationService class is responsible for fetching the latest device location and providing
 * it to the client code. It uses the LocationManager and LocationListener APIs to register
 * for location updates and listens for location changes.
 */

package com.example.socialcompass;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LocationService implements LocationListener {
    private static LocationService instance;
    private Activity activity;

    private MutableLiveData<Pair<Double,Double>> locationValue;

    private final LocationManager locationManager;

    /**
     * Returns the singleton instance of the LocationService class. If the instance is not already
     * initialized, it creates one and returns it. The singleton pattern is used to ensure that
     * there is only one instance of the LocationService class throughout the application.
     *
     * @param activity The activity that will use the LocationService instance
     * @return LocationService singleton instance
     */
    public static LocationService singleton(Activity activity) {
        if (instance == null){
            instance = new LocationService(activity);
        }
        return instance;
    }

    /**
     * Constructor for the LocationService class. It initializes the instance variables and registers
     * for location updates using the LocationManager API.
     *
     * @param activity The activity that will use the LocationService instance
     */
    protected LocationService(Activity activity){
        this.locationValue = new MutableLiveData<>();
        this.activity = activity;
        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        this.registerLocationListener();
    }

    /**
     * Registers for location updates using the LocationManager API. If the app does not have
     * location permissions, it throws an IllegalStateException.
     */
    private void registerLocationListener(){
        if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            throw new IllegalStateException("App needs location permissions to get latest location");
        }

        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    /**
     * Called when the device location changes. It updates the latest location value in the
     * MutableLiveData object so that the client code can observe it.
     *
     * @param location The new location of the device
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.locationValue.postValue(new Pair<>(location.getLatitude(), location.getLongitude()));
    }

    /**
     * Unregisters the LocationListener to stop receiving location updates.
     */
    public void unregisterLocationListener() {
        locationManager.removeUpdates(this);
    }

    /**
     * Returns the latest location value as a LiveData object. The client code can observe this
     * value and receive updates whenever the location changes.
     *
     * @return The latest location value as a LiveData object
     */
    public LiveData<Pair<Double, Double>> getLocation() {
        return this.locationValue;
    }


    /**
     * Sets the location source to a mock data source for testing purposes. It unregisters the
     * LocationListener and updates the MutableLiveData object with the mock data source.
     *
     * @param mockDataSource The mock data source for testing
     */
    public void setMockLocationSource(MutableLiveData<Pair<Double, Double>> mockDataSource){
        unregisterLocationListener();

        /* Unregister the location manager, and send any activities on this observe
        *  to the original MutableLiveData object.
        */
        mockDataSource.observeForever(loc -> this.locationValue.setValue(loc));
    }


}
