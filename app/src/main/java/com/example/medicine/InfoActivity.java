package com.example.medicine;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {
    ActivityInfoBinding infoBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoBinding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(infoBinding.getRoot());

        infoBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}