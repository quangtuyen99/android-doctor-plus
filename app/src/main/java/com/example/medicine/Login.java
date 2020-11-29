package com.example.medicine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityLoginBinding;

import java.sql.Connection;

public class Login extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    Connection connection = null;
    SharePreferences p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        p = new SharePreferences(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set up Connection
        connection = SQLConnect.setConnection();

        // Khi bấm vào nút đăng nhập
        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginBinding.txtPassword.getText().toString().equals("") || loginBinding.txtUsername.getText().toString().equals("")) {
                    createDialog("Thiếu thông tin", "Vui lòng nhập đủ thông tin", R.drawable.ic_warning);
                } else {
                    User user = SQLConnect.checkAccount(loginBinding.txtUsername.getText().toString(), loginBinding.txtPassword.getText().toString());

                    if (user != null) {
                        saveLogin(user.taiKhoan, user.matKhau, user.soDienThoai, user.ten, user.thanhPho);
                        Intent intent = new Intent(Login.this, MainScreenActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else {
                        createDialog("Lỗi", "Sai tên tài khoản hoặc mật khẩu", R.drawable.ic_error);
                    }
                }
            }
        });

        // Đăng ký
        loginBinding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    public void createDialog(String title, String message, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }


    @Override
    public void onBackPressed() {

    }

    public void saveLogin(String taikhoan, String matkhau, String sodienthoai, String ten, String thanhpho) {
        p.savePreferences("taikhoan", taikhoan);
        p.savePreferences("matkhau", matkhau);
        p.savePreferences("sodienthoai", sodienthoai);
        p.savePreferences("ten", ten);
        p.savePreferences("thanhpho", thanhpho);

        p.removePreferences("delta");
        p.savePreferences("delta", String.valueOf(System.currentTimeMillis()));
    }



}