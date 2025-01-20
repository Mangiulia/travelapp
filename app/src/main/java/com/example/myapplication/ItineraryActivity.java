package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class ItineraryActivity extends AppCompatActivity {

    RecyclerView rvItinerary;
    TravelPlan travelPlan;
    Button btnDeleteItinerary, btnSavePin;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rvItinerary = findViewById(R.id.rvItinerary);
        btnDeleteItinerary = findViewById(R.id.btnDeleteItinerary);
        btnSavePin = findViewById(R.id.btnSavePin);
        btnBack = findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        travelPlan = (TravelPlan) getIntent().getSerializableExtra("travelPlan");
        Toast.makeText(getApplicationContext(),travelPlan.getKey().toString(),Toast.LENGTH_LONG);
        if (travelPlan != null && travelPlan.getPlan() != null) {
            TravelAdapter travelAdapter = new TravelAdapter(this, travelPlan.getPlan());
            rvItinerary.setLayoutManager(new LinearLayoutManager(this));
            rvItinerary.setAdapter(travelAdapter);

        } else {
            Log.e("ItineraryActivity", "Plan de călătorie null sau gol");
        }

        btnDeleteItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItinerary(travelPlan.getKey());
                Log.d("ItineraryActivity", "ID-ul itinerariului: " + travelPlan.getKey());

            }
        });

        btnSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePin();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItineraryActivity.this,TripsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }

    private void deleteItinerary(String id) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userItineraryRef = mDatabase.child("travel_plans").child(userId).child(id);

            userItineraryRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("ItineraryActivity", "Itinerariul cu ID-ul " + id + " a fost șters cu succes.");
                    finish(); // Înapoi la activitatea anterioară
                } else {
                    Log.e("ItineraryActivity", "Eroare la ștergerea itinerariului cu ID-ul " + id + ": " + task.getException().getMessage());
                }
            });
        }
    }

    private void savePin() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userPinsRef = mDatabase.child("users").child(userId).child("pins");

            if (travelPlan != null && travelPlan.getDestination() != null) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> addresses = geocoder.getFromLocationName(travelPlan.getDestination(), 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        double latitude = address.getLatitude();
                        double longitude = address.getLongitude();

                        Pin pin = new Pin(travelPlan.getDestination(), latitude, longitude);
                        userPinsRef.push().setValue(pin).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("ItineraryActivity", "Pin salvat cu succes.");
                            } else {
                                Log.e("ItineraryActivity", "Eroare la salvarea pinului.");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
