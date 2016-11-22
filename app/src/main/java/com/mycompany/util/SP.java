package com.mycompany.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {
    private static final String FILE_NAME = "sp";

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    public SP(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        ed = sp.edit();
    }

    public void set(String key, String value) {
        ed.putString(key, value);
        ed.commit();
    }

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void set(String key, int value) {
        ed.putInt(key, value);
        ed.commit();
    }

    public int get(String key, int defValue) {
        return sp.getInt(key, defValue);
    }
}
