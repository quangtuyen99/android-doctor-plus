package com.example.medicine.Category;

import com.example.medicine.R;

import java.util.ArrayList;

public class Category {
    String name;
    int image;

    public Category() {
    }

    public Category(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public static ArrayList<Category> getMockData() {
        ArrayList<Category> categories = new ArrayList<>();

        String[] names = {"Thuốc", "Bác sĩ", "Ghi chú", "Thông tin bệnh viện"};
        int[] images = {R.drawable.pharmacy, R.drawable.doctor, R.drawable.reminhder, R.drawable.hospital};


        for(int i=0; i < names.length; i++) {
            Category category = new Category(names[i], images[i]);
            categories.add(category);
        }


        return categories;
    }
}
