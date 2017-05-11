package com.footasylum.digitalsignage;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by Crow on 9/5/2017.
 */

class Play {

    private Context context;
    private VideoView videoView;
    private ImageView imageView;

    private ArrayList<String> playList;

    private static int plIndex = 0;
    private static final int IMAGE_DEFAULT_DURATION = 10000;

    Play(Context context){
        playList = new Playlist().getPlayList();
        videoView = (VideoView) ((Activity)context).findViewById(R.id.videoView);
        imageView = (ImageView) ((Activity)context).findViewById(R.id.imageView);
    }

    void loopAll(){
        playNext();
    }

    private void playVideo(){
        String content = playList.get(plIndex);
        if (videoView.getVisibility() == View.GONE){
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
        videoView.setVideoURI(Uri.parse(content));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                plIndex++;
                playNext();
            }
        });
    }

    private void playImage(){
        String content = playList.get(plIndex);
        if(imageView.getVisibility() == View.GONE){
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        imageView.setImageURI(Uri.parse(content));
        new CountDownTimer(IMAGE_DEFAULT_DURATION, 1000) {

            public void onTick(long millisUntilFinished) {
                // implement whatever you want for every tick
            }

            public void onFinish() {
                plIndex++;
                playNext();
            }
        }.start();
//        Handler imgHandler = new Handler();
//        Runnable imgRun = new Runnable() {
//            @Override
//            public void run() {
//                videoView.setVisibility(View.GONE);
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setImageURI(Uri.parse(playList.get(plIndex)));
//                playNext();
//            }
//        };
//        imgHandler.postDelayed(imgRun, IMAGE_DEFAULT_DURATION);
    }

    private void playNext(){
        Log.i("info", playList.size()+"");
        Log.i("info", plIndex +"");
        if (plIndex < playList.size()){
            String content = playList.get(plIndex);
            if (content.endsWith(".mp4") || content.endsWith(".MP4")) {
                playVideo();
            } else if (content.endsWith(".jpg") || content.endsWith(".JPG")) {
                playImage();
            } else {
                playNext();
            }
        }
        if (plIndex == playList.size()){
            plIndex = 0;
            playNext();
        }
    }
}
