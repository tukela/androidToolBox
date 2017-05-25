package com.honglei.toolbox.view;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.colin.aiyi.R;

/**
 * Created by hl on 2017/5/25.
 */

public class PopuDialog {


    public  static void dfdf(Context context,View view){
        final AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
       // mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        View  layout = LayoutInflater.from(context).inflate(R.layout.layout_voice,null);
        VerticalSeekBar seekBar= (VerticalSeekBar) layout.findViewById(R.id.verticalSeekBar);
        seekBar.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar.setProgress(currentVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 1);
                sendKeyEvent(3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PopupWindow popupWindow = new PopupWindow(layout,25,240);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
    }


    public static void sendKeyEvent(final int KeyCode) {
        new Thread() {     //不可在主线程中调用
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }






}
