package com.example.medicine.Category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.Doctor.DoctorActivity;
import com.example.medicine.Hospital.HospitalActivity;
import com.example.medicine.Medicine.MedicineActivity;
import com.example.medicine.Reminder.ReminderActivity;
import com.example.medicine.User;
import com.example.medicine.databinding.CategoryItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<Category> categories;
    Context context;
    CategoryItemBinding itemBinding;
    User user;
    public CategoryAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        itemBinding =
                CategoryItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, final int position) {
        // Lấy thông tin user
        Intent intent = ((Activity) context).getIntent();
        user = (User) intent.getExtras().getSerializable("user");


        Category category = categories.get(position);
        itemBinding.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(context, MedicineActivity.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(context, DoctorActivity.class);
                        context.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context, ReminderActivity.class);
                        intent2.putExtra("user", user);
                        context.startActivity(intent2);
                        break;

                    case 3:
                        Intent intent3 = new Intent(context, HospitalActivity.class);
                        context.startActivity(intent3);
                        break;
                    default:
                        break;
                }
            }
        });
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryLogo;
        TextView tvCategoryName;

        public ViewHolder(@NonNull CategoryItemBinding binding) {
            super(binding.getRoot());
            ivCategoryLogo = binding.ivCategoryLogo;
            tvCategoryName = binding.tvCategoryName;
            context = binding.getRoot().getContext();
        }


        public void bind(final Category category) {
            ivCategoryLogo.setImageResource(category.image);
            tvCategoryName.setText(category.name);
        }
    }
}
