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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Message;
import com.rabbitforever.backgroundprocess.R;
import com.rabbitforever.backgroundprocess.services.GamblingWebServiceMgr;
import com.rabbitforever.backgroundprocess.services.ProcessPhotoService;
import com.rabbitforever.backgroundprocess.threads.SmallDelay;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Intent serviceIntent;
    private ProcessPhotoService processPhotoService;
    Context ctx;
    Handler handler;
    Timer timer=new Timer();//Used for a delay to provide user feedback

     EditText edNumberInput;
     TextView tvPrimeResult;
    long lngNum;

    public void CheckPrimeClick(View arg0) {
        String number = edNumberInput.getText().toString();
        tvPrimeResult.setText("Checking please wait.");
        try {
            lngNum=Long.parseLong(number);
        }catch(Exception ex){
            tvPrimeResult.setText("Error " + ex.getMessage() + " testing " + number);
            return;
        }
        timer.schedule(new SmallDelay(handler), 100);
    }
    public void PostToGamblingWebServiceClick(View arg0){
        try {
            GamblingWebServiceMgr mgr = new GamblingWebServiceMgr();
            mgr.execute();

        }catch(Exception e){
            Log.e(this.getClass().getSimpleName(),
                    ".PostToGamblingWebServiceClick()", e);
            return;
        }
    }
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
        handler = new Handler(callback);
        int seconds = 3;
        timer.schedule(new SmallDelay(handler), seconds*1000);

        this.edNumberInput = findViewById(R.id.edNumberInput);
        this.tvPrimeResult = findViewById(R.id.tvPrimeResult);
    }
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if(isPrime(lngNum)){
                tvPrimeResult.setText(edNumberInput.getText() + " IS a prime.");
            }else{
                tvPrimeResult.setText(edNumberInput.getText() + " IS NOT a prime.");
            }
            return true;
        }
    };
    /* Test for prime numbers
     * http://primes.utm.edu/curios/includes/primetest.php
     * also look at Java BigInteger isProbablePrime
     */
    public static boolean isPrime(long N) {
        // Trial divide the positive integer N by the primes from 2
        // Returns true if a prime divisor found, or false if none found
        if (N%2 == 0) return false;//Eliminates evens
        if (N%3 == 0) return false;//Eliminates multiples of three
        // No need to go past the square root of our number (see Sieve of Eratosthenes)
        long Stop = (long) Math.sqrt(N);
        // Okay, lets "wheel factor" alternately adding 2 and 4
        long di=2;
        for(long i=5; i<=Stop; i+=di, di=6-di) {
            if (N%i == 0) return false;
        };
        return true;
    }
//    class SmallDelay extends TimerTask {
//        @Override
//        public void run() {
//            handler.sendEmptyMessage(0);
//        }
//    }
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
