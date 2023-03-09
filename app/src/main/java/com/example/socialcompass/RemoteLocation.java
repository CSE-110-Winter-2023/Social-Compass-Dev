package com.example.socialcompass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "location")
public class RemoteLocation {
    /** The title of the note. Used as the primary key for shared notes (even on the cloud). */
    @PrimaryKey
    @SerializedName("label")
    @NonNull
    public String label;

    /** The content of the note. */
    @SerializedName("longitude")
    @NonNull
    public double longitude;

    /** The content of the note. */
    @SerializedName("latitude")
    @NonNull
    public double latitude;


    /** General constructor for a location. */
    public RemoteLocation(@NonNull String label, @NonNull double longitude, @NonNull double latitude) {
        this.label = label;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public static RemoteLocation fromJSON(String json) {
        return new Gson().fromJson(json, RemoteLocation.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}