package com.sample.triphistory.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.triphistory.model.Step;
import com.sample.triphistory.model.Trip;
import com.sample.triphistory.utils.Repository;

import java.util.LinkedList;

public class TripDetailViewModel extends AndroidViewModel {

    private Context context;
    private Trip currentTrip;
    private String tripId;

    public TripDetailViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    /*
     * this must be called as this view model is dependent on this trip id.
     */
    public void setTripId(@NonNull String tripId) {
        this.tripId = tripId;
    }

    public Trip getTripDetails() {
        currentTrip =  Repository.getTripHistoryRepository().getTripDetailById(context, tripId);
        return currentTrip;
    }

    public MarkerOptions getStartMarkerOptions() {
        return new MarkerOptions().position(getStartLatLng());
    }

    public LatLng getStartLatLng() {
        if (currentTrip == null) getTripDetails();
        LatLng latLng = null;
        if (currentTrip.getPath() != null && currentTrip.getPath().size() > 0) {
            LinkedList<Step> path = currentTrip.getPath();
            Step startStep = path.get(0);
            latLng = new LatLng(startStep.getLatitude(), startStep.getLongitude());
        }
        return latLng;
    }

    public MarkerOptions getEndMarkerOptions() {
        return new MarkerOptions().position(getEndLatLng());
    }

    public LatLng getEndLatLng() {
        if (currentTrip == null) getTripDetails();
        LatLng latLng = null;
        if (currentTrip.getPath() != null && currentTrip.getPath().size() > 0) {
            LinkedList<Step> path = currentTrip.getPath();
            Step endStep = path.get(path.size() - 1);
            latLng = new LatLng(endStep.getLatitude(), endStep.getLongitude());
        }
        return latLng;
    }

    public LatLngBounds getTripBoundary() {
        return  new LatLngBounds(
                new LatLng(-44, 113), new LatLng(-10, 154));
    }
}
