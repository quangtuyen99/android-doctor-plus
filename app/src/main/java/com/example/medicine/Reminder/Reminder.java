package com.example.medicine.Reminder;

public class Reminder {
    String name, category, num, date, time, id;
    int idReminder;
    public Reminder(int idReminder, String name, String category, String num, String date, String time, String id) {
        this.idReminder = idReminder;
        this.name = name;
        this.category = category;
        this.num = num;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public Reminder() {
    }
}
