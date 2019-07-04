package com.sample.triphistory.ui;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.sample.triphistory.constant.Constants;
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
                marker.setIcon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_directions));

        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(viewModel.getTripBounds(), 64);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(cameraUpdate);
                addPolyLineToPath();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animateMarker(marker);
                    }
                }, 500);
            }
        });
    }

    /**
     * adding the line along the path
     */
    private void addPolyLineToPath() {
        List<LatLng> listOfPath = viewModel.getPathLatLngList();
        PolylineOptions options;
        for (int i = 0; i < listOfPath.size() - 1; i++) {
            LatLng src = listOfPath.get(i);
            LatLng dest = listOfPath.get(i + 1);
            options = new PolylineOptions();
            options.add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude));
            options.width(5).color(Color.GREEN).geodesic(true);
            mMap.addPolyline(options);
        }
    }

    /**
     *  animate the market along the path
     * @param marker marker that has to move
     */
    private void animateMarker(final Marker marker) {
        final Handler handler = new Handler();
        final long end = viewModel.getEndTimeInMillies();
        final long start = viewModel.getStartTimeInMillies();
        final long duration = end - start;
        final Interpolator interpolator = new LinearInterpolator();
        final List<Step> path = viewModel.getPath();

        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                long elapsed = path.get(i).getTime_millis() - start;
                float interpolation = interpolator.getInterpolation((float) elapsed
                        / duration);
                if (i < path.size())
                    marker.setPosition(new LatLng(path.get(i).getLatitude(), path.get(i).getLongitude()));
                i++;

                if (interpolation < 1.0) {
                    long delay = 0;
                    if (i < path.size()) {
                        delay =( ( path.get(i).getTime_millis() - path.get(i-1).getTime_millis() ) ) * Constants.ANIMATION_TIME / duration;
                    }
                    handler.postDelayed(this, delay);
                }
            }
        });
    }
}
