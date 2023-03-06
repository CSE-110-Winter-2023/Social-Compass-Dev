package com.example.socialcompass;

import static com.example.socialcompass.RemoteLocation.fromJSON;

import android.util.Log;

import androidx.annotation.AnyThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LocationAPI {

    private volatile static LocationAPI instance = null;

    private OkHttpClient client;

    public LocationAPI() {
        this.client = new OkHttpClient();
    }

    public static LocationAPI provide() {
        if (instance == null) {
            instance = new LocationAPI();
        }
        return instance;
    }

    @AnyThread
    public RemoteLocation getFromRemoteAPIAsync(String publicCode) {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getFromRemoteAPI(publicCode));
        try {
            return future.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            Log.i("[REMOTE ASYNC]", "Returning null remote location");
            return null;
        }
    }

    public RemoteLocation getFromRemoteAPI(String publicCode){

        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + publicCode)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            RemoteLocation body = fromJSON(response.body().string());

            Log.i("Acer", "label of fetched loc: " + body.label);

            return body;
        } catch (Exception e) {
            Log.i("Acer", "Nope");

            e.printStackTrace();
            return null;
        }
    }

    //put own location continuously to server for others to track

    //list of friends (UUID) that we need to track
    //fetch them from

}
