package com.example.medicine;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.medicine.Fragment.FragmentHome;
import com.example.medicine.Fragment.FragmentMap;
import com.example.medicine.Fragment.FragmentNotification;
import com.example.medicine.databinding.ActivityMainScreenBinding;
import com.example.medicine.databinding.NavigationHeaderBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainScreenActivity extends AppCompatActivity {
    ActivityMainScreenBinding mainScreenBinding;
    ActionBarDrawerToggle hamburger;
    Toolbar toolbar;
    User user;
    NavigationHeaderBinding navigationHeaderBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainScreenBinding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(mainScreenBinding.getRoot());

        setUpTab();
        setUpToolBar();
        setUpNavigation();

        // Lấy thông tin user
        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");

        // Set thông tin user cho Navigation
        setUpNavigationInfo();
    }

    public void setUpNavigationInfo() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.leftView);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tvNameHeader);
        navUsername.setText(user.ten);

        TextView navCity = (TextView) headerView.findViewById(R.id.tvCityHeader);
        navCity.setText(user.thanhPho);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add thanh menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void setUpTab() {
        // Truyền dữ liệu vào các viewPager
        addTabs(mainScreenBinding.viewPager);
        // Đồng bộ tabLayout và viewPager
        ((TabLayout)mainScreenBinding.tabLayout).setupWithViewPager(mainScreenBinding.viewPager);
        tabLayoutListener();
        // Thêm icon cho các tab
        addIcon();
    }

    public void setUpToolBar() {
        //Toolbar
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // Tạo button home trong thanh toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }


    public void setUpNavigation() {
        // Khởi tạo Navigation Drawer
        setUpNavigationDrawer();

        // Xử lý khi chọn các item trong drawer
        setupDrawerContext(mainScreenBinding.leftView);
    }

    // Xử lý thao tác khi bấm vào nút menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_notification) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Add các tab vào trong tab layout
    public void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new FragmentHome());
        adapter.add(new FragmentMap());
        adapter.add(new FragmentNotification());


        viewPager.setAdapter(adapter);
    }

    public void addIcon() {
        int[] images = {R.drawable.ic_home, R.drawable.ic_map, R.drawable.ic_notification};

        for(int i=0; i <= 2; i++) {
            mainScreenBinding.tabLayout.getTabAt(i).setIcon(images[i]);
        }
        // Set màu khởi tạo cho các icon
        mainScreenBinding.tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorTabSelected), PorterDuff.Mode.SRC_IN);
        mainScreenBinding.tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN);
        mainScreenBinding.tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN);


    }

    public void tabLayoutListener() {
        final String[] titles = {"Trang chủ", "Bản đồ", "Thông báo"};

        mainScreenBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mainScreenBinding.tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(R.color.colorTabSelected), PorterDuff.Mode.SRC_IN);
                tab.setText(titles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mainScreenBinding.tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN);
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setUpNavigationDrawer() {
        hamburger = new ActionBarDrawerToggle(this, mainScreenBinding.layoutDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mainScreenBinding.layoutDrawer.addDrawerListener(hamburger);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hamburger.syncState();
    }

    // Set up menu drawer
    public void setupDrawerContext(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }


    public void selectDrawerItem(MenuItem menuItem) {
//        Fragment fragment = null;
//        Class fragmentClass = null;

        switch (menuItem.getItemId()) {
            case R.id.itemUser: // Thông tin tài khoản
                Intent intent = new Intent(this, UserActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.itemInfo: // Thông tin ứng dụng
                Intent intent2 = new Intent(this, InfoActivity.class);
                startActivity(intent2);
                break;
            case R.id.itemLogOut: // Đăng xuất
                Intent intent1 = new Intent(this, Login.class);
                startActivity(intent1);
                SharePreferences p = new SharePreferences(this);
                p.clearPreferences();
                break;
        }

//
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//        menuItem.setChecked(true); // Highlight item được chọn
//        mainScreenBinding.layoutDrawer.closeDrawers(); // Đóng left View
    }


    @Override
    public void onBackPressed() {

    }
}