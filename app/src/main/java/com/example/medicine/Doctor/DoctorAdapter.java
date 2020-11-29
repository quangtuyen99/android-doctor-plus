package com.example.medicine.Doctor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.databinding.DoctorItemRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {
    DoctorItemRowBinding itemRowBinding;
    ArrayList<Doctor> doctors;
    Context context;
    ImageView ivDoctor;
    TextView tvName, tvDiaChi, tvChucVu, tvKinhNghiem, tvBook;
    private static final int PHONE_CALL_PERMISSION_REQUEST = 101;
    String phone;


    public DoctorAdapter(ArrayList<Doctor> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemRowBinding = DoctorItemRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
        final Doctor doctor = doctors.get(position);
        holder.bind(doctor);

        itemRowBinding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = doctor.sodienthoai;
                makePhoneCall(phone);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makePhoneCall(phone);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull DoctorItemRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            ivDoctor = itemRowBinding.ivDoctor;
            tvName = itemRowBinding.tvName;
            tvBook = itemRowBinding.tvBook;
            tvChucVu = itemRowBinding.tvChucVu;
            tvDiaChi = itemRowBinding.tvDiaChi;
            tvKinhNghiem = itemRowBinding.tvKinhNghiem;
            context = itemRowBinding.getRoot().getContext();
        }

        public void bind(final Doctor doctor) {
            Picasso.with(context).load(doctor.hinh).into(ivDoctor);
            tvName.setText(doctor.ten);
            tvBook.setText(doctor.book);
            tvChucVu.setText(doctor.chucvu);
            tvDiaChi.setText(doctor.diachi);
            tvKinhNghiem.setText(doctor.kinhnghiem);
        }
    }


    private void makePhoneCall(String phone) {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CALL_PHONE}, PHONE_CALL_PERMISSION_REQUEST);
                return;
            }
        }

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        if(callIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(callIntent);
        }
    }


}
