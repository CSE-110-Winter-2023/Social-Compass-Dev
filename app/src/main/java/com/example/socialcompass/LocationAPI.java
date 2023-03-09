package com.example.socialcompass;

import static com.example.socialcompass.RemoteLocation.fromJSON;

import android.location.Location;
import android.util.Log;

import androidx.annotation.AnyThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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


    public void upsertToRemoteAPIAsync(String userPrivateKey, String userPublicKey, Location location, String displayName) {
        var executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> upsertToRemoteAPI(userPrivateKey, userPublicKey, location, displayName));
    }

    public void upsertToRemoteAPI(String userPrivateKey, String userPublicKey, Location location, String displayName) {
        Log.i("Debuug", "Passed DisplayName " + displayName);

        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        /*
        {
          "private_code": "123-456-7890",
          "label": "foo",
          "latitude": 40,
          "longitude": 40
        }
         */
        JSONObject noteJSON = new JSONObject();
        try {
            noteJSON.put("private_code", userPrivateKey);
            noteJSON.put("label", displayName);
            noteJSON.put("latitude", location.getLatitude());
            noteJSON.put("longitude", location.getLongitude());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(String.valueOf(noteJSON), JSON);
        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + userPublicKey)
                .method("PUT", body)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            Log.i("Debuug", "MY LOCATION " + response.body().string());



        } catch (Exception e) {
            Log.i("Debuug", "PROBLEM");

            e.printStackTrace();

        }
    }
}
