package com.example.socialcompass;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;

public class MockRemoteKey extends RemoteKey {
    private MutableLiveData<RemoteLocation> updateLoc;
    private RemoteLocation nextValue;
    public MockRemoteKey(String publicKey, RemoteLocation nextValue) {
        super(publicKey);
        this.updateLoc = new MutableLiveData<>();
        this.nextValue = nextValue;

    }

    public LiveData<RemoteLocation> initRemoteScheduler() {
        MutableLiveData<RemoteLocation> updatedLoc = new MutableLiveData<>();

        java.util.concurrent.ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            updatedLoc.postValue(this.nextValue);
        }, 0, 3, SECONDS);

        return updatedLoc;
    }

    public void updateLoc(RemoteLocation newLoc) {
        this.nextValue = newLoc;
    }

}
