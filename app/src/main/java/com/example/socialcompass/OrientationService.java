/**
 * This class provides orientation data by registering for sensor updates from the
 * device's accelerometer and magnetometer. It can also be used to set a mock orientation
 * data source for testing purposes. The class implements SensorEventListener interface
 * to listen for sensor updates.
 */
package com.example.socialcompass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class OrientationService implements SensorEventListener {

    private static OrientationService instance;
    private final SensorManager sensorManager;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private MutableLiveData<Float> azimuth;
    private Activity activity;
    private Observer<Float> currentObserver;

    /**
     * Constructor for OrientationService that takes an Activity as a parameter
     * and initializes the SensorManager and the MutableLiveData for orientation data.
     * It also registers the listener for accelerometer and magnetometer sensors.
     *
     * @param activity - the activity that will be used to get the system sensor service
     */
    protected OrientationService(Activity activity){
        this.azimuth = new MutableLiveData<>();
        this.activity = activity;
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.registerSensorListener();
    }

    /**
     * This method registers the listener for accelerometer and magnetometer sensors
     * with the SensorManager.
     */
    public void registerSensorListener(){
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * This method returns the singleton instance of OrientationService.
     * If an instance has not been created, it will create one.
     *
     * @param activity - the activity that will be used to create the instance if needed
     * @return OrientationService instance
     */
    public static OrientationService singleton(Activity activity){
        if(instance == null){
            instance = new OrientationService(activity);
        }
        return instance;
    }

    /**
     * This method is called when sensor data has changed.
     * It checks which sensor has changed and updates the corresponding data array.
     * If both accelerometer and magnetometer data is available, it calls the
     * onBothSensorDataAvailable() method.
     *
     * @param event - the sensor event that occurred
     */
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerReading = event.values;
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magnetometerReading = event.values;
        }
        if(accelerometerReading != null && magnetometerReading != null){
            onBothSensorDataAvailable();
        }
    }

    /**
     * This method is called when the accuracy of a sensor has changed.
     *
     * @param sensor - the sensor whose accuracy has changed
     * @param i - the new accuracy of the sensor
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * This method is called when both accelerometer and magnetometer data are available.
     * It computes the orientation using the SensorManager's getRotationMatrix() and
     * getOrientation() methods, and posts value to the azimuth.
     */
     private void onBothSensorDataAvailable(){
        if(accelerometerReading == null || magnetometerReading == null){
            throw new IllegalStateException("Both sensors must be available to compute orientation");
        }
        //left those as floats
        float[] r = new float[9];
        float[] i = new float[9];

        boolean success = SensorManager.getRotationMatrix(r, i, accelerometerReading, magnetometerReading);

        if(success){
            float[] orientation = new float[3];
            SensorManager.getOrientation(r, orientation);
            this.azimuth.postValue(orientation[0]);
        }
    }

    /**
     * Method that unregisters this class from listening to sensor events.
     * Used when trying to mock data or pause sensor input.
     */
    public void unregisterSensorListeners(){
        sensorManager.unregisterListener(this);
    }

    /**
     * This object is a getter that returns the azimuth object.
     * Observers can register to listen to changes to the azimuth angle.
     * @return LiveData<Float> azimuth
     */
    public LiveData<Float> getOrientation(){
        return this.azimuth;
    }

    /**
     * This method mocks the orientation source for this object.
     * It unregisters the sensor listeners and registers an observer
     * to listen for changes to the mockDataSource.
     * When a change is detected, it sets the new value to the azimuth object.
     *
     * @param mockDataSource - the new data source to use for orientation
     */
    public void setMockOrientationSource (MutableLiveData<Float> mockDataSource){
        unregisterSensorListeners();

        mockDataSource.observeForever((loc) -> {
            this.azimuth.setValue(loc);
        });
    }

}
