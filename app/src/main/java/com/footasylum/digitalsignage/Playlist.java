package com.footasylum.digitalsignage;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Crow on 9/5/2017.
 */

class Playlist {
    //private ArrayList<HashMap<String, String>> playlist = new ArrayList<HashMap<String, String>>();
    private ArrayList<String> playlist = new ArrayList<>();
    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;
    private String state = Environment.getExternalStorageState();
    /**
     * Function to read all mp4 and jpg files from sdcard
     * and store the details in ArrayList
     * */

    ArrayList<String> getPlayList(){
        checkExternalStorage();
        String MEDIA_PATH;
        if (mExternalStorageAvailable && mExternalStorageWriteable) {
            MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "FA";
            Log.i("info1: ", MEDIA_PATH);
        }else {
            MEDIA_PATH = Environment.getDataDirectory().getPath() + File.separator + "FA";
            Log.i("info2: ", MEDIA_PATH);
        }
        File path = new File(MEDIA_PATH);
        if (!path.exists()){
            path.mkdir();
        }
//        Log.i("info", home.getPath());
        if (path.listFiles() != null) {
            for (File file : path.listFiles(new FileExtensionFilter())) {
                String song = file.getPath();
                playlist.add(song);
            }
        }
        return playlist;
    }

    private void checkExternalStorage() {
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    /**
     * Class to filter files which are having .mp4 or jpg extension
     * */
    private class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp4") || name.endsWith(".MP4")
                    || name.endsWith(".jpg") || name.endsWith(".JPG"));
        }
    }
}
