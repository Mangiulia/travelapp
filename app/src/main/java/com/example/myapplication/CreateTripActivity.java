package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTripActivity extends AppCompatActivity {
    Button btnTrip;
    EditText etDestination, etDays;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnTrip = findViewById(R.id.btnTrip);
        etDays = findViewById(R.id.etNumerDays);
        etDestination = findViewById(R.id.etDestination);
        btnBack = findViewById(R.id.buttonBack);

        btnTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateTripActivity.this, GenerateTripActivity.class);
                intent.putExtra("destination", etDestination.getText().toString());
                intent.putExtra("numDays", etDays.getText().toString());
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateTripActivity.this,TripsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }
}