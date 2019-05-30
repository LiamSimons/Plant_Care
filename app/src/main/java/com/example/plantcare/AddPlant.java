package com.example.plantcare;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import org.json.JSONException;
import org.json.JSONObject;

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
    public void done(final View view) {
        final String name = ((EditText) findViewById(R.id.editText_name)).getText().toString().trim();
        final String species = ((EditText) findViewById(R.id.editText_species)).getText().toString().trim();
        final String str1 = ((EditText) findViewById(R.id.editText_water)).getText().toString().trim();
        final Context context = this;

        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");

        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(species)||TextUtils.isEmpty(str1)||TextUtils.isEmpty(datum)){
            Toast.makeText(this, "Fill in every field to add plant.", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(name + ", " + species + ", " + datum + ", " + str1 + "/" + email);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://studev.groept.be/api/a18_sd409/addPlant/";
        String url1 = "https://studev.groept.be/api/a18_sd409/checkPlant/";
        JsonArrayRequest request = new JsonArrayRequest( url1 + name + "/" + email + "/" + name,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject answer = response.getJSONObject(0);
                            String answer1 = answer.getString("answer");
                            Toast.makeText(context, "You already have a plant with this name", Toast.LENGTH_LONG).show();
                            return;
                        }
                        catch(JSONException e) {
                            System.out.println(url + name + "/" + species + "/" + datum + "/" + str1 + "/" + email);
                            JsonArrayRequest request1 = new JsonArrayRequest(url + name + "/" + species + "/" + datum + "/" + str1 + "/" + email,

                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            System.out.println("Successfully added plant.");
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println("Database not found!");
                                        }
                                    });

                            queue.add(request1);
                            Toast.makeText(context, "You added " + name + " to the database.", Toast.LENGTH_SHORT).show();

                            goToMyPlants(view);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((TextView)findViewById(R.id.errorMessage)).setText("You have no internet connection. Turn on your wifi or mobile data and try again.");

                    }
                });
        queue.add(request);
    }

    public void goToMyPlants(View view){
        Intent intent = new Intent(this, MyPlants.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }


}
