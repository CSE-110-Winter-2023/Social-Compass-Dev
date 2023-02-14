package com.example.socialcompass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



public class OrientationService implements SensorEventListener {
    private static OrientationService instance;
    private final SensorManager sensorManager;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private MutableLiveData<Float> azimuth;
    private Activity activity;

    protected OrientationService(Activity activity){
        this.azimuth = new MutableLiveData<>();
        this.activity = activity;
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.registerSensorListner();
    }

    private void registerSensorListner(){
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    public static OrientationService singleton(Activity activity){
        if(instance == null){
            instance = new OrientationService(activity);
        }
        return instance;
    }

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

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

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

    public void unregisterSensorListeners(){
        sensorManager.unregisterListener(this);
    }

    public LiveData<Float> getOrientation(){return this.azimuth;}

    public void setMockOrientationSource (MutableLiveData<Float> mockDataSource){
        unregisterSensorListeners();

        /* Unregister the location manager, and send any activities on this observe
         *  to the original MutableLiveData object.
         */
        mockDataSource.observe((LifecycleOwner) activity, loc -> this.azimuth.setValue(loc));
    }

}
