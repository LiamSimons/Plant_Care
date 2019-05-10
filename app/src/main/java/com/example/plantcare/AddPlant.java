package com.example.plantcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.time.LocalDate;

public class AddPlant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }


    public void done(View view) {
        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String species = ((EditText) findViewById(R.id.editText_species)).getText().toString();
        int waterTime = (EditText)findViewById(R.id.editText_water);
        int light = ..
        LocalDate date = ((EditText) findViewById(R.id.editText_day))
        if (((CheckBox)findViewById(R.id.checkBox)).isChecked()){
            date = static LocalDate.now();
            //HELP
        }
    }
}
