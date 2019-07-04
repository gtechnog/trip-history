package com.sample.triphistory.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.triphistory.model.Step;
import com.sample.triphistory.model.Trip;
import com.sample.triphistory.model.TripHistoryRepository;
import com.sample.triphistory.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

public class TripDetailViewModel extends AndroidViewModel {

    private Trip currentTrip;

    TripDetailViewModel(@NonNull Application application,
                        @NonNull TripHistoryRepository repository,
                        @NonNull String tripId) {
        super(application);
        currentTrip = repository.getTripDetailById(application, tripId);
    }

    /**
     * to get the start positions marker options
     * @return MarkerOptions
     */
    public MarkerOptions getStartMarkerOptions() {
        return new MarkerOptions().position(getStartLatLng());
    }

    /**
     * to get the end positions marker options
     * @return MarkerOptions
     */
    public MarkerOptions getEndMarkerOptions() {
        return new MarkerOptions().position(getEndLatLng());
    }

    /**
     * To get the start point LatLng
     * @return Latlng
     */
    private LatLng getStartLatLng() {
        LatLng latLng = null;
        if (currentTrip.getPath() != null && currentTrip.getPath().size() > 0) {
            List<Step> path = currentTrip.getPath();
            Step startStep = path.get(0);
            latLng = new LatLng(startStep.getLatitude(), startStep.getLongitude());
        }
        return latLng;
    }

    /**
     * To get the end point LatLng
     * @return Latlng
     */
    private LatLng getEndLatLng() {
        LatLng latLng = null;
        if (currentTrip.getPath() != null && currentTrip.getPath().size() > 0) {
            List<Step> path = currentTrip.getPath();
            Step endStep = path.get(path.size() - 1);
            latLng = new LatLng(endStep.getLatitude(), endStep.getLongitude());
        }
        return latLng;
    }

    /**
     *  To get the trip boundaries including all the path positions also
     * @return LatLngBounds
     */
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

    /**
     * To get the list of all the LatLng along the path
     * @return List of all latlng along the path
     */
    public List<LatLng> getPathLatLngList() {
        ArrayList<LatLng> list = new ArrayList<>();
        for (Step step : currentTrip.getPath()) {
            LatLng latLng = new LatLng(step.getLatitude(), step.getLongitude());
            list.add(latLng);
        }
        return list;
    }

    /**
     * to get the list of steps taken during the trip
     * @return list of steps
     */
    public List<Step> getPath() {
        return currentTrip.getPath();
    }

    /**
     * To get start time of trip in millis
     * @return seconds in millis
     */
    public long getStartTimeInMillies() {
        return DateTimeUtils.getTimeMillies(currentTrip.getStartTime());
    }

    /**
     *  To get the end time of trip in millis
     * @return seconds in millis
     */
    public long getEndTimeInMillies() {
        return DateTimeUtils.getTimeMillies(currentTrip.getEndTime());
    }
}
