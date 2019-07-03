package com.sample.triphistory.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.triphistory.R;
import com.sample.triphistory.constant.Constants;
import com.sample.triphistory.helper.Dispatcher;
import com.sample.triphistory.model.Trip;
import com.sample.triphistory.model.TripHistory;
import com.sample.triphistory.utils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class TripHistoryListAdapter extends RecyclerView.Adapter<TripHistoryListAdapter.ViewHolder> {


    private static final String TAG = TripHistoryListAdapter.class.getSimpleName();
    private TripHistory tripHistory;

    public TripHistoryListAdapter(@NonNull TripHistory tripHistory) {
        this.tripHistory = tripHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Trip trip = tripHistory.getTrips().get(position);
        holder.startTime.setText(DateTimeUtils.getDisplayTime(trip.getStartTime()));
        holder.endTime.setText(DateTimeUtils.getDisplayTime(trip.getEndTime()));
        holder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispatcher.showTripDetailActivity(holder.startTime.getContext(), trip.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripHistory.getTrips() != null ? tripHistory.getTrips().size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView startTime;
        TextView endTime;
        ImageView preview;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            preview = itemView.findViewById(R.id.preview_map);
        }
    }
}
