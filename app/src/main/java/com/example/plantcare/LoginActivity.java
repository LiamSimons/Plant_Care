package com.example.plantcare;


import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.Editable;
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


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button loginButton;
    private Button registerButton;
    private Context context;

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editTextUsername.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();
            loginButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            registerButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        editTextUsername.addTextChangedListener(loginTextWatcher);
        editTextPassword.addTextChangedListener(loginTextWatcher);
        context = this;
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

    public void sendMessage(View view) {
        Toast.makeText(this, "You successfully logged in.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (this, MainActivity.class);
        EditText editText = findViewById(R.id.email);
        String mail = editText.getText().toString();
        intent.putExtra("EMAIL", mail);
        startActivity(intent);
    }

    public void goToRegister(View view) {
        Intent intent= new Intent(this, RegisterAvtivity.class);
        startActivity(intent);
    }

    public void checkUsername(final View view) {

        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        RequestQueue queue =  Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a18_sd409/checkPassword/";
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String pass = getHash(password);
        url += pass + "/" + email;
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject user = response.getJSONObject(0);
                            String answer = user.getString("answer");
                            System.out.println(answer);
                            TextView statusText = findViewById(R.id.errorMessage);
                            if (answer.equals("1")) {
                                sendMessage(view);
                            }
                            else {
                                statusText.setText("Incorrect password.");
                            }

//                            else {
//                                statusText.setText("Please register first.");
//                            }
                        }
                        catch(JSONException e) {
                            ((TextView)findViewById(R.id.errorMessage)).setText("Please register first.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Database not found!");
                    }
                });

        queue.add(request);
    }
    //}
}

