package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String email;
    private int count = 0;
    private boolean fooled = false;
    private boolean setting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");
        count = 0;
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

    public void showLogout(View view){
        TextView logout = findViewById(R.id.logout);
        if (!setting) {
            logout.setText("Logout");
            setting = true;
        }
        else{
            logout.setText("");
            setting = false;
        }
    }

    public void logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void easterEgg(View view){
        if(fooled == false) {
            if (count == 0) {
                Toast.makeText(this, "Here you can add a plant. Press 2 more times for a surprise!", Toast.LENGTH_LONG).show();
            }
            if (count == 1) {
                Toast.makeText(this, "Here you can add a plant. Press 1 more time for a surprise!", Toast.LENGTH_LONG).show();
            }

            if (count == 2) {
                Toast.makeText(this, "Haha fooled ya!!!", Toast.LENGTH_LONG).show();
                count = 0;
                fooled = true;
            }
            count += 1;
        }
        else{
            Toast.makeText(this, "Here you can add a plant.", Toast.LENGTH_LONG).show();

        }

    }

    public void yourPlants(View view){
        Toast.makeText(this, "Here you can view you plants.", Toast.LENGTH_LONG).show();
    }
    public void yourAgenda(View view){
        Toast.makeText(this, "This is your agenda, you can see when you have to water your plants. The wrong plants though, i'm sorry. But you get the idea.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You already logged in, you can't go back.", Toast.LENGTH_SHORT).show();
    }

    public void goToAgenda(View view){
        Intent intent = new Intent(this, Agenda.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}
