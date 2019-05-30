package com.example.plantcare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterAvtivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button loginButton;
    private Button registerButton;

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editTextUsername.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();
            registerButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_avtivity);
        editTextUsername = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        editTextUsername.addTextChangedListener(loginTextWatcher);
        editTextPassword.addTextChangedListener(loginTextWatcher);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }
    public void skipLogin(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void register(final View v) {


        final String email = ((EditText) findViewById(R.id.email)).getText().toString();
        final String password = ((EditText) findViewById(R.id.password)).getText().toString();
        final String familyName = ((EditText) findViewById(R.id.familyName)).getText().toString();
        final String name = ((EditText) findViewById(R.id.name)).getText().toString();
        final Context context = this;

        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(familyName)||TextUtils.isEmpty(password)||TextUtils.isEmpty(email)){
            Toast.makeText(this, "Fill in every field to register.", Toast.LENGTH_LONG).show();
            return;
        }



        final RequestQueue queue =  Volley.newRequestQueue(this);
        String url1 = "https://studev.groept.be/api/a18_sd409/checkEmail/";
        JsonArrayRequest request = new JsonArrayRequest( url1 + email,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject user = response.getJSONObject(0);
                            String email1 = user.getString("email");
                            Toast.makeText(context, "This email is already in use.", Toast.LENGTH_LONG).show();
                            return;

                        }
                        catch(JSONException e) {
                            String url = "https://studev.groept.be/api/a18_sd409/setUserData/";
                            JsonArrayRequest request1 = new JsonArrayRequest(url + email + "/" + getHash(password) + "/" + familyName + "/" + name,

                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            System.out.println("Succesfully registered");
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println("Database not found!");
                                        }
                                    });

                            queue.add(request1);
                            goToLogin(v);
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
    private String getHash(String pass) {

        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();

            for (byte b : bytes) {
                result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result.substring(45);
    }
    public void goToLogin(View view) {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
