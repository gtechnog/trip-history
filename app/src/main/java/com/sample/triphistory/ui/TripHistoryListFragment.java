package com.sample.triphistory.ui;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.triphistory.R;
import com.sample.triphistory.utils.Injector;
import com.sample.triphistory.viewmodel.TripHistoryListViewModel;

import java.util.Objects;

public class TripHistoryListFragment extends Fragment {

    private TripHistoryListViewModel viewModel;

    public TripHistoryListFragment() {
    }

    static TripHistoryListFragment newInstance() {
        TripHistoryListFragment fragment = new TripHistoryListFragment();
        // to put some arguments to bundle
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        viewModel = ViewModelProviders.of(this,
                Injector.provideTripHistoryListViewModelFactory((Application) Objects.requireNonNull(getContext()).getApplicationContext())).
                get(TripHistoryListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_history_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(new TripHistoryListAdapter(viewModel.getTripHistory()));
        }
        return view;
    }
}
