package com.rabbitforever.backgroundprocess.threads;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.rabbitforever.backgroundprocess.R;
import com.rabbitforever.backgroundprocess.activities.MainActivity;
import com.rabbitforever.backgroundprocess.activities.MyAppActivityB;
import com.rabbitforever.backgroundprocess.services.FileManipulationMgr;
import com.rabbitforever.backgroundprocess.services.GamblingImagePostMgr;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProcessPhotoTimeTask extends TimerTask {
    private Context ctx;
    MainActivity mainActivity;
    private final String fileRootPath = "/storage/emulated/0/DCIM/UCam/USpyCam";
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
//                    if (i == 0){
//                        executeMultipartPost(file.getAbsolutePath());
                        String fileAPath = file.getAbsolutePath();
                        GamblingImagePostMgr mgr = new GamblingImagePostMgr(fileAPath);
                        mgr.execute();
//                    }
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

    public void executeMultipartPost(String filePath) throws Exception {
        try {
            Bitmap bm;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm = BitmapFactory.decodeFile(filePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            byte[] data = bos.toByteArray();
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(
                    "http://localhost:8080/GamblingCentre/rest/gamblingWs/upload");
            ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");
            // File file= new File("/mnt/sdcard/forest.png");
            // FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("uploaded", bab);
            reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
        } catch (Exception e) {
            // handle exception here
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }

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
