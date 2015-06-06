package com.dh.mediaplayer.notification;

import android.content.Context;

import com.dh.mediaplayer.R;

import com.dh.mediaplayer.bean.UtilsSong;

import java.util.Random;

public class Controls {
    static String LOG_CLASS = "Controls";

    public static void playControl(Context context) {
        sendMessage("Play");
    }

    public static void pauseControl(Context context) {
        sendMessage("Pause");
    }

    public static void nextControl(Context context) {
        boolean isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if (PlayerConstants.SONGS_LIST.size() > 0) {
            if (PlayerConstants.SONG_NUMBER < (PlayerConstants.SONGS_LIST.size() - 1)) {
                PlayerConstants.SONG_NUMBER++;
                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            } else {
                PlayerConstants.SONG_NUMBER = 0;
                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        PlayerConstants.SONG_PAUSED = false;
    }

    public static void previousControl(Context context) {
        boolean isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if (PlayerConstants.SONGS_LIST.size() > 0) {
            if (PlayerConstants.SONG_NUMBER > 0) {
                PlayerConstants.SONG_NUMBER--;
                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            } else {
                PlayerConstants.SONG_NUMBER = PlayerConstants.SONGS_LIST.size() - 1;
                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        PlayerConstants.SONG_PAUSED = false;
    }

    public static void shuffleControl(Context context) {
        boolean isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if (PlayerConstants.SONGS_LIST.size() > 0) {
//            if(PlayerConstants.SONG_NUMBER > 0){

            long duration = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getDuration();
            for (int i = 0;i< duration/1000;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ( i == duration - 1000){
                    Random rand = new Random();
                    PlayerConstants.SONG_NUMBER = rand.nextInt((PlayerConstants.SONGS_LIST.size() - 1) - 0 + 1) + 0;
                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                }
            }
//            }else{
//                PlayerConstants.SONG_NUMBER = PlayerConstants.SONGS_LIST.size() - 1;
//                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
//            }
        }
        PlayerConstants.SONG_PAUSED = false;
    }

    public static void repeatControl(Context context) {
        boolean isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if (PlayerConstants.SONGS_LIST.size() > 0) {
            //if(PlayerConstants.SONG_NUMBER > 0){
            //PlayerConstants.SONG_NUMBER;

            long duration = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getDuration();
            for (int i = 0;i< duration/1000;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ( i == duration - 1000){
                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                }
            }
//            }else{
//                PlayerConstants.SONG_NUMBER = PlayerConstants.SONGS_LIST.size() - 1;
//                PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
//            }
        }
        PlayerConstants.SONG_PAUSED = false;
    }

    public static void repeatAllControl(Context context) {
        boolean isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), context);
        if (!isServiceRunning) {
            return;
        }
        if (PlayerConstants.SONGS_LIST.size() > 0) {
            if (PlayerConstants.SONG_NUMBER < (PlayerConstants.SONGS_LIST.size() - 1)) {
                long duration = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getDuration();
                for (int i = 0;i< duration/1000;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if ( i == duration - 1000){
                        PlayerConstants.SONG_NUMBER++;
                        PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                    }
                }

            } else {
//                    PlayerConstants.SONG_NUMBER = 0;
//                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        PlayerConstants.SONG_PAUSED = false;
    }

    private static void sendMessage(String message) {
        try {
            PlayerConstants.PLAY_PAUSE_HANDLER.sendMessage(PlayerConstants.PLAY_PAUSE_HANDLER.obtainMessage(0, message));
        } catch (Exception e) {
        }
    }
}
