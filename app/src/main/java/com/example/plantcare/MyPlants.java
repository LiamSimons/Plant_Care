package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyPlants extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");
        
    }

    public void goToAdd(View view) {
        Intent intent = new Intent (this, AddPlant.class);
        startActivity(intent);
    }
}
