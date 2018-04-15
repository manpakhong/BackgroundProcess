package com.rabbitforever.backgroundprocess.services;

import android.util.Log;

import com.rabbitforever.backgroundprocess.helpers.FileManipulationMgrHelper;
import com.rabbitforever.backgroundprocess.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManipulationMgr{
    private FileUtils fileUtils;
    private FileManipulationMgrHelper fileManipulationMgrHelper;
    public FileManipulationMgr() throws Exception{
        init();
    }
    private void init() throws Exception {
        try {
            fileManipulationMgrHelper = new FileManipulationMgrHelper();
            fileUtils = FileUtils.getInstance();

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".init()", e);
            throw e;
        }

    }
    public List<File> getFileListRecrusively(String folderPath) throws Exception {
        List<File> fileList = null;
        try {
            String folderRootFromSourceToUsb = folderPath;
            fileList = new ArrayList<File>();
            fileUtils.traverseDir(folderRootFromSourceToUsb, fileList);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(),
                    ".getFileListRecrusively() -folderPath=" + folderPath, e);
            throw e;
        }
        return fileList;
    }
}
