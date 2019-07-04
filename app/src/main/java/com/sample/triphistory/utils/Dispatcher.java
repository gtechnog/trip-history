package com.sample.triphistory.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.sample.triphistory.constant.BundleKeys;
import com.sample.triphistory.ui.TripDetailActivity;

/**
 *  Dispatcher is responsible to dispatch the activities/fragments, single entry points for navigation
 *  in application.
 */
public class Dispatcher {

    public static void showTripDetailActivity(@NonNull Context context, @NonNull String tripId) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeys.BUNDLE_KEY_TRIP_ID, tripId);
        Intent intent = new Intent(context, TripDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
