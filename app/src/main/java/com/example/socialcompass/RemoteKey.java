package com.example.socialcompass;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;

public class RemoteKey {
    private final String publicKey;

    public RemoteKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Initializes location updates on current Location Object.
     * Returns MutableLiveData that has updated locations.
     **/
    public LiveData<RemoteLocation> initRemoteScheduler() {
        MutableLiveData<RemoteLocation> updatedLoc = new MutableLiveData<>();

        java.util.concurrent.ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            updatedLoc.postValue(LocationAPI.provide().getFromRemoteAPIAsync(this.publicKey));
        }, 0, 3, SECONDS);

        return updatedLoc;
    }
}

