package com.footasylum.digitalsignage;

import android.os.Environment;
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

    private static String MEDIA_PATH = "";

    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;
    private String state = Environment.getExternalStorageState();

    /**
     * Function to read all mp4 and jpg files from sdcard
     * and store the details in ArrayList
     * */
    ArrayList<String> getPlayList(){
        checkExternalStorage();
        File home = checkExternalStorage();
        if (home.listFiles()!=null) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                String song = file.getPath();
                playlist.add(song);
            }
        }
        return playlist;
    }

    private File checkExternalStorage(){
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if(mExternalStorageAvailable){
            MEDIA_PATH = Environment.getExternalStorageDirectory()+ File.separator+ "FA";
        }else
            MEDIA_PATH = Environment.getDataDirectory()+File.separator+"FA";
        File home = new File(MEDIA_PATH);
        if(!home.exists()){
            home.mkdir();
        }
        return home;
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
