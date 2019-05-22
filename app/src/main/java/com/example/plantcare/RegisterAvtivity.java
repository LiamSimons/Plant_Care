package com.example.plantcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

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


        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String familyName = ((EditText) findViewById(R.id.familyName)).getText().toString();
        String name = ((EditText) findViewById(R.id.name)).getText().toString();

        RequestQueue queue =  Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a18_sd409/setUserData/";
        System.out.println(url + email + "/" + getHash(password) + "/" + familyName + "/" + name);
        JsonArrayRequest request = new JsonArrayRequest(url + email + "/" + getHash(password) + "/" + familyName + "/" + name,

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

        queue.add(request);
        goToLogin(v);

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
