package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelPlanAdapter extends RecyclerView.Adapter<TravelPlanAdapter.ViewHolder> {

    private Context context;
    private List<TravelPlan> travelPlans;
    private List<String> travelPlanImages;

    public TravelPlanAdapter(Context context, List<TravelPlan> travelPlans, List<String> travelPlanImages) {
        this.context = context;
        this.travelPlans = travelPlans;
        this.travelPlanImages = travelPlanImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_travel_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TravelPlan travelPlan = travelPlans.get(position);
        String imageUrl = travelPlanImages.get(position);

        holder.tvTravelPlan.setText(travelPlan.toString());

        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(holder.ivDestination);
        } else {
            holder.ivDestination.setImageResource(R.drawable.image_not_available); // Imagine de placeholder
        }
    }

    @Override
    public int getItemCount() {
        return travelPlans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTravelPlan;
        ImageView ivDestination;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTravelPlan = itemView.findViewById(R.id.tvTravelPlan);
            ivDestination = itemView.findViewById(R.id.ivDestination);
        }
    }
}
