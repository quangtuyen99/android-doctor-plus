package com.example.medicine.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.R;
import com.example.medicine.Reminder.Reminder;
import com.example.medicine.Reminder.ReminderAdapter;
import com.example.medicine.SQLConnect;
import com.example.medicine.User;

import java.sql.Connection;
import java.util.ArrayList;


public class FragmentNotification extends Fragment {
    RecyclerView rvReminder;
    ArrayList<Reminder> reminders;
    User user;
    ReminderAdapter adapter;
    Connection connection = null;
    TextView tvData;
    public FragmentNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        rvReminder = view.findViewById(R.id.rvReminder);
        tvData = view.findViewById(R.id.tvData);


        // Lấy thông tin user
        Intent intent = getActivity().getIntent();
        user = (User) intent.getExtras().getSerializable("user");

        connection = SQLConnect.setConnection();
        reminders = SQLConnect.getReminder(user.taiKhoan);

        if(reminders.size() == 0) {
            tvData.setVisibility(View.VISIBLE);
        } else {
            tvData.setVisibility(View.GONE);
        }

        adapter = new ReminderAdapter(reminders, view.getContext());
        rvReminder.setAdapter(adapter);
        rvReminder.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}