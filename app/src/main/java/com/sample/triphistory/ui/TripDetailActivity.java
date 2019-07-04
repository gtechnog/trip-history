package com.sample.triphistory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.triphistory.R;
import com.sample.triphistory.constant.BundleKeys;
import com.sample.triphistory.model.Trip;
import com.sample.triphistory.viewmodel.TripDetailViewModel;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TripDetailViewModel viewModel;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            finish();
        }

        String tripId;
        tripId = getIntent().getExtras().getString(BundleKeys.BUNDLE_KEY_TRIP_ID);

        viewModel = ViewModelProviders.of(this).get(TripDetailViewModel.class);
        viewModel.setTripId(tripId);
        trip = viewModel.getTripDetails();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng sydney = new LatLng(trip.getPath().get(0).getLatitude(), trip.getPath().get(0).getLongitude());
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.addMarker(viewModel.getStartMarkerOptions()).setTitle("start position");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(viewModel.getStartLatLng()));


//        mMap.addMarker(viewModel.getEndMarkerOptions());
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(viewModel.getEndLatLng()));

        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null);

        mMap.addMarker(viewModel.getEndMarkerOptions()).setTitle("End position");
        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(viewModel.getEndLatLng())      // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
