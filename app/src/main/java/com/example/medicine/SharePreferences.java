package com.example.medicine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {
    Context context;

    public SharePreferences(Context context) {
        this.context = context;
    }

    @SuppressLint("ApplySharedPref")
    public void savePreferences(String key, String value) {
        SharedPreferences p = context.getSharedPreferences("caches", Context.MODE_PRIVATE);
        SharedPreferences.Editor edCaches = p.edit();
        edCaches.putString(key, value);
        edCaches.commit();
    }

    public String loadPreferences(String key) {
        SharedPreferences p = context.getSharedPreferences("caches", Context.MODE_PRIVATE);
        return p.getString(key, null);
    }

    @SuppressLint("ApplySharedPref")
    public void removePreferences(String key) {
        SharedPreferences p = context.getSharedPreferences("caches", Context.MODE_PRIVATE);
        SharedPreferences.Editor edCaches = p.edit();
        edCaches.remove(key);
        edCaches.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void clearPreferences() {
        SharedPreferences p = context.getSharedPreferences("caches", Context.MODE_PRIVATE);
        SharedPreferences.Editor edCaches = p.edit();
        edCaches.clear();
        edCaches.commit();
    }
}
