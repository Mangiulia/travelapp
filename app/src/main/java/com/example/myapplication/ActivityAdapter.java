package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private Context mContext;
    private List<Activity> mActivities;

    public ActivityAdapter(Context context, List<Activity> activities) {
        mContext = context;
        mActivities = activities;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_item, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = mActivities.get(position);
        holder.tvActivityTime.setText(activity.getTime());
        holder.tvActivityDescription.setText(activity.getDescription());
    }

    @Override
    public int getItemCount() {
        return mActivities.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityTime;
        TextView tvActivityDescription;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityTime = itemView.findViewById(R.id.tvActivityTime);
            tvActivityDescription = itemView.findViewById(R.id.tvActivityDescription);
        }
    }
}
