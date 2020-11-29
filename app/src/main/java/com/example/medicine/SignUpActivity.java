package com.example.medicine;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.medicine.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        // Trở về màn hình đăng nhập
        backPressed();



        signUpBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSignUp();


                if(checkSignUp()) {
                    boolean flag = SQLConnect.createAccount(signUpBinding.txtUsername.getText().toString(), signUpBinding.txtPassword.getText().toString(),
                            signUpBinding.txtPhoneNumber.getText().toString(), signUpBinding.txtName.getText().toString(),
                            signUpBinding.txtCity.getText().toString());

                    if(flag) {
                        createDialogEnd("Thành công", "Bạn đã đăng ký thành công", R.drawable.ic_info);
                    }
                    else {
                        createDialog("Lỗi", "Tài khoản đã tồn tại", R.drawable.ic_error);
                    }

                }
            }
        });
    }


    // Trở về màn hình đăng nhập
    public void backPressed() {
        signUpBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signUpBinding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public boolean checkSignUp() {

        // Điền thiếu thông tin
        if(signUpBinding.txtUsername.getText().toString().trim().equals("") ||
                signUpBinding.txtPassword.getText().toString().trim().equals("") ||
                signUpBinding.txtPhoneNumber.getText().toString().trim().equals("") ||
                signUpBinding.txtRePassWord.getText().toString().trim().equals("") || signUpBinding.txtName.getText().toString().trim().equals("")
                || signUpBinding.txtCity.getText().toString().trim().equals("")) {
            createDialog("Thiếu thông tin", "Vui lòng kiểm tra lại thông tin!", R.drawable.ic_warning);
            return false;
        }

        // Xác nhận mật khẩu sai
        if( !signUpBinding.txtRePassWord.getText().toString().trim().equals(signUpBinding.txtPassword.getText().toString().trim()) ) {
            createDialog("Sai thông tin", "Mật khẩu xác nhận và mật khẩu không trùng khớp", R.drawable.ic_warning);
            return false;
        }

        return true;
    }

    public void createDialog(String title, String message, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

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

    public void createDialogEnd(String title, String message, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}