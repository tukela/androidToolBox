package com.honglei.toolbox.utils.ok;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hl on 2017/5/10.
 */

public class MediaPlayUtil implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{
    private static MediaPlayUtil mMediaPlayUtil;

    public static final int MEDIA_STATUS_START = -1;
    public static final int MEDIA_STATUS_PREPARE=0;
    public static final int MEDIA_STATUS_PLAY = 1;
    public static final int MEDIA_STATUS_STOP = 2;
    public static final int MEDIA_STATUS_PAUSE = 3;
    private  MediaPlayer mMediaPlayer;
    private IMediaPlayerProgressListener mediaPlayerProgressLisener=null;
    private IOnPreparedListener onPreparedListener=null;
    private ScheduledExecutorService service;
    public int media_player_status;
    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener;

    /*资源准备完成开启 开放播放功能*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        media_player_status=MEDIA_STATUS_PREPARE;
        if (onPreparedListener!=null)
            onPreparedListener.onPrepared(mp);
    }
    private IOnCompleteListener onCompleteListener=null;

    @Override
    public void onCompletion(MediaPlayer mp) {
        media_player_status=MEDIA_STATUS_STOP;
        updateProgress(false);
        if (onCompleteListener!=null)
            onCompleteListener.onCompletion(mp);
    }
    /*资源准备完成*/
    public interface IOnPreparedListener{
        void onPrepared(MediaPlayer mp);
    }
    /*播放结束事件*/
    public interface IOnCompleteListener{
        void onCompletion(MediaPlayer mp);
    }
   /* 播放进度监听*/
    public interface IMediaPlayerProgressListener{
        void mediaPlayer(int duration, int CurrentPosition);
    }

    public void setOnCompleteListener(IOnCompleteListener onCompleteListener){
       this.onCompleteListener=onCompleteListener;
    }

    public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener){
      this.onBufferingUpdateListener=onBufferingUpdateListener;
    }
    public void setOnPreparedListener(IOnPreparedListener onPreparedListener){
        this.onPreparedListener=onPreparedListener;
    }
    public void setMediaPlayerProgressLisener(IMediaPlayerProgressListener mediaPlayerProgressLisener){
        this.mediaPlayerProgressLisener=mediaPlayerProgressLisener;
    }

    public static MediaPlayUtil getInstance(){
        if(mMediaPlayUtil == null){
            mMediaPlayUtil = new MediaPlayUtil();
        }
        return  mMediaPlayUtil;
    }

    private MediaPlayUtil() {
      create();
    }

    public void play(){
        try {
            if (media_player_status==MEDIA_STATUS_PREPARE)
            {
                mMediaPlayer.start();
                updateProgress(true);
                media_player_status=MEDIA_STATUS_PLAY;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateProgress(boolean b){
        if (mMediaPlayer!=null)
        {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mediaPlayerProgressLisener!=null)
                    mediaPlayerProgressLisener
                            .mediaPlayer(mMediaPlayer.getDuration(),mMediaPlayer.getCurrentPosition());
                }
            };
            if (!b&service!=null)
                service.shutdown();
            service = Executors .newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(runnable, 800, 500, TimeUnit.MILLISECONDS);// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        }
    }

    public void pause(){
        if(mMediaPlayer != null){
            try {
                mMediaPlayer.pause();
                media_player_status=MEDIA_STATUS_PAUSE;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }


    public void stop(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            media_player_status=MEDIA_STATUS_STOP;
        }
    }
    public int getCurrentPosition(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            return mMediaPlayer.getCurrentPosition();
        }else{
            return 0;
        }
    }


    public int getDutation(){
        if(mMediaPlayer!= null && mMediaPlayer.isPlaying()){
            return mMediaPlayer.getDuration();
        }else{
            return 0;
        }
    }


    public boolean isPlaying(){
        if(mMediaPlayer != null){
            return mMediaPlayer.isPlaying();
        }else{
            return false;
        }
    }



    public void create( ) {
        if (mMediaPlayer!=null)
        {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
        mMediaPlayer =  new MediaPlayer();
    }



    private String getPathFromUri(Uri songUri){
        try {
            return URLDecoder.decode(songUri.getPath().toString(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDataSource(AssetFileDescriptor afd){
        reserMediaPlay();
        try {
            mMediaPlayer.setDataSource(afd);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(Context context,Uri uri){
        reserMediaPlay();
        try {
            mMediaPlayer.setDataSource(context,uri);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setDataSource(String path){
        reserMediaPlay();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDataSource(FileDescriptor fileDescriptor){
        reserMediaPlay();
        try {
            mMediaPlayer.setDataSource(fileDescriptor);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDataSource(MediaDataSource mediaDataSource){
        reserMediaPlay();
        mMediaPlayer.setDataSource(mediaDataSource);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
    }
    private void reserMediaPlay(){
        if (mMediaPlayer==null)
            create();
        if (mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
            mMediaPlayer=new MediaPlayer();
        }
        media_player_status=MEDIA_STATUS_START;
    }



}



