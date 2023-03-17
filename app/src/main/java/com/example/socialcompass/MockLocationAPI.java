package com.example.socialcompass;

import android.location.Location;

public class MockLocationAPI extends LocationAPI {

    private RemoteLocation remoteLocation;
    private boolean isUpsertSuccessful;
    private volatile static MockLocationAPI instance = null;


    public static MockLocationAPI provide() {
        if (instance == null) {
            instance = new MockLocationAPI();
        }
        return instance;
    }

    public void setRemoteLocation(RemoteLocation remoteLocation) {
        this.remoteLocation = remoteLocation;
    }

    public void setUpsertSuccessful(boolean isUpsertSuccessful) {
        this.isUpsertSuccessful = isUpsertSuccessful;
    }

    public boolean isUpsertSuccessful() {
        return this.isUpsertSuccessful;
    }

    @Override
    public RemoteLocation getFromRemoteAPI(String publicCode) {
        return remoteLocation;
    }

    @Override
    public void upsertToRemoteAPI(String userPrivateKey, String userPublicKey, Location location, String displayName) {
        isUpsertSuccessful = true;
    }
}
