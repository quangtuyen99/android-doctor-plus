package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    // Khởi tạo list Fragment
    private final List<Fragment> fragmentList = new ArrayList<>();
    // Khởi tạo list Title


    private final List<String> titleFrm = new ArrayList<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titleFrm.get(position);
//    }

    public void add(Fragment fm) {
        fragmentList.add(fm);

    }
}
