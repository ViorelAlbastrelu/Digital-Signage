package com.footasylum.digitalsignage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;

import com.footasylum.digitalsignage.DownloadPlaylistService.MyBinder;

public class MainActivity extends Activity {

    DownloadPlaylistService downloadPlaylistService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, DownloadPlaylistService.class);
        bindService(i,faConnection, Context.BIND_AUTO_CREATE);

        Play playList = new Play(this);
        playList.loopAll();
    }

    private ServiceConnection faConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder binder = (MyBinder) service;
            downloadPlaylistService = binder.getService();
            downloadPlaylistService.downloadWithManager("https://img-9gag-fun.9cache.com/photo/a2rgMDe_460sv.mp4");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            downloadPlaylistService = null;
        }
    };
}
