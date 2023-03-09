package com.example.socialcompass;

import static java.util.concurrent.TimeUnit.SECONDS;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.Executors;

public class LocationManager {


    public LiveData<RemoteLocation> getRemote(String publicCode) {

        MutableLiveData<RemoteLocation> updatedLoc = new MutableLiveData<>();

        var executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            updatedLoc.postValue(LocationAPI.provide().getFromRemoteAPIAsync(publicCode));
        }, 0, 3, SECONDS);

        return updatedLoc;

    }


}
