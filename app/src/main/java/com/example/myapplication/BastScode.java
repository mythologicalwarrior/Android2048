package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class BastScode {

    private SharedPreferences s;
    BastScode(Context context){
        s = context.getSharedPreferences("bestscode", context.MODE_PRIVATE);
    }

    public int getBestScode(){
        int bestscode = s.getInt("bestscode", 0);
        return bestscode;
    }

    public void setBestScode(int bestScode){
        SharedPreferences.Editor editor = s.edit();
        editor.putInt("bestscode", bestScode);
        editor.commit();
    }
}
