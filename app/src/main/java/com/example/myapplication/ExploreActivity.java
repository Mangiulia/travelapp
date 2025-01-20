package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

public class ExploreActivity extends AppCompatActivity implements Serializable {
   // static ListView lvTrip;
    BottomNavigationView bottomNavigationView;
    ImageView imgParis , imgIstanbul , imgRome , imgMaldives , imgCairo , imgLondon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.optionHome);
        imgParis = findViewById(R.id.imageView3);
        imgIstanbul = findViewById(R.id.imageView6);
        imgLondon = findViewById(R.id.imageView4);
        imgMaldives = findViewById(R.id.imageView8);
        imgRome = findViewById(R.id.imageView5);
        imgCairo = findViewById(R.id.imageView);

        imgParis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenerateTripActivity("Paris","3");
            }
        });
        imgRome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenerateTripActivity("Rome","3");
            }
        });
        imgIstanbul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenerateTripActivity("Istanbul","5");
            }
        });
        imgMaldives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenerateTripActivity("Maldive","7");
            }
        });
        imgCairo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenerateTripActivity("Cairo","5");
            }
        });
        imgLondon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenerateTripActivity("London","4");
            }
        });
        // Afișează datele în TextView-uri


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.optionHome) {
                    Intent intent1 = new Intent(getApplicationContext(), ExploreActivity.class);
                    startActivity(intent1);
                    return true;
                }else
                if(item.getItemId() == R.id.optionProfile) {
                    Intent intent2 = new Intent(getApplicationContext() , ProfileActivity.class);
                    startActivity(intent2);
                    return true;
                } else
                    if(item.getItemId() == R.id.optionTrips) {
                Intent intent2 = new Intent(getApplicationContext() , TripsActivity.class);
                startActivity(intent2);
                return true;
            }
                return false;
            }
        });


    }

    private void navigateToGenerateTripActivity(String destination, String numDays) {
        Intent intent = new Intent(ExploreActivity.this, GenerateTripActivity.class);
        intent.putExtra("destination", destination);
        intent.putExtra("numDays", numDays);
        startActivity(intent);
    }




}