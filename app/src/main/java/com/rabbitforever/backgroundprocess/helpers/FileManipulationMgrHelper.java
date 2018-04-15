package com.rabbitforever.backgroundprocess.helpers;

import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileManipulationMgrHelper {
    public FileManipulationMgrHelper() throws Exception {
        init();
    }
    private void init() throws Exception{
        try {

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".init()", e);
            throw e;
        }

    }
    public List<File> sortByNumber(List<File> files) {
        File[] fileArray = new File[files.size()];

        try {
            fileArray= files.toArray(fileArray);
            Arrays.sort(fileArray, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    String absolutePath1 = o1.getAbsolutePath();
                    String [] splitArray1 = absolutePath1.split("\\\\");
                    String absolutePath2 = o2.getAbsolutePath();
                    String [] splitArray2 = absolutePath2.split("\\\\");
                    int n1 =splitArray1.length;
                    int n2 = splitArray2.length;
                    return n1 - n2;
                }
            });
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".sortByNumber() - files=" + files, e);
            throw e;
        }
        return Arrays.asList(fileArray);
    }
}
