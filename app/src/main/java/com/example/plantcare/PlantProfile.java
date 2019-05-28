package com.example.plantcare;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlantProfile extends AppCompatActivity {
    private String id;
    private String name;
    private String species;
    private String plantDay;
    private String waterTime;
    private String email;
    private boolean deleteCount = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_profile);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("ID");
        name = extras.getString("NAME");
        species = extras.getString("SPECIES");
        plantDay = extras.getString("DAY");
        waterTime = extras.getString("TIME");
        email = extras.getString("EMAIL");

        TextView naam = findViewById(R.id.textViewName);
        naam.setText(name);

        TextView soort = findViewById(R.id.textViewSpecies);
        soort.setText(species);

        TextView dag = findViewById(R.id.textViewDate);
        dag.setText(plantDay);

        TextView tijd = findViewById(R.id.textViewWater);
        tijd.setText(waterTime);
    }
    public void deletePlantFromDatabase(final View view) {
        if (!deleteCount){
            Toast.makeText(this, "Are you sure you want to delete " + name + "? Press delete again to delete the plant.", Toast.LENGTH_LONG).show();
            deleteCount = true;
            return;
        }
        String url = "https://studev.groept.be/api/a18_sd409/deletePlant/" + id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject row = response.getJSONObject(0);
                            goToMyPlants();
                            showText();
                        }
                        catch(JSONException e) {
                            goToMyPlants();
                            showText();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((TextView) findViewById(R.id.errorMessage)).setText("Database not found");
                    }
                });
        queue.add(request);
    }

    private void goToMyPlants(){
        Intent intent = new Intent(this, MyPlants.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    private void showText(){
        Toast.makeText(this, name + " was successfully deleted.", Toast.LENGTH_LONG).show();
    }
}
