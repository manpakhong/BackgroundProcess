package com.rabbitforever.backgroundprocess.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class FileUtils {
    private static FileUtils fileUtils;
    private FileUtils(){

    }
    public static FileUtils getInstance(){
        if (fileUtils == null){
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }
    public String getCurrentDirectory(Context ctx) throws Exception{
        String currentDirectory = null;
        try{
            PackageManager m = ctx.getPackageManager();
            String s = ctx.getPackageName();
            PackageInfo p = m.getPackageInfo(s, 0);
            currentDirectory = p.applicationInfo.dataDir;
        } catch (Exception e){
            Log.e(this.getClass().getSimpleName() + ".getCurrentDirectory", "exception", e);
        }
        return currentDirectory;
    }
}
