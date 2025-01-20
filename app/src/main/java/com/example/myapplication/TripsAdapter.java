package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    private List<TravelPlan> travelPlans;
    private OnPlanClickListener listener;

    public TripsAdapter(List<TravelPlan> travelPlans, OnPlanClickListener listener) {
        this.travelPlans = travelPlans;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TravelPlan travelPlan = travelPlans.get(position);
        holder.bind(travelPlan, listener);
    }

    @Override
    public int getItemCount() {
        return travelPlans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDestination;
        public ImageView ivPlanImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDestination = itemView.findViewById(R.id.tvTravelPlan);
            ivPlanImage = itemView.findViewById(R.id.ivDestination);
        }

        public void bind(final TravelPlan travelPlan, final OnPlanClickListener listener) {
            tvDestination.setText(travelPlan.getDestination());
            if (travelPlan.getImageUrl() != null && !travelPlan.getImageUrl().isEmpty()) {
                Picasso.get().load(travelPlan.getImageUrl()).into(ivPlanImage);
            } else {
                ivPlanImage.setImageResource(R.drawable.image_not_available); // Imagine default
            }
            itemView.setOnClickListener(v -> listener.onPlanClick(travelPlan));
        }
    }

    public interface OnPlanClickListener {
        void onPlanClick(TravelPlan travelPlan);
    }
}
