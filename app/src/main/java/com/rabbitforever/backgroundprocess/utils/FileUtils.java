package com.rabbitforever.backgroundprocess.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> getFileList(){
        List<String> fileList = null;
        try{

        } catch (Exception e){
            Log.e(this.getClass().getSimpleName() + ".getFileList()", "exception", e);
        }
        return fileList;
    }
    public void createDirectoryIfNotExisted(String directoryName) throws Exception {
        File theDir = null;
        try {
            theDir = new File(directoryName);

            if (!theDir.exists()){
                theDir.mkdirs();
            }


        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".createDirectoryIfNotExisted() -directoryName=" + directoryName, e);
            throw e;
        } finally{
            if (theDir != null){
                theDir = null;
            }
        }
    }
    public List<String> readFromFile(String fileName) throws Exception {
        BufferedReader br = null;
        List<String> stringList = new ArrayList<String>();
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(fileName));
            while ((sCurrentLine = br.readLine()) != null) {
                stringList.add(sCurrentLine);
            }

        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName() + ".readFromFile()", "exception:  - fileName=" + fileName, e);
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return stringList;
    }

    public void traverseDir(String fileInString, List<File> fileList){
        try {
            File file = new File(fileInString);
            if (file != null){
                traverseDir(file, fileList);
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".traverseDir() -fileInString=" + fileInString + ",fileList="+ fileList, e);
            throw e;
        } finally{

        }
    }
    public void traverseDir(File dir, List<File> fileList){
        try {
            for (File fileEntry: dir.listFiles()){
                if (fileEntry.isDirectory()) {
                    traverseDir(fileEntry, fileList);
                } else {
                    fileList.add(fileEntry);

                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".traverseDir() -dir=" + dir + ",fileList="+ fileList, e);
            throw e;
        } finally{

        }
    }
}
