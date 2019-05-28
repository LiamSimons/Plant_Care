package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

import classes.Plant;
import classes.RecyclerViewAdapter;

public class MyPlants extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    private String email;
    private RecyclerViewAdapter adapter;
    private final ArrayList<String> plantNames = new ArrayList<>();
    private final ArrayList<Plant> plants = new ArrayList<>();
    private  int length;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");

        addAllPlantsToArrayLists();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapter(this, plantNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        System.out.println(plantNames);
        System.out.println();
    }

    private void addAllPlantsToArrayLists() {
        String url = "https://studev.groept.be/api/a18_sd409/getPlantData/" + email;
        System.out.println(url);
        RequestQueue queue =  Volley.newRequestQueue(this);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            length = response.length();
                            if(length == 0){
                                ((TextView) findViewById(R.id.errorMessage)).setText("You have no plants.");
                            }
                            else {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject row = response.getJSONObject(i);
                                    String name = row.getString("name");
                                    String species = row.getString("species");
                                    String plantDay = row.getString("plantDay");
                                    String waterTime = row.getString("waterTime");
                                    //gebruik hier nog plant class
                                    Plant plant = new Plant(name, species, plantDay, waterTime);
                                    plants.add(plant);
                                    plantNames.add(name);
                                    System.out.println("koekje");
                                    refreshView();
                                }
                            }
                        }
                        catch(JSONException e) {
                            if (length == 0) {
                                ((TextView) findViewById(R.id.errorMessage)).setText("You have no plants.");
                            }
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

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        plantNames.add("testPlant");
        System.out.println(plantNames);


    }

    public void goToAdd(View view) {
        Intent intent = new Intent (this, AddPlant.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        int number = position + 1;
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + number, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, PlantProfile.class);
//        intent.pu
    }
    private void refreshView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
