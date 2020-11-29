package com.example.medicine.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity {
    ActivityDoctorBinding doctorBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctorBinding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(doctorBinding.getRoot());

        doctorBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        doctorBinding.btnDaKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this, DetailDoctorActivity.class);
                intent.putExtra("label", "Đa Khoa");
                startActivity(intent);
            }
        });

        doctorBinding.btnNhaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DoctorActivity.this, DetailDoctorActivity.class);
                intent1.putExtra("label", "Nha Sĩ");
                startActivity(intent1);
            }
        });

        doctorBinding.btnDaLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DoctorActivity.this, DetailDoctorActivity.class);
                intent2.putExtra("label", "Da Liễu");
                startActivity(intent2);
            }
        });

        doctorBinding.btnThuY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(DoctorActivity.this, DetailDoctorActivity.class);
                intent3.putExtra("label", "Thú Y");
                startActivity(intent3);
            }
        });

        doctorBinding.btnTreEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(DoctorActivity.this, DetailDoctorActivity.class);
                intent4.putExtra("label", "Bác Sĩ Nhi");
                startActivity(intent4);
            }
        });

        doctorBinding.btnYTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(DoctorActivity.this, DetailDoctorActivity.class);
                intent5.putExtra("label", "Y Tá");
                startActivity(intent5);
            }
        });
    }


}