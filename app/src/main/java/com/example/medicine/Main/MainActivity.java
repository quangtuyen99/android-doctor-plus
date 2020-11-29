package com.example.medicine.Main;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.Login;
import com.example.medicine.MainScreenActivity;
import com.example.medicine.R;
import com.example.medicine.SharePreferences;
import com.example.medicine.User;
import com.example.medicine.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SharePreferences p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        p = new SharePreferences(this);
        checkLogin();
    }


    // Dùng animator khi bắt đầu ứng dụng
    public void animatorLoading() {
        // Animator của logo và name
        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
        anim.setTarget(binding.ivLogo);
        anim.start();

        Animator anim1 = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
        anim1.setTarget(binding.tvName);
        anim1.start();

        // Chay animator
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(binding.ivLogo, "logo_image");
                pairs[1] = new Pair<View, String>(binding.tvName, "logo_text");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());

                }
            }
        }, 2000);
    }

    public void checkLogin() {
        long time;
        if(p.loadPreferences("delta") != null) {
           time = Long.parseLong(p.loadPreferences("delta"));
        } else {
            time = 0;
        }
        int timeLast = R.integer.time;
        long delta = System.currentTimeMillis() - time;
        if((System.currentTimeMillis() - time) < timeLast) {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
            anim.setTarget(binding.ivLogo);
            anim.start();

            Animator anim1 = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
            anim1.setTarget(binding.tvName);
            anim1.start();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                    String taikhoan = p.loadPreferences("taikhoan");
                    String matkhau = p.loadPreferences("matkhau");
                    String sodienthoai = p.loadPreferences("sodienthoai");
                    String ten = p.loadPreferences("ten");
                    String thanhpho = p.loadPreferences("thanhpho");



                    User user = new User(taikhoan, matkhau, sodienthoai, ten, thanhpho);

                    intent.putExtra("user", user);
                    p.savePreferences("delta", String.valueOf(System.currentTimeMillis()));
                    startActivity(intent);

                }
            }, 2000);

        } else {
            animatorLoading();
            p.clearPreferences();
        }

    }

    @Override
    public void onBackPressed() {

    }
}