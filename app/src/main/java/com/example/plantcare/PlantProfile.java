package com.example.plantcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlantProfile extends AppCompatActivity {
    private String name;
    private String species;
    private String plantDay;
    private String waterTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_profile);
        //moet nog plant object door krijgen met intent
        Bundle extras = getIntent().getExtras();
        name = extras.getString("NAME");
        species = extras.getString("SPECIES");
        plantDay = extras.getString("DAY");
        waterTime = extras.getString("TIME");


        TextView naam = findViewById(R.id.textViewName);
        naam.setText(name);

        TextView soort = findViewById(R.id.textViewSpecies);
        soort.setText(species);

        TextView dag = findViewById(R.id.textViewDate);
        dag.setText(plantDay);

        TextView tijd = findViewById(R.id.textViewWater);
        tijd.setText(waterTime);
    }
}
