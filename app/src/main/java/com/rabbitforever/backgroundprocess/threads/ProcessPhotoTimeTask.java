package com.rabbitforever.backgroundprocess.threads;


import android.content.Context;
import android.util.Log;

import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.util.TimerTask;

public class ProcessPhotoTimeTask extends TimerTask {
    public Context ctx;
    public ProcessPhotoTimeTask(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public void run() {
//        try {
//            FileUtils fileUtils;
//            ctx.getPackageManager();
//            fileUtils = FileUtils.getInstance();
//            String currentDir =  fileUtils.getCurrentDirectory(ctx);
//            Log.i("in timer", "in timer ++++  "+ (counter++) + ", currentDir: " + currentDir);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
