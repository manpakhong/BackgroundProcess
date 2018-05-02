package com.rabbitforever.backgroundprocess.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

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
import java.io.InputStreamReader;

public class GamblingImagePostMgr extends AsyncTask<Void,Void,Void> {
    private FileUtils fileUtils;
    private String fileToBePosted;
    private final String uri = "http://192.168.1.132:8080/GamblingCentre/rest/gamblingWs/upload";
    public GamblingImagePostMgr(String fileToBePosted) throws Exception{
        this.fileToBePosted = fileToBePosted;
        init();
    }
    private void init() throws Exception {
        try {
            fileUtils = FileUtils.getInstance();

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".init()", e);
            throw e;
        }
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            executeMultipartPost(this.fileToBePosted);
        } catch (Exception e){
            Log.e(this.getClass().getSimpleName(),
                    ".doInBackground()", e);
        }
        return null;
    }
    public void executeMultipartPost(String filePath) throws Exception {
        try {
            Bitmap bm;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm = BitmapFactory.decodeFile(filePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            byte[] data = bos.toByteArray();
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(
                    "http://192.168.1.132:8080/GamblingCentre/rest/gamblingWs/upload");
            // storage/emulated/0/DCIM/UCam/USpyCam/USpyCam_20180211_144735/IMG_20180211_144741.jpg
            String fileName = fileUtils.getFileNameFromAbsolutePath(filePath);

            ByteArrayBody bab = new ByteArrayBody(data, fileName);
            // File file= new File("/mnt/sdcard/forest.png");
            // FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", bab);
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
}
