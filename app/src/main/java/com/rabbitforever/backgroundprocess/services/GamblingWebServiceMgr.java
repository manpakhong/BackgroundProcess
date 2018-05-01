package com.rabbitforever.backgroundprocess.services;

import android.os.AsyncTask;
import android.util.Log;

import com.rabbitforever.backgroundprocess.helpers.FileManipulationMgrHelper;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GamblingWebServiceMgr extends AsyncTask<Void,Void,Void> {
    private FileUtils fileUtils;
    private final String uri = "http://192.168.1.132:8080/GamblingCentre/rest/gamblingWs/test";
    public GamblingWebServiceMgr() throws Exception{
        init();
    }

    @Override
    protected Void doInBackground(Void... voids)  {
        try {
            testConnection();
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".init()", e);

        }
        return null;
    }
    protected void onPreExecute() {
        //display progress dialog.

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
    private final String USER_AGENT = "Mozilla/5.0";
    public boolean testConnection() throws Exception {
        boolean isConnectionOk = false;
        try {
            URL obj = new URL(uri);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + uri);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".init()", e);
            throw e;
        }
        return isConnectionOk;
    }
}
