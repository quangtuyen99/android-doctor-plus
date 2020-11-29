package com.example.medicine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.databinding.ActivityUpdateBinding;

import java.sql.Connection;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding updateBinding;
    User user, user1;
    Connection connection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateBinding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(updateBinding.getRoot());

        // Lấy thông tin user
        getInfoUser();

        // Truyền vào view
        bindDataUser();

        connection = SQLConnect.setConnection();

        updateBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        final SharePreferences p = new SharePreferences(this);

        updateBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUpdate();

                if(checkUpdate()) {
                    user1 = new User(user.taiKhoan, updateBinding.txtPhoneNumber.getText().toString(), updateBinding.txtPassword.getText().toString(),
                            updateBinding.txtName.getText().toString(), updateBinding.txtCity.getText().toString());

                    SQLConnect.updateUser(user.taiKhoan, user1.soDienThoai, user1.matKhau, user1.ten, user1.thanhPho);
                    p.savePreferences("matkhau", user1.matKhau);
                    p.savePreferences("sodienthoai", user1.soDienThoai);
                    p.savePreferences("ten", user1.ten);
                    p.savePreferences("thanhpho", user1.thanhPho);
                    createDialog("Cập nhập thành công", "", R.drawable.ic_info);
                }

            }
        });
    }


    public void getInfoUser() {
        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");
    }

    public void bindDataUser() {
        updateBinding.txtName.setText(user.ten);
        updateBinding.txtCity.setText(user.thanhPho);
        updateBinding.txtPhoneNumber.setText(user.soDienThoai);
    }

    public void createDialog(String title, String message, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(UpdateActivity.this, MainScreenActivity.class);
                intent.putExtra("user", user1);
                startActivity(intent);

                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public boolean checkUpdate() {
        // Điền thiếu thông tin
        if( updateBinding.txtPassword.getText().toString().trim().equals("") ||
                updateBinding.txtPhoneNumber.getText().toString().trim().equals("") ||
                updateBinding.txtRePassword.getText().toString().trim().equals("") || updateBinding.txtName.getText().toString().trim().equals("")
                || updateBinding.txtCity.getText().toString().trim().equals("")) {
            createDialog("Thiếu thông tin", "Vui lòng kiểm tra lại thông tin!", R.drawable.ic_warning);
            return false;
        }

        // Xác nhận mật khẩu sai
        if( !updateBinding.txtRePassword.getText().toString().trim().equals(updateBinding.txtPassword.getText().toString().trim()) ) {
            createDialog("Sai thông tin", "Mật khẩu xác nhận và mật khẩu không trùng khớp", R.drawable.ic_warning);
            return false;
        }

        return true;
    }
}