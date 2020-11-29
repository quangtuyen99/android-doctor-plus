package com.example.medicine;

import android.app.Application;

import com.example.medicine.Alarm.NotificationTask;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationTask.createNotificationChannel(this);
    }
}
