package com.example.medicine.Hospital;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.databinding.HospitalItemRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {
    HospitalItemRowBinding itemRowBinding;
    ArrayList<Hospital> hospitals;
    Context context;
    ImageView ivLogo;
    TextView tvName;
    LinearLayout layoutHospital;
    public HospitalAdapter(ArrayList<Hospital> hospitals, Context context) {
        this.hospitals = hospitals;
        this.context = context;
    }

    @NonNull
    @Override
    public HospitalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemRowBinding = HospitalItemRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.ViewHolder holder, int position) {
        final Hospital hospital = hospitals.get(position);
        holder.bind(hospital);

        layoutHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailHospitalActivity.class);
                intent.putExtra("hospital", hospital);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull HospitalItemRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            context = itemRowBinding.getRoot().getContext();
            ivLogo = itemRowBinding.ivLogo;
            tvName = itemRowBinding.tvName;
            layoutHospital = itemRowBinding.layoutHospital;
        }

        public void bind(final Hospital hospital) {
            Picasso.with(context).load(hospital.hinh).into(ivLogo);
            tvName.setText(hospital.ten);
        }
    }
}
