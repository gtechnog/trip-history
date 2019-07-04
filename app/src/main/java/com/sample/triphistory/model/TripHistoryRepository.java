package com.sample.triphistory.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sample.triphistory.managers.AssetsManager;

import java.lang.reflect.Type;

/**
 *  Repository for Trip history
 */
public class TripHistoryRepository {

    private static TripHistoryRepository sInstance;

    private TripHistoryRepository() {
    }

    public static TripHistoryRepository getInstance() {
        if (sInstance == null) {
            synchronized (TripHistoryRepository.class) {
                if (sInstance == null) {
                    sInstance = new TripHistoryRepository();
                }
            }
        }
        return sInstance;
    }

    /**
     * TODO: This method implementation parse trip_history.json file and convert to TripHistory object
     * TODO: after first method call, put (tripid -> value) to shared preference
     * TODO Improvement: Move this code to AsyncTask (get rid of UI Thread, file might be very big)
     * To get the trips history
     * @param context application context
     * @return trip history
     */
    public TripHistory getTripHistoryList(Context context) {
        TripHistory trips = null;
        String tripsJsonString = new AssetsManager(context.getAssets()).readTripHistoryFile();
        if (tripsJsonString != null && !tripsJsonString.isEmpty()) {
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<TripHistory>() {
            }.getType();
            trips = gson.fromJson(tripsJsonString, type);
        }
        return trips;
    }

    /**
     *  TODO: Currently This method implementation parse trip_history.json file and iterate over the list of trips
     *  TODO: Improvement: After one time of parsing from getTripHistoryList() method, put (tripid -> value) to shared preference
     *  TODO: Improvement: next call of this method should fetch from shared preference
     * @param context application context
     * @param tripId trip id of the trip
     * @return Trip
     */
    @Nullable
    public Trip getTripDetailById(@NonNull Context context, @Nullable String tripId) {
        if (tripId == null || tripId.isEmpty()) return null;

        Trip trip = null;
        TripHistory history = getTripHistoryList(context);
        if (history != null && history.getTrips() != null && history.getTrips().size() > 0) {
            for (int i = 0; i< history.trips.size() ; i++) {
                 trip = history.trips.get(i);
                if (trip.getId().equals(tripId)) {
                    return trip;
                }
            }
        }
        return  trip;
    }
}
