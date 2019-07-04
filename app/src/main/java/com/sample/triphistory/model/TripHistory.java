package com.sample.triphistory.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

/**
 *  Data holder class, holds the history of user's trip
 */
public class TripHistory {

    @SerializedName("trips")
    ArrayList<Trip> trips;

    public TripHistory(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripHistory that = (TripHistory) o;
        return Objects.equals(trips, that.trips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trips);
    }

    @Override
    public String toString() {
        return "TripHistory{" +
                "trips=" + trips +
                '}';
    }
}
