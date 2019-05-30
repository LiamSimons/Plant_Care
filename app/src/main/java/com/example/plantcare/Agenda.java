package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import classes.RecyclerViewAdapter;

public class Agenda extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    private String date;
    private int day;
    private int month;
    private int year;
    private String email;
    private RecyclerViewAdapter adapter;
    private final ArrayList<String> plantId = new ArrayList<>();
    private final ArrayList<String> plantNames = new ArrayList<>();
    private final ArrayList<String> plantSpecies = new ArrayList<>();
    private final ArrayList<String> plantDays = new ArrayList<>();
    private final ArrayList<String> plantWateringTimes = new ArrayList<>();
    private int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        CalendarView calendarView;
        calendarView = findViewById(R.id.calendarView);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");

        TextView info = findViewById(R.id.info);
        info.setText("Today these plants need water:");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int jaar, int maand, int dag) {
                maand++;
                date = dag + "-" + maand + "-" + jaar;
                maand--;
                year = jaar;
                month = maand;
                day = dag;
                TextView info = findViewById(R.id.info);
                info.setText("On " + date + " these plants need water:");
                plantId.clear();
                plantNames.clear();
                plantSpecies.clear();
                plantDays.clear();
                plantWateringTimes.clear();
                addAllPlantsToArrayLists(year,month,day);
                refreshView();
            }
        });

        Calendar now = Calendar.getInstance();
        int daynow = now.get(Calendar.DAY_OF_MONTH);
        int monthnow = now.get(Calendar.MONTH);
        int yearnow = now.get(Calendar.YEAR);

        System.out.println("AAAA" + now);
        plantId.clear();
        plantNames.clear();
        plantSpecies.clear();
        plantDays.clear();
        plantWateringTimes.clear();
        addAllPlantsToArrayLists(yearnow,monthnow,daynow);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapter(this, plantNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    private void addAllPlantsToArrayLists(final int year, final int month, final int day) {
        String url = "https://studev.groept.be/api/a18_sd409/getPlantData/" + email;
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
                                    String id = row.getString("id");
                                    String name = row.getString("name");
                                    String species = row.getString("species");
                                    String plantDay = row.getString("plantDay");
                                    String waterTime = row.getString("waterTime");
                                    int time;
                                    if (waterTime.equals("0")){
                                        time = 1;
                                    }
                                    else{
                                        time = Integer.valueOf(waterTime);
                                    }

                                    String[] parts = plantDay.split("-");
                                    String plantDag = parts[0];
                                    String plantMaand = parts[1];
                                    String plantJaar = parts[2];

                                    int dag = Integer.valueOf(plantDag);
                                    int maand = Integer.valueOf(plantMaand) - 1;
                                    int jaar = Integer.valueOf(plantJaar);

                                    Calendar c = Calendar.getInstance();
                                    c.set(year, month, day);
                                    Calendar c2 = Calendar.getInstance();
                                    c2.set(jaar, maand, dag, 0, 0);

                                    long diffInMillies = Math.abs(c.getTime().getTime() - c2.getTime().getTime());
                                    long diff = diffInMillies/(1000*60*60*24);
                                    int dif = (int) diff;
                                    System.out.println(diff);
                                    System.out.println(dif);
                                    if (dif%time==0) {
                                        plantId.add(id);
                                        plantNames.add(name);
                                        plantSpecies.add(species);
                                        plantDays.add(plantDay);
                                        plantWateringTimes.add(waterTime);
                                    }
                                    refreshView();
                                }
                            }
                        }
                        catch(JSONException e) {
                            if (length == 0) {
                                ((TextView) findViewById(R.id.info)).setText("No plants need water on " + date + ".");
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((TextView) findViewById(R.id.info)).setText("Database not found");
                    }
                });

        queue.add(request);
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PlantProfile.class);
        String id = plantId.get(position);
        String name = plantNames.get(position);
        String species = plantSpecies.get(position);
        String plantDay = plantDays.get(position);
        String waterTime = plantWateringTimes.get(position);
        intent.putExtra("ID", id);
        intent.putExtra("NAME", name);
        intent.putExtra("SPECIES", species);
        intent.putExtra("DAY", plantDay);
        intent.putExtra("TIME", waterTime);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    private void refreshView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
