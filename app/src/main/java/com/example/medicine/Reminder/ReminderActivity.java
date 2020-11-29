package com.example.medicine.Reminder;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.Alarm.AlarmTask;
import com.example.medicine.R;
import com.example.medicine.SQLConnect;
import com.example.medicine.User;
import com.example.medicine.databinding.ActivityReminderBinding;

import java.sql.Connection;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    ActivityReminderBinding reminderBinding;
    User user;
    Connection connection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reminderBinding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(reminderBinding.getRoot());

        //Set Spinner
        createSpinner();
        connection = SQLConnect.setConnection();
        reminderBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        showCalendar();
        showClock();

        // Lấy thông tin user
        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");

        reminderBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReminder();
            }
        });
    }


    public void createSpinner() {
        Spinner spinner = reminderBinding.spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // Xử lý việc chọn ngày

    public void showCalendar() {

        reminderBinding.txtDate.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (reminderBinding.txtDate.getRight() - reminderBinding.txtDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        createDatePicker();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void createDatePicker() {
        // Lấy thông tin ngày hiện tại
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo date picker dialog với thông tin ngày hiện tại
        DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, day);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        reminderBinding.txtDate.setText(date);
    }


    // Xử lý việc chọn giờ
    public void showClock() {

        reminderBinding.txtTime.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (reminderBinding.txtTime.getRight() - reminderBinding.txtTime.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        createTimePicker();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void createTimePicker() {
        // Lấy thông tin giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this));
        dialog.show();
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String time;
        if (minute < 10) {
            time = hourOfDay + ":0" + minute;
        } else {
            time = hourOfDay + ":" + minute;
        }

        reminderBinding.txtTime.setText(time);
    }

    public void saveReminder() {
        if(checkInfo()) {
            boolean flag = SQLConnect.saveReminder(reminderBinding.txtName.getText().toString(), reminderBinding.txtConcern.getText().toString(),
                    reminderBinding.spinner.getSelectedItem().toString(), reminderBinding.txtDate.getText().toString(),
                    reminderBinding.txtTime.getText().toString(), user.taiKhoan);

 
            AlarmTask alarmTask = new AlarmTask(this);
            alarmTask.setAlarm(reminderBinding.txtDate.getText().toString(), reminderBinding.txtTime.getText().toString(), reminderBinding.txtName.getText().toString());

            if(flag) {
                createDialogEnd("Thành công", "Tạo lời nhắc thành công", R.drawable.ic_info);
            }
            else {
                createDialog("Lỗi", "Vui lòng kiểm tra lại", R.drawable.ic_error);
            }
        }
    }

    public boolean checkInfo() {
        if(reminderBinding.txtName.toString().equals("") || reminderBinding.txtConcern.toString().equals("") ||
                reminderBinding.txtDate.toString().equals("") || reminderBinding.txtTime.toString().equals("")) {
            createDialog("Thiếu thông tin", "Vui lòng nhập đủ thông tin", R.drawable.ic_warning);
            return false;
        }
        return true;
    }

    public void createDialog(String title, String message, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void createDialogEnd(String title, String message, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}