package com.example.medicine.Hospital;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medicine.SQLConnect;
import com.example.medicine.databinding.ActivityHospitalBinding;

import java.sql.Connection;
import java.util.ArrayList;

public class HospitalActivity extends AppCompatActivity {
    ActivityHospitalBinding hospitalBinding;
    HospitalAdapter adapter;
    ArrayList<Hospital> hospitals;
    Connection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hospitalBinding = ActivityHospitalBinding.inflate(getLayoutInflater());
        setContentView(hospitalBinding.getRoot());

        hospitalBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        connection = SQLConnect.setConnection();

        hospitals = SQLConnect.getAllHospital();
        adapter = new HospitalAdapter(hospitals, this);
        hospitalBinding.rvHospital.setAdapter(adapter);
        hospitalBinding.rvHospital.setLayoutManager(new LinearLayoutManager(this));
    }
}