package classes;


import android.content.Context;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

// serializable is om plant object te kunnen doorgeven bij intents bij put extra
public class Plant implements Serializable {
    private String nickname;
    private String species;
    private Calendar plantDate;
    private Integer wateringTime;
    private Integer userId;
    // private int lightIntensity;

    public Plant(String nickname, String species, Calendar plantDate, int wateringTime) {
        this.nickname = nickname;
        this.species = species;
        this.plantDate = plantDate;
        this.wateringTime = wateringTime;
        //moet nog een userid bij
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Calendar getPlantDate() {
        return plantDate;
    }

    public String getPlantDateStr(){
        int year = plantDate.get(Calendar.YEAR);
        int month = plantDate.get(Calendar.MONTH);
        int day = plantDate.get(Calendar.DAY_OF_MONTH);
        String datum = day +"/" + month +"/"+year;
        return datum;
        //terug in calendar type via simple dateformat denk
    }

    public void setPlantDate(Calendar plantDate) {
        this.plantDate = plantDate;
    }

    public int getWateringTime() {
        return wateringTime;
    }

    public void setWateringTime(int wateringTime) {
        this.wateringTime = wateringTime;
    }

    public void plantToDB(Context context) {
        String url = "https://studev.groept.be/api/a18_sd409/addPlant/";
        url = url + nickname + "/" + species + "/" + plantDate + "/" + wateringTime;

        StringRequest answer = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//handle?
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parms = new HashMap<>();
                parms.put("name", nickname);
                parms.put("species", species);
                parms.put("plantday", getPlantDateStr());
                parms.put("watertime", wateringTime.toString());
//moet nog user id bij
               // parms.put("users_idusers", userId.toString());
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(answer);
    }


}
