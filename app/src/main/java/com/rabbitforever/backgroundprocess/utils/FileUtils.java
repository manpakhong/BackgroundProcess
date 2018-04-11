package com.rabbitforever.backgroundprocess.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;

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
            Log.e(this.getClass().getSimpleName() + ".getCurrentDirectory()", "exception", e);
        }
        return currentDirectory;
    }
    public boolean isDirectoryExisted(String dirPath){
        boolean isDirectoryExisted = false;
        File file = null;
        try{
            file = new File(dirPath);
            isDirectoryExisted = isDirectoryExisted(file);
        }catch (Exception e){
            Log.e(this.getClass().getSimpleName() + ".isDirectoryExisted()", "exception", e);
        } finally{
            if (file != null){
                file = null;
            }
        }
        return isDirectoryExisted;
    }
    public boolean isDirectoryExisted(File file){
        boolean isDirectoryExisted = false;
        try{
            if (file.isDirectory() && file.exists()){
                isDirectoryExisted = true;
            }
        } catch (Exception e){
            Log.e(this.getClass().getSimpleName() + ".isDirectoryExisted()", "exception", e);
        }
        return isDirectoryExisted;
    }
    public boolean isFileExisted(String fileString){
        boolean isFileExisted = false;
        File file = null;
        try{
            file = new File(fileString);
            isFileExisted = isFileExisted(file);
        }catch (Exception e){
            Log.e(this.getClass().getSimpleName() + ".isFileExisted()", "exception", e);
        } finally{
            if (file != null){
                file = null;
            }
        }
        return isFileExisted;
    }
    public boolean isFileExisted(File file){
        boolean isFileExisted = false;
        try{
            if (file.isFile() && file.exists()){
                isFileExisted = true;
            }
        } catch (Exception e){
            Log.e(this.getClass().getSimpleName() + ".isFileExisted()", "exception", e);
        }
        return isFileExisted;
    }
}
