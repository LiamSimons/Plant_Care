package com.example.plantcare;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "notplanten";
    private String email;
    private int count = 0;
    private boolean fooled = false;
    private boolean setting = false;
//    private AlarmManager alarmMgr;
//    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EMAIL");
        count = 0;
        sendNotification();
//
//        alarmMgr = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//// Set the alarm to start at 8:30 a.m.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 30);
    }

    public void goToMyPlants(View view) {
        Intent intent= new Intent(this, MyPlants.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    public void goToAdd(View view) {
        Intent intent = new Intent (this, AddPlant.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    public void showLogout(View view){
        TextView logout = findViewById(R.id.logout);
        if (!setting) {
            logout.setText("Logout");
            setting = true;
        }
        else{
            logout.setText("");
            setting = false;
        }
    }

    public void logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void easterEgg(View view){
        if(fooled == false) {
            if (count == 0) {
                Toast.makeText(this, "Here you can add a plant. Press 2 more times for a surprise!", Toast.LENGTH_LONG).show();
            }
            if (count == 1) {
                Toast.makeText(this, "Here you can add a plant. Press 1 more time for a surprise!", Toast.LENGTH_LONG).show();
            }

            if (count == 2) {
                Toast.makeText(this, "Haha fooled ya!!!", Toast.LENGTH_LONG).show();
                count = 0;
                fooled = true;
            }
            count += 1;
        }
        else{
            Toast.makeText(this, "Here you can add a plant.", Toast.LENGTH_LONG).show();

        }

    }

    public void yourPlants(View view){
        Toast.makeText(this, "Here you can view you plants.", Toast.LENGTH_LONG).show();
    }
    public void yourAgenda(View view){
        Toast.makeText(this, "This is your agenda, you can see when you have to water your plants.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You already logged in, you can't go back.", Toast.LENGTH_SHORT).show();
    }

    public void goToAgenda(View view){
        Intent intent = new Intent(this, Agenda.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
    private void sendNotification() {
        createNotificationChannel();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.flower)
                .setContentTitle("Plant Care.")
                .setContentText(" Water your plants.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "chn";
            String description = "chn";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
