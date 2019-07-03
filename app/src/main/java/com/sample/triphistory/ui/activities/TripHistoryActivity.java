package com.sample.triphistory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sample.triphistory.R;
import com.sample.triphistory.ui.fragments.TripHistoryListFragment;

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
