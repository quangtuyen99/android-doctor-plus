package com.example.medicine.Doctor;

import java.io.Serializable;

public class Doctor implements Serializable {
    public int id;
    public String ten, chucvu, kinhnghiem, hinh, book, sodienthoai, diachi;

    public Doctor(int id, String ten, String chucvu, String kinhnghiem, String hinh, String book, String sodienthoai, String diachi) {
        this.id = id;
        this.ten = ten;
        this.chucvu = chucvu;
        this.kinhnghiem = kinhnghiem;
        this.hinh = hinh;
        this.book = book;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
    }

    public Doctor() {
    }
}
