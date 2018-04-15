package com.rabbitforever.backgroundprocess.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        processPhotoService = new ProcessPhotoService(getCtx(), this);
        serviceIntent = new Intent(getCtx(), processPhotoService.getClass());
        checkAndRequestRuntimePermission();
        if (!isMyServiceRunning(processPhotoService.getClass())){
            startService(serviceIntent);
        }
        TextView txtView = findViewById(R.id.txtMessage);
        txtView.setText("waiting for file list......");

    }

    private final int READ_EXTERNAL_STORAGE = 1;
    private void checkAndRequestRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE);
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
