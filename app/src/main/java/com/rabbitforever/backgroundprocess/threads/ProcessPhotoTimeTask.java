package com.rabbitforever.backgroundprocess.threads;


import android.content.Context;
import android.util.Log;

import com.rabbitforever.backgroundprocess.activities.MyAppActivityB;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.util.TimerTask;

public class ProcessPhotoTimeTask extends TimerTask {

    public ProcessPhotoTimeTask(){

    }
    @Override
    public void run() {
        Context ctx = null;
        try {
            ctx = MyAppActivityB.getContext();
            FileUtils fileUtils;
            ctx.getPackageManager();
            fileUtils = FileUtils.getInstance();
            String currentDir =  fileUtils.getCurrentDirectory(ctx);
//            Log.i("in timer", "in timer ++++  "+ (counter++) + ", currentDir: " + currentDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
