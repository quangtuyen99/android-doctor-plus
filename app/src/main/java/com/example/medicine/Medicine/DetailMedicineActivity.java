package com.example.medicine.Medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityDetailMedicineBinding;
import com.squareup.picasso.Picasso;

public class DetailMedicineActivity extends AppCompatActivity {
    ActivityDetailMedicineBinding detailMedicineBinding;
    Medicine medicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailMedicineBinding = ActivityDetailMedicineBinding.inflate(getLayoutInflater());
        setContentView(detailMedicineBinding.getRoot());

        detailMedicineBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getDataMedicine();
    }

    public void getDataMedicine() {
        // Lấy thông tin user
        Intent intent = getIntent();
        medicine = (Medicine) intent.getExtras().getSerializable("medicine");


        Picasso.with(DetailMedicineActivity.this).load(medicine.image).into(detailMedicineBinding.ivLogo);
        detailMedicineBinding.tvName.setText(medicine.name);
        detailMedicineBinding.tvNumber.setText(medicine.numberDK);
        detailMedicineBinding.tvDate.setText(medicine.date);
        detailMedicineBinding.tvAdvantage.setText(medicine.advantage);
        detailMedicineBinding.tvCompanyCC.setText(medicine.companyCC);
        detailMedicineBinding.tvCompanyDK.setText(medicine.companyDK);


    }

}