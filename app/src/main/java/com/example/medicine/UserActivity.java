package com.example.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding userBinding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBinding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(userBinding.getRoot());

        // lấy thông tin user từ activity trước
        getInfoUser();

        // Truyền thông tin vào view
        bindDataUser();

        userBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // Update thông tin
        userBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, UpdateActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);

            }
        });
    }

    public void getInfoUser() {
        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");
    }

    public void bindDataUser() {
        userBinding.txtUsername.setText(user.taiKhoan);
        userBinding.txtPassword.setText(user.matKhau);
        userBinding.txtName.setText(user.ten);
        userBinding.txtCity.setText(user.thanhPho);
        userBinding.txtPhoneNumber.setText(user.soDienThoai);
    }
}