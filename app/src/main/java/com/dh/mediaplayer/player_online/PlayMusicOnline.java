package com.dh.mediaplayer.player_online;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by DH on 5/15/2015.
 */
public class PlayMusicOnline {
    MediaPlayer mediaPlayer;
    Context context;

    public PlayMusicOnline(Context mContext) {
        context = mContext;
    }

    public void playAudio(String url) {
        killMediaPlayer();
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
            new AsynTaskPlaySongs().execute(url);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public  boolean playStopAudio(){
        boolean status = false;
        try {
           if (mediaPlayer.isPlaying())
           {
               mediaPlayer.pause();
               status= true;
           }
            else
           {
               mediaPlayer.start();
               status = false;
           }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return status;
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class AsynTaskPlaySongs extends AsyncTask<String, Void, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... url) {
            try {
                mediaPlayer.setDataSource(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }

    }


}
