package com.example.medicine.Medicine;

import java.io.Serializable;

public class Medicine implements Serializable {
    int id;
    String name, companyCC, advantage, date, companyDK, numberDK, image, category;

    public Medicine(int id, String name, String companyCC, String advantage, String date, String companyDK, String numberDK, String image, String category) {
        this.id = id;
        this.name = name;
        this.companyCC = companyCC;
        this.advantage = advantage;
        this.date = date;
        this.companyDK = companyDK;
        this.numberDK = numberDK;
        this.image = image;
        this.category = category;
    }

    public Medicine() {
    }

    public static Medicine setMedicine(int id, String name, String companyCC, String advantage, String date, String companyDK, String numberDK, String image, String category) {
        return new Medicine(id, name, companyCC, advantage, date, companyDK, numberDK, image, category);
    }


}
