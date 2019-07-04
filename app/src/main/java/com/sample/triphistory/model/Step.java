package com.sample.triphistory.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Data holder class which holds the information of location at a specific time.
 */
public class Step {

    public Step(Double latitude, String timestamp, Double longitude, Double time_millis) {
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.time_millis = time_millis;
    }

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("time_millis")
    private Double time_millis;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public long getTime_millis() {
        return Math.round(time_millis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equals(latitude, step.latitude) &&
                Objects.equals(timestamp, step.timestamp) &&
                Objects.equals(longitude, step.longitude) &&
                Objects.equals(time_millis, step.time_millis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, timestamp, longitude, time_millis);
    }

    @NonNull
    @Override
    public String toString() {
        return "Step{" +
                "latitude=" + latitude +
                ", timestamp='" + timestamp + '\'' +
                ", longitude=" + longitude +
                ", time_millis=" + time_millis +
                '}';
    }
}
