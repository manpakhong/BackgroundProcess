package com.rabbitforever.backgroundprocess.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rabbitforever.backgroundprocess.R;
import com.rabbitforever.backgroundprocess.services.ProcessPhotoService;

public class MainActivity extends AppCompatActivity {
    Intent serviceIntent;
    private ProcessPhotoService processPhotoService;
    Context ctx;
    public Context getCtx(){
        return ctx;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        setContentView(R.layout.activity_main);
        processPhotoService = new ProcessPhotoService(getCtx());
        serviceIntent = new Intent(getCtx(), processPhotoService.getClass());
        if (!isMyServiceRunning(processPhotoService.getClass())){
            startService(serviceIntent);
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }
}
