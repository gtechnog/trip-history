package com.sample.triphistory.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.triphistory.R;

public class TripHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_container, TripHistoryListFragment.newInstance(), null)
                .commit();

    }
}
