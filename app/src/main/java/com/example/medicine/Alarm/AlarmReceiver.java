package com.example.medicine.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.medicine.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationTask.createNotification(context, 1,
                R.drawable.ic_medicine, "Tới giờ uống thuốc", intent.getStringExtra("MESSAGE"));
    }
}
