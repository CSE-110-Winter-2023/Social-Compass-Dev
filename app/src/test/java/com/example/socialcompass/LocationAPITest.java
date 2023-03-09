package com.example.socialcompass;

import static com.example.socialcompass.RemoteLocation.fromJSON;
import static org.junit.Assert.assertEquals;

import android.location.Location;

import org.junit.Test;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class LocationAPITest {

    @Test
    public void testGetFromRemoteAPI()  {
        LocationAPI locationAPI = LocationAPI.provide();
        RemoteLocation remoteLocation = locationAPI.getFromRemoteAPI("team4TestPublicKey");
        assertEquals(remoteLocation.longitude, -123.393333, 0);

        assertEquals(remoteLocation.latitude, -35.876667, 0);

        assertEquals(remoteLocation.label, "team4TestPublicKey");
    }

    @Test
    public void testGetFromRemoteAPI2()  {
        LocationAPI locationAPI = LocationAPI.provide();
        RemoteLocation remoteLocation = locationAPI.getFromRemoteAPI("team4TestPublicKey1");
        assertEquals(remoteLocation.longitude, -32, 0);

        assertEquals(remoteLocation.latitude, -23, 0);

        assertEquals(remoteLocation.label, "team4TestPublicKey1");
    }


    @Test
    public void testGetFromRemoteAPIMocked() throws Exception {
        MockWebServer server = new MockWebServer();
        server.start();
        double bodyOfrequest = mockGetFromRemoteAPI(new OkHttpClient(), "team4TestPublicKey");
        assertEquals(-35.876667, bodyOfrequest, 0);
        server.shutdown();
    }

    public double mockGetFromRemoteAPI(OkHttpClient okHttpClient, String publicCode){
        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + publicCode)
                .method("GET", null)
                .build();
        try (var response = okHttpClient.newCall(request).execute()) {
            assert response.body() != null;
            RemoteLocation body = fromJSON(response.body().string());
            return body.latitude;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
