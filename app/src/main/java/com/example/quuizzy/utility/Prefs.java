package com.example.quuizzy.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;
    public  Prefs(Activity activity)
    {
        this.preferences=activity.getPreferences(Context.MODE_PRIVATE);

    }

    public void saveHighScore(int score){
        int lastScore= preferences.getInt("High Score",0);
        if (score>lastScore){
            preferences.edit().putInt("High Score",score).apply();
        }
    }
    public  int getHighSCore(){
        return preferences.getInt("High Score",0);

    }
    public void setState(int index){
        preferences.edit().putInt("state",index).apply();

    }
    public int getState(){
        return preferences.getInt("state",0);
    }
}
