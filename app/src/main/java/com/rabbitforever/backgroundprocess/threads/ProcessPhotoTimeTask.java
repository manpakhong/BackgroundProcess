package com.rabbitforever.backgroundprocess.threads;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.rabbitforever.backgroundprocess.R;
import com.rabbitforever.backgroundprocess.activities.MainActivity;
import com.rabbitforever.backgroundprocess.activities.MyAppActivityB;
import com.rabbitforever.backgroundprocess.services.FileManipulationMgr;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.TimerTask;

public class ProcessPhotoTimeTask extends TimerTask {
    private Context ctx;
    private MainActivity mainActivity;
    private final String fileRootPath = "/storage/emulated/0/DCIM";
    public ProcessPhotoTimeTask(Context ctx, MainActivity mainActivity){
        this.ctx = ctx;
        this.mainActivity = mainActivity;
    }
    @Override
    public void run() {
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
                TextView txtView = this.mainActivity.findViewById(R.id.txtMessage);
                txtView.setText("oh! no!");

            }
            Log.i("in timer", "" + isDirectoryExisted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
