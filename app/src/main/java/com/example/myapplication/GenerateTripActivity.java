package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GenerateTripActivity extends AppCompatActivity {

    RecyclerView rvTrips;
    private String destination;
    private String numDays;
    private TravelPlan travelPlan; // Variabila travelPlan la nivel de clasă
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button floatingActionButton, btnStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_trip);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rvTrips = findViewById(R.id.recycleView);
        Intent intent = getIntent();
        destination = intent.getStringExtra("destination");
        numDays = intent.getStringExtra("numDays");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("Auth", mAuth.toString());

        // Lansează cererea API automat la crearea activității
        new HttpAsyncTask().execute();

        floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), ExploreActivity.class);
            startActivity(intent1);
        });

        btnStar = findViewById(R.id.btnStar);
        btnStar.setOnClickListener(v -> {
            Log.d("Button Click", "btnStar clicked");
            saveTravelPlan();
        });
    }

    private void saveTravelPlan() {
        if (travelPlan != null) {
            Log.d("Travel plan", travelPlan.toString());
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                String key = mDatabase.child("travel_plans").child(userId).push().getKey();
                travelPlan.setKey(key);

                // Verificăm dacă URL-ul imaginii este setat corect
                if (travelPlan.getImageUrl() != null) {
                    mDatabase.child("travel_plans").child(userId).child(key).setValue(travelPlan)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(GenerateTripActivity.this, "Planul de călătorie a fost salvat", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(GenerateTripActivity.this, "Eroare la salvarea planului de călătorie", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Log.e("Save Error", "URL-ul imaginii este null");
                    Toast.makeText(GenerateTripActivity.this, "URL-ul imaginii nu este setat", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Log.e("Travel plan", "Planul de călătorie este null");
            Toast.makeText(GenerateTripActivity.this, "Planul de călătorie nu a fost încă generat", Toast.LENGTH_SHORT).show();
        }
    }

    private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://ai-trip-planner.p.rapidapi.com/?days=" + numDays + "&destination=" + destination)
                    .get()
                    .addHeader("X-RapidAPI-Key", "c2fc470af3mshe43bcb0e70577c2p18c383jsneb98a0f7d1cd")
                    .addHeader("X-RapidAPI-Host", "ai-trip-planner.p.rapidapi.com")
                    .build();

            try {
                Response travelPlanResponse = client.newCall(request).execute();
                String travelPlanResult = travelPlanResponse.body().string();
                travelPlan = new Gson().fromJson(travelPlanResult, TravelPlan.class);
                if (travelPlan == null || travelPlan.getPlan() == null) {
                    Log.e("Travel Plan Error", "travelPlan sau planul este null");
                    return null;
                }
                Request unsplashRequest = new Request.Builder()
                        .url("https://api.unsplash.com/photos/random?query=" + destination + "&client_id=qUlE60n4T75-6QalvFg_l8Xh-0daRUHhDDQo7JvF__A")
                        .build();
                Response unsplashResponse = client.newCall(unsplashRequest).execute();
                String unsplashResult = unsplashResponse.body().string();
                JsonObject unsplashJson = new Gson().fromJson(unsplashResult, JsonObject.class);
                String imageUrl = unsplashJson.get("urls").getAsJsonObject().get("regular").getAsString();
                travelPlan.setImageUrl(imageUrl);
                travelPlan.setDestination(destination);
                return new Gson().toJson(travelPlan);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.d("HTTP Response", result);
                parseAndDisplayResult(result);
            } else {
                Log.e("HTTP Error", "Solicitarea HTTP a eșuat");
            }
        }

        private void parseAndDisplayResult(String result) {
            try {
               Gson gson = new Gson();
                travelPlan = gson.fromJson(result, TravelPlan.class);
                if (travelPlan != null && travelPlan.getPlan() != null) {
                    TravelAdapter travelAdapter = new TravelAdapter(GenerateTripActivity.this, travelPlan.getPlan());
                    rvTrips.setLayoutManager(new LinearLayoutManager(GenerateTripActivity.this));
                    rvTrips.setAdapter(travelAdapter);
                    Log.d("Travel plan set", "Travel plan has been set successfully");
                } else {
                    Log.e("Parsing Error", "Răspuns JSON incorect: obiect null sau planul de călătorie este null");
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Log.e("Parsing Error", "Eroare la parsarea răspunsului JSON");
            }
        }
    }
}
