package com.sample.triphistory.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sample.triphistory.model.Step;
import com.sample.triphistory.model.Trip;
import com.sample.triphistory.model.TripHistoryRepository;
import com.sample.triphistory.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TripDetailViewModel extends AndroidViewModel {

    private final Context context;
    private final TripHistoryRepository repository;
    private final String tripId;
    private Trip currentTrip;

    TripDetailViewModel(@NonNull Application application,
                        @NonNull TripHistoryRepository repository,
                        @NonNull String tripId) {
        super(application);
        this.context = application;
        this.repository = repository;
        this.tripId = tripId;
        currentTrip = repository.getTripDetailById(context, tripId);
    }

    public MarkerOptions getStartMarkerOptions() {
        return new MarkerOptions().position(getStartLatLng());
    }

    private LatLng getStartLatLng() {
        LatLng latLng = null;
        if (currentTrip.getPath() != null && currentTrip.getPath().size() > 0) {
            List<Step> path = currentTrip.getPath();
            Step startStep = path.get(0);
            latLng = new LatLng(startStep.getLatitude(), startStep.getLongitude());
        }
        return latLng;
    }

    public MarkerOptions getEndMarkerOptions() {
        return new MarkerOptions().position(getEndLatLng());
    }

    private LatLng getEndLatLng() {
        LatLng latLng = null;
        if (currentTrip.getPath() != null && currentTrip.getPath().size() > 0) {
            List<Step> path = currentTrip.getPath();
            Step endStep = path.get(path.size() - 1);
            latLng = new LatLng(endStep.getLatitude(), endStep.getLongitude());
        }
        return latLng;
    }

    public LatLngBounds getTripBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(getStartLatLng());
        builder.include(getEndLatLng());
        List<LatLng> list = getPathLatLngList();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }
        return builder.build();
    }

    public List<LatLng> getPathLatLngList() {
        ArrayList<LatLng> list = new ArrayList<>();
        for (Step step : currentTrip.getPath()) {
            LatLng latLng = new LatLng(step.getLatitude(), step.getLongitude());
            list.add(latLng);
        }
        return list;
    }

    public List<Step> getPath() {
        return currentTrip.getPath();
    }

    public long getStartTimeInMillies() {
        return DateTimeUtils.getTimeMillies(currentTrip.getStartTime());
    }

    public long getEndTimeInMillies() {
        return DateTimeUtils.getTimeMillies(currentTrip.getEndTime());
    }
}
