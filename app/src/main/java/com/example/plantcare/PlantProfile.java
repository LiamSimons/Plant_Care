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
        TextView naam = findViewById(R.id.textView2);
        String str = Plant.getNickname();
        naam.setText(str);
    }
}
