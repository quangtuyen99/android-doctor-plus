package com.example.medicine.Hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityDetailHospitalBinding;
import com.squareup.picasso.Picasso;

public class DetailHospitalActivity extends AppCompatActivity {
    ActivityDetailHospitalBinding detailHospitalBinding;
    Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailHospitalBinding = ActivityDetailHospitalBinding.inflate(getLayoutInflater());
        setContentView(detailHospitalBinding.getRoot());

        detailHospitalBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getDataHospital();

        bindData();
    }

    public void getDataHospital() {
        Intent intent = getIntent();
        hospital = (Hospital) intent.getExtras().getSerializable("hospital");
    }

    public void bindData() {
        Picasso.with(this).load(hospital.hinh).into(detailHospitalBinding.ivLogo);
        detailHospitalBinding.tvName.setText(hospital.ten);
        detailHospitalBinding.tvAddress.setText(hospital.diachi);
        detailHospitalBinding.tvNumber.setText(hospital.sodienthoai);
        detailHospitalBinding.tvEmail.setText(hospital.email);
    }

}