package com.rabbitforever.backgroundprocess.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rabbitforever.backgroundprocess.R;
import com.rabbitforever.backgroundprocess.activities.MainActivity;
import com.rabbitforever.backgroundprocess.activities.MyAppActivityB;
import com.rabbitforever.backgroundprocess.threads.ProcessPhotoTimeTask;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class ProcessPhotoService extends Service {
    public int counter=0;
    private Context ctx;
    private MainActivity mainActivity;

    public ProcessPhotoService(Context applicationContext, MainActivity mainActivity) {
        super();
        ctx = applicationContext;
        this.mainActivity = mainActivity;
        startTimer();
        Log.i("HERE", "here I am!");
    }
    public ProcessPhotoService(){

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
//        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.rabbitforever.backgroundprocess.services.RestartProcessPhotoService");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private ProcessPhotoTimeTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 10000, 10000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
//        final String fileRootPath = "/storage/emulated/0/DCIM/UCam/USpyCam";
        final String fileRootPath = "/storage/emulated/0/DCIM/UCam/USpyCam";
//        timerTask = new TimerTask() {
//            public void run() {
//                FileManipulationMgr fileManipulationMgr = null;
//                try {
//                    fileManipulationMgr = new FileManipulationMgr();
//                    ctx = MyAppActivityB.getContext();
//                    FileUtils fileUtils;
//                    ctx.getPackageManager();
//                    fileUtils = FileUtils.getInstance();
//                    String currentDir =  fileUtils.getCurrentDirectory(ctx);
//                    boolean isDirectoryExisted = fileUtils.isDirectoryExisted(fileRootPath);
//                    if (isDirectoryExisted){
//                        List<File> fileList = fileManipulationMgr.getFileListRecursively(fileRootPath);
//                        TextView txtView = mainActivity.findViewById(R.id.txtMessage);
//                        txtView.setText("oh! no!");
//                    }
//                    Log.i("in timer", "in timer ++++  "+ (counter++) + ", currentDir: " + currentDir + ",isDirExisted:" + isDirectoryExisted);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
        timerTask = new ProcessPhotoTimeTask(ctx, mainActivity);
        timerTask.run();;
    }



    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
