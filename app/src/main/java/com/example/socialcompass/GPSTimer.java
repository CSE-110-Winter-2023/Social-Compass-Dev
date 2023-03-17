package com.example.socialcompass;

import android.graphics.PorterDuff;
import android.util.Log;

public class GPSTimer {


    static String calculateGPSdelay(long currentTime, long mLastGpsTime){
        long msMinute = 60000;
        long msHour = 3600000;

        long timeSinceLastGps = currentTime - mLastGpsTime;
        if (timeSinceLastGps < 1000){
            return "";
        }
        else if (timeSinceLastGps / msHour >= 1)
        {
            return timeSinceLastGps/msHour + " hr";
        }
        else if (timeSinceLastGps / msHour < 1)
        {
            return timeSinceLastGps/msMinute + " m";
        }
        else {
            return "";
        }

    }

    static boolean color(long currentTime, long mLastGpsTime){
        if(currentTime-mLastGpsTime > 60000){
            Log.i("GPS", "Signal Lost");
            return false;
        }
        return true;
    }

    static boolean hasSignal(long currentTime, long mLastGpsTime){
        if(currentTime-mLastGpsTime > 1000){
            Log.i("GPS", "Signal Lost");
            return false;
        }
        return true;
    }

}
