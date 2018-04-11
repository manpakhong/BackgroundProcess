package com.rabbitforever.backgroundprocess.activities;

import android.app.Application;
import android.content.Context;

public class MyAppActivityB extends Application {
    private static MyAppActivityB instance;
    public static MyAppActivityB getInstance(){
        return instance;
    }
    public static Context getContext(){
        return instance;
    }
    @Override
    public void onCreate(){
        instance = this;
        super.onCreate();
    }
}
