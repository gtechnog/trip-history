package com.sample.triphistory.ui;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sample.triphistory.R;
import com.sample.triphistory.constant.BundleKeys;
import com.sample.triphistory.model.Step;
import com.sample.triphistory.utils.Injector;
import com.sample.triphistory.viewmodel.TripDetailViewModel;

import java.util.List;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = TripDetailActivity.class.getSimpleName();
    private GoogleMap mMap;
    private TripDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        String tripId = "";
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (getIntent() != null && getIntent().getExtras() != null) {
            tripId = getIntent().getExtras().getString(BundleKeys.BUNDLE_KEY_TRIP_ID);
            if (tripId == null || tripId.isEmpty() || mapFragment == null) {
                finish();
            }
        } else {
            finish();
        }

        mapFragment.getMapAsync(this);
        viewModel = ViewModelProviders.of(this,
                Injector.provideTripDetailViewModelFactory((Application) getApplicationContext(), tripId))
                .get(TripDetailViewModel.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(viewModel.getStartMarkerOptions()).setTitle(" Start Point");
        mMap.addMarker(viewModel.getEndMarkerOptions()).setTitle(" End Point");

        final Marker marker = mMap.addMarker(viewModel.getStartMarkerOptions());
                marker.setIcon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_media_pause));


        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(viewModel.getTripBounds(), 128);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(cameraUpdate);
                addPolyLineToPath();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animateMarker(mMap, marker, viewModel.getPath(),viewModel.getStartTimeInMillies() , viewModel.getEndTimeInMillies());
                    }
                }, 500);
            }
        });
    }

    public PolylineOptions addPolyLineToPath() {
        List<LatLng> listOfPath = viewModel.getPathLatLngList();
        PolylineOptions options = null;
        for (int i = 0; i < listOfPath.size() - 1; i++) {
            LatLng src = listOfPath.get(i);
            LatLng dest = listOfPath.get(i + 1);
            options = new PolylineOptions();
            options.add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude));
            options.width(5).color(Color.LTGRAY).geodesic(true);
            mMap.addPolyline(options);
        }
        return options;
    }

    private static void animateMarker(GoogleMap myMap, final Marker marker, final List<Step> directionPoint, final long start, final long end) {
        final Handler handler = new Handler();
        final long duration = end -start;
        final Interpolator interpolator = new LinearInterpolator();
        final long animateDuration = 20000;

        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                long elapsed = directionPoint.get(i).getTime_millis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                if (i < directionPoint.size())
                    marker.setPosition(new LatLng(directionPoint.get(i).getLatitude(), directionPoint.get(i).getLongitude()));
                i++;

                if (t < 1.0) {
                    // Post again 16ms later.
                    long delay = 16;
                    if (i < directionPoint.size()) {
                        delay =( ( directionPoint.get(i).getTime_millis() - directionPoint.get(i-1).getTime_millis() ) ) * animateDuration / duration;
                        Log.d(TAG, "run: delay: " + delay);
                    }
                    handler.postDelayed(this, delay);
                } else {

                }
            }
        });
    }
}
