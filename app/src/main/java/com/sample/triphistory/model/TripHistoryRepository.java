package com.sample.triphistory.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sample.triphistory.managers.AssetsManager;

import java.lang.reflect.Type;

public class TripHistoryRepository {

    private static TripHistoryRepository sInstance;

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

    public TripHistory getTripHistoryList(Context context) {
        TripHistory trips = null;
        String tripsJsonString = new AssetsManager(context.getAssets()).getTripsHistory();
        if (tripsJsonString != null && !tripsJsonString.isEmpty()) {
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<TripHistory>() {
            }.getType();
            trips = gson.fromJson(tripsJsonString, type);
        }
        return trips;
    }


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
