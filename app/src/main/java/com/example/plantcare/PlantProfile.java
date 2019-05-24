package com.example.plantcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import classes.Plant;

public class PlantProfile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_profile);
        //moet nog plant object door krijgen met intent

//        TextView naam = findViewById(R.id.textViewName);
//        String str1 = Plant.getNickname();
//        naam.setText(str1);
//        TextView species = findViewById(R.id.textViewSpecies);
//        String str2 = Plant.getSpecies();
//        species.setText(str2);
//        TextView day = findViewById(R.id.textViewDate);
//        String str3 = Plant.getPlantDate();
//        naam.setText(str3);
//        TextView water = findViewById(R.id.textViewWater);
//        String str4 = Plant.getWateringTime();
//        species.setText(str4);
    }
}
