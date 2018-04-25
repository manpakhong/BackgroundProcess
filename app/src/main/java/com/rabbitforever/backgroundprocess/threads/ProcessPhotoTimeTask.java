package com.rabbitforever.backgroundprocess.threads;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.rabbitforever.backgroundprocess.R;
import com.rabbitforever.backgroundprocess.activities.MainActivity;
import com.rabbitforever.backgroundprocess.activities.MyAppActivityB;
import com.rabbitforever.backgroundprocess.services.FileManipulationMgr;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProcessPhotoTimeTask extends TimerTask {
    private Context ctx;
    MainActivity mainActivity;
    private final String fileRootPath = "/storage/emulated/0/DCIM";
    private Handler handler;
    private Timer timer=new Timer();//Used for a delay to provide user feedback

    public ProcessPhotoTimeTask(Context ctx, MainActivity mainActivity){
        this.ctx = ctx;
        this.mainActivity = mainActivity;
        handler = new Handler(callback);
        int seconds = 3;
        timer.schedule(new SmallDelay(handler), seconds*1000);
    }
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            FileManipulationMgr fileManipulationMgr = null;
            try {


            fileManipulationMgr = new FileManipulationMgr();
            ctx = MyAppActivityB.getContext();
            FileUtils fileUtils;
            ctx.getPackageManager();
            fileUtils = FileUtils.getInstance();
            String currentDir =  fileUtils.getCurrentDirectory(ctx);
            boolean isDirectoryExisted = fileUtils.isDirectoryExisted(fileRootPath);
            if (isDirectoryExisted){
                List<File> fileList = fileManipulationMgr.getFileListRecursively(fileRootPath);
                TextView txtView = mainActivity.findViewById(R.id.txtMessage);
                String fileString = "";
                for (int i = 0; i < fileList.size(); i++) {
                    File file = fileList.get(i);
                    if (i > 0){
                        fileString += "\n";
                    }
                    fileString += file.getAbsolutePath();
                }
                txtView.setText(fileString);
            }
                Log.i("in timer", "" + isDirectoryExisted);
            } catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
    };


    @Override
    public void run() {
        FileManipulationMgr fileManipulationMgr = null;
        try {
//            fileManipulationMgr = new FileManipulationMgr();
//            ctx = MyAppActivityB.getContext();
//            FileUtils fileUtils;
//            ctx.getPackageManager();
//            fileUtils = FileUtils.getInstance();
//            String currentDir =  fileUtils.getCurrentDirectory(ctx);
//            boolean isDirectoryExisted = fileUtils.isDirectoryExisted(fileRootPath);
//            if (isDirectoryExisted){
//                List<File> fileList = fileManipulationMgr.getFileListRecursively(fileRootPath);
//                TextView txtView = this.mainActivity.findViewById(R.id.txtMessage);
//                txtView.setText("oh! no!");
//
//            }
//            Log.i("in timer", "" + isDirectoryExisted);
            int seconds = 1;
            timer.schedule(new SmallDelay(handler), seconds*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
