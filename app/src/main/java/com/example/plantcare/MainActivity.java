package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");
    }

    public void goToMyPlants(View view) {
        Intent intent= new Intent(this, MyPlants.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    public void goToAdd(View view) {
        Intent intent = new Intent (this, AddPlant.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}
