package com.example.plantcare;


import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;

import classes.Plant;

public class AddPlant extends AppCompatActivity {

    private static final String TAG = "AddPLant";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Calendar cal;
    private String datum;

    //Als die op de textview klinkt zou er een kalender tevoorschijn moeten komen waar hij een datum kan kiezen.
    //https://www.youtube.com/watch?v=hwe1abDO2Ag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
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
                        year,month,day);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;

                datum = day +"/" + month +"/"+year;
                mDisplayDate.setText(datum);
            }
        };
    }

    // optie: voeg nog light intensity toe, (alertdialog builder)

//zou nieuw plant object moeten aanmaken en die in de db storen
    public void done(View view) {
        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String species = ((EditText) findViewById(R.id.editText_species)).getText().toString();
        String str1 = ((EditText) findViewById(R.id.editText_water)).getText().toString();
        int watertime = new Integer(str1).intValue();

        System.out.println(name+", "+species+", "+watertime+", " + datum);
        Plant plant = new Plant(name,species,cal,watertime);

//        Context context = getApplicationContext();
//        plant.plantToDB(context);



        }
    }
