package com.sample.triphistory.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  Data holder class, holds trip information
 */
public class Trip {

    @SerializedName("trip_id")
    private Long id;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("simple_path")
    private ArrayList<Step> path;

    public Trip(Long id, String startTime, String endTime, ArrayList<Step> path) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.path = path;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getId() {
        return id.toString();
    }

    public List<Step> getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) &&
                Objects.equals(startTime, trip.startTime) &&
                Objects.equals(endTime, trip.endTime) &&
                Objects.equals(path, trip.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, path);
    }

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", path=" + path +
                '}';
    }
}
