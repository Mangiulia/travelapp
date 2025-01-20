package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity {
    ListView lvTrips;
    LinearLayoutManager linearLayoutManager;
    TravelPlanAdapter travelPlanAdapter;
    List<TravelPlan> travelPlans;
    List<String> travelPlanImages;
    RecyclerView rvTrips;
    TripsAdapter tripsAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView cityImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        BottomNavigationView bottomNavigationView;
        FloatingActionButton buttonAddTrip;
        rvTrips = findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(this));

        //cityImageView = findViewById(R.id.cityImageView);

        travelPlans = new ArrayList<>();
        tripsAdapter = new TripsAdapter(travelPlans, travelPlan -> onPlanClicked(travelPlan));
        rvTrips.setAdapter(tripsAdapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadSavedTrips();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.optionTrips);
        buttonAddTrip = findViewById(R.id.floatingActionButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.optionHome) {
                startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                return true;
            } else if (item.getItemId() == R.id.optionProfile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            } else if (item.getItemId() == R.id.optionTrips) {
                startActivity(new Intent(getApplicationContext(), TripsActivity.class));
                return true;
            }
            return false;
        });

        buttonAddTrip.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CreateTripActivity.class)));
    }

    private void loadSavedTrips() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            mDatabase.child("travel_plans").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    travelPlans.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TravelPlan plan = snapshot.getValue(TravelPlan.class);
                        if (plan != null) {
                            travelPlans.add(plan);
                            Log.d("TripsActivity", "Plan: " + plan.get_id() + ", Image URL: " + plan.getImageUrl());
                        }
                    }
                    tripsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("TripsActivity", "Eroare la citirea planurilor de călătorie", databaseError.toException());
                    Toast.makeText(TripsActivity.this, "Eroare la citirea planurilor de călătorie", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onPlanClicked(TravelPlan travelPlan) {
        Intent intent = new Intent(this, ItineraryActivity.class);
        intent.putExtra("travelPlan", travelPlan);
        startActivity(intent);
    }
}
