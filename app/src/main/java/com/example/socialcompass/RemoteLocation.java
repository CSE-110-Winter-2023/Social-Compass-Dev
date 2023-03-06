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
    public float longitude;

    /** The content of the note. */
    @SerializedName("latitude")
    @NonNull
    public float latitude;

    /**
     * When the note was last modified. Used for resolving local (db) vs remote (api) conflicts.
     * Defaults to 0 (Jan 1, 1970), so that if a note already exists remotely, its content is
     * always preferred to a new empty note.
     */
    @SerializedName(value = "version")
    public long version = 0;

    /** General constructor for a location. */
    public RemoteLocation(@NonNull String label, @NonNull float longitude, @NonNull float latitude) {
        this.label = label;
        this.longitude = longitude;
        this.latitude = latitude;
        this.version = 0;
    }

    @Ignore
    public RemoteLocation(@NonNull String label, @NonNull float longitude, @NonNull float latitude, long version) {
        this.label = label;
        this.longitude = longitude;
        this.latitude = latitude;
        this.version = version;
    }

    public static RemoteLocation fromJSON(String json) {
        return new Gson().fromJson(json, RemoteLocation.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}