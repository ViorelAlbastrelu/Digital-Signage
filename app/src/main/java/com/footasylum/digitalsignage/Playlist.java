package com.footasylum.digitalsignage;

import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Crow on 9/5/2017.
 */

class Playlist {
    //private ArrayList<HashMap<String, String>> playlist = new ArrayList<HashMap<String, String>>();
    private ArrayList<String> playlist1 = new ArrayList<>();

    private final String MEDIA_PATH = Environment.getExternalStorageDirectory()+ File.separator+ "FA";
    /**
     * Function to read all mp4 and jpg files from sdcard
     * and store the details in ArrayList
     * */
    ArrayList<String> getPlayList(){
        File home = new File(MEDIA_PATH);
        if (home.listFiles()!=null) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                String song = file.getPath();
                playlist1.add(song);
            }
        }
        return playlist1;
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
