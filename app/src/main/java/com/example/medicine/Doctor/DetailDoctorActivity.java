package com.example.medicine.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medicine.SQLConnect;
import com.example.medicine.databinding.ActivityDetailDoctorBinding;

import java.sql.Connection;
import java.util.ArrayList;

public class DetailDoctorActivity extends AppCompatActivity {
    ActivityDetailDoctorBinding detailDoctorBinding;
    Connection connection = null;
    ArrayList<Doctor> doctors;
    DoctorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailDoctorBinding = ActivityDetailDoctorBinding.inflate(getLayoutInflater());
        setContentView(detailDoctorBinding.getRoot());

        detailDoctorBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String label = intent.getStringExtra("label");

        detailDoctorBinding.tvToolbar.setText(label);

        connection = SQLConnect.setConnection();

        doctors = SQLConnect.getAllDoctor(label);
        adapter = new DoctorAdapter(doctors, this);
        detailDoctorBinding.rvDoctor.setAdapter(adapter);
        detailDoctorBinding.rvDoctor.setLayoutManager(new LinearLayoutManager(this));

    }


}