package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.TravelViewHolder> {
    private Context mContext;
    private List<TravelDay> mTravelDays;

    public TravelAdapter(Context context, List<TravelDay> travelDays) {
        mContext = context;
        mTravelDays = travelDays;
    }

    @NonNull
    @Override
    public TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.travel_day_item, parent, false);
        return new TravelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelViewHolder holder, int position) {
        TravelDay travelDay = mTravelDays.get(position);
        holder.tvDay.setText("Day " + travelDay.getDay());

        ActivityAdapter activityAdapter = new ActivityAdapter(mContext, travelDay.getActivities());
        holder.rvActivities.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rvActivities.setAdapter(activityAdapter);
    }

    @Override
    public int getItemCount() {
        return mTravelDays.size();
    }

    public static class TravelViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        RecyclerView rvActivities;

        public TravelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            rvActivities = itemView.findViewById(R.id.rvActivities);
        }
    }
}
