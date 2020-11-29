package com.example.medicine;

import java.io.Serializable;

public class User implements Serializable {
    public String taiKhoan, matKhau, soDienThoai, ten, thanhPho;

    public User(String taiKhoan, String matKhau, String soDienThoai, String ten, String thanhPho) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.ten = ten;
        this.thanhPho = thanhPho;
    }

    public User() {
    }
}
