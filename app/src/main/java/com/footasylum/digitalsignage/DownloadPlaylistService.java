package com.footasylum.digitalsignage;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPlaylistService extends Service {
    private final IBinder iBinder = new MyBinder();

    public DownloadPlaylistService() {
    }

    public void dowloadWithManager(String fileURL){

        String fileName = URLUtil.guessFileName(fileURL, null, MimeTypeMap.getFileExtensionFromUrl(fileURL));
        String dwnldDir = Environment.getDownloadCacheDirectory().getPath();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileURL));
        request.setTitle("File download.");
        request.setDescription("Downloading "+fileName+"...");

//      request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setDestinationInExternalFilesDir(this, dwnldDir, fileName);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public void downloadContent(String fileURL){
        try {

            File rootDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "FaPalylist");
            if(!rootDirectory.exists()){
                rootDirectory.mkdir();
            }

            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String fileName = URLUtil.guessFileName(fileURL, null, MimeTypeMap.getFileExtensionFromUrl(fileURL));
            FileOutputStream f = new FileOutputStream(new File(rootDirectory,fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while((byteCount = in.read(buffer)) > 0){
                f.write(buffer, 0, byteCount);
            }
            f.close();
        }catch (IOException e){
            Log.d("Error....", e.toString());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public class MyBinder extends Binder {
        DownloadPlaylistService getService(){
            return DownloadPlaylistService.this;
        }
    }
}
