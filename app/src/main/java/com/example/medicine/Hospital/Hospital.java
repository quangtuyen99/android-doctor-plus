package com.example.medicine.Hospital;

import java.io.Serializable;

public class Hospital implements Serializable {
    public int id;
    public String ten, diachi, sodienthoai, email, hinh, vido, kinhdo;

    public Hospital(int id, String ten, String diachi, String sodienthoai, String email, String hinh, String vido, String kinhdo) {
        this.id = id;
        this.ten = ten;
        this.diachi = diachi;
        this.sodienthoai = sodienthoai;
        this.email = email;
        this.hinh = hinh;
        this.vido = vido;
        this.kinhdo = kinhdo;
    }

    public Hospital() {
    }
}
