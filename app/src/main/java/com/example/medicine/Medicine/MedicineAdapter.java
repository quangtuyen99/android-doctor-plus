package com.example.medicine.Medicine;

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

import com.example.medicine.databinding.MedicineItemRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    ArrayList<Medicine> medicines;
    Context context;
    MedicineItemRowBinding itemRowBinding;
    ImageView ivLogo;
    TextView tvName, tvCategory, tvAdvantage;
    LinearLayout layoutMedicine;


    public MedicineAdapter(ArrayList<Medicine> medicines, Context context) {
        this.medicines = medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemRowBinding = MedicineItemRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        final Medicine medicine = medicines.get(position);
        holder.bind(medicine);

        layoutMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMedicineActivity.class);
                intent.putExtra("medicine", medicine);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull MedicineItemRowBinding itemRowBinding1) {
            super(itemRowBinding1.getRoot());
            ivLogo = itemRowBinding1.ivLogo;
            tvName = itemRowBinding1.tvName;

            tvCategory = itemRowBinding1.tvCategory;
            layoutMedicine = itemRowBinding1.layoutMedicine;
            tvAdvantage = itemRowBinding1.tvUses;
        }

        public void bind(final Medicine medicine) {
            Picasso.with(context).load(medicine.image).into(ivLogo);
            tvName.setText(medicine.name);
            tvAdvantage.setText(medicine.advantage);
            tvCategory.setText(medicine.category);
        }
    }
}
