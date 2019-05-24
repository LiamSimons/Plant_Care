package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import classes.RecyclerViewAdapter;

public class MyPlants extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    private String email;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");

        ArrayList<String> plantNames = new ArrayList<>();


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapter(this, plantNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void goToAdd(View view) {
        Intent intent = new Intent (this, AddPlant.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
