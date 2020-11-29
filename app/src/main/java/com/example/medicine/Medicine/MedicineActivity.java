package com.example.medicine.Medicine;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medicine.SQLConnect;
import com.example.medicine.databinding.ActivityMedicineBinding;

import java.sql.Connection;
import java.util.ArrayList;

public class MedicineActivity extends AppCompatActivity {
    ActivityMedicineBinding medicineBinding;
    ArrayList<Medicine> medicines;
    MedicineAdapter adapter;
    Connection connection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicineBinding = ActivityMedicineBinding.inflate(getLayoutInflater());
        setContentView(medicineBinding.getRoot());

        // Back press
        medicineBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        connection = SQLConnect.setConnection();

        bindDataMedicine();

    }


    // Gọi dữ liệu từ adapter qua
    public void bindDataMedicine() {
        medicines = SQLConnect.getAllMedicine();
        adapter = new MedicineAdapter(medicines, MedicineActivity.this);
        medicineBinding.rvMedicine.setAdapter(adapter);
        medicineBinding.rvMedicine.setLayoutManager(new LinearLayoutManager(this));
    }
}