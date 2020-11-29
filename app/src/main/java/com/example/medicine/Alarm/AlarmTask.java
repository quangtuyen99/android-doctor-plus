package com.example.medicine.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmTask {

    private Context context;

    public AlarmTask(Context context) {
        this.context = context;
    }


    public void setAlarm(String date, String time1, String message) {
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE); // 1

        String[] values = date.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);

        String[] values1 = time1.split(":");
        int hour = Integer.parseInt(values1[0]);
        int minute = Integer.parseInt(values1[1]);

        Calendar myAlarmDate = Calendar.getInstance();

        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.clear();
        myAlarmDate.set(Calendar.YEAR, year);
        myAlarmDate.set(Calendar.MONTH, month);
        myAlarmDate.set(Calendar.DAY_OF_MONTH, day);
        myAlarmDate.set(Calendar.HOUR_OF_DAY, hour);
        myAlarmDate.set(Calendar.MINUTE, minute);
        myAlarmDate.set(Calendar.SECOND, 0);
        myAlarmDate.set(Calendar.MILLISECOND, 0);


        Intent intent = new Intent(context, AlarmReceiver.class); // 2
        intent.putExtra("MESSAGE", message);
        Log.i("DDDD", myAlarmDate.getTimeInMillis() + "");
        Log.i("DDDD", System.currentTimeMillis() + "");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT); // 3
        alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), alarmIntent); // 4
    }

    public void cancelAlarm(int time) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, time, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent);
    }
}
