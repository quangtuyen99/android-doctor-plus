package com.example.medicine.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.medicine.Main.MainActivity;
import com.example.medicine.MainScreenActivity;
import com.example.medicine.R;
import com.example.medicine.SharePreferences;
import com.example.medicine.User;

public class NotificationTask {
    public static String CHANNEL_ID = "MY_TODO_LIST";
    public static String CHANNEL_NAME = "Reminder";
    public static int REQUEST_CODE = 0;
    Context context;


    public NotificationTask(Context context) {
        this.context = context;
    }
    public static void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, importance);
            channel.setDescription("ToDo List");
            channel.setShowBadge(true);
            NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    public static void createNotification(Context context, int notificationId, int
            icon, String title, String message) {
        NotificationCompat.Builder builder;
        Intent intent;
        SharePreferences p = new SharePreferences(context);

        long timeNew = System.currentTimeMillis() - Long.parseLong(p.loadPreferences("delta"));
        int timeDelta = R.integer.time;
        if(timeNew < timeDelta) {
            intent = new Intent(context, MainScreenActivity.class);
            p.removePreferences("delta");
            p.savePreferences("delta", String.valueOf(System.currentTimeMillis()));

            String taikhoan, matkhau, sodienthoai, ten, thanhpho;

            taikhoan = p.loadPreferences("taikhoan");
            matkhau = p.loadPreferences("matkhau");
            sodienthoai = p.loadPreferences("sodienthoai");
            ten = p.loadPreferences("ten");
            thanhpho = p.loadPreferences("thanhpho");


            User user = new User(taikhoan, matkhau, sodienthoai, ten, thanhpho);
            intent.putExtra("user", user);
        } else {
            intent = new Intent(context, MainActivity.class);
        }



        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE,
                intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_alarm, "Hoàn thành", pendingIntent);

        } else {
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_alarm, "Hoàn thành", pendingIntent);
        }
        NotificationManager mNotificationManager =
                (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, builder.build());
    }
}

