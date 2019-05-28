package com.example.plantcare;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.Calendar;

public class AddPlant extends AppCompatActivity {

    private static final String TAG = "AddPlant";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Calendar cal;
    private String datum;
    private Button doneButton;
    private String email;



    //Als die op de textview klinkt zou er een kalender tevoorschijn moeten komen waar hij een datum kan kiezen.
    //https://www.youtube.com/watch?v=hwe1abDO2Ag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        doneButton = findViewById(R.id.loginButton);

        mDisplayDate = (TextView) findViewById(R.id.textdate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddPlant.this,
                        android.R.style.Theme_Black,
                        mDateSetListener,
                        year, month, day);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                datum = day + "-" + month + "-" + year;
                mDisplayDate.setText(datum);
            }
        };
    }



    // optie: voeg nog light intensity toe, (alertdialog builder)

    //zou nieuw plant object moeten aanmaken en die in de db storen
    public void done(View view) {
        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString().trim();
        String species = ((EditText) findViewById(R.id.editText_species)).getText().toString().trim();
        String str1 = ((EditText) findViewById(R.id.editText_water)).getText().toString().trim();

        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");

        System.out.println(name + ", " + species + ", " + datum + ", " + str1 + "/" + email);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a18_sd409/addPlant/";
        System.out.println(url + name + "/" + species + "/" + datum + "/" + str1 + "/" + email);
        JsonArrayRequest request = new JsonArrayRequest(url + name + "/" + species + "/" + datum + "/" + str1 + "/" + email,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Succesfully added plant.");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Database not found!");
                    }
                });

        queue.add(request);
        Toast.makeText(this, "You added " + name + " to the database.", Toast.LENGTH_SHORT).show();

        goToMyPlants(view);
    }

    public void goToMyPlants(View view){
        Intent intent = new Intent(this, MyPlants.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }


}
