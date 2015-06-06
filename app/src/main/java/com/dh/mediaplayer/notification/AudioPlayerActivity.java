package com.dh.mediaplayer.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.UtilsSong;

public class AudioPlayerActivity extends Activity {
    private ImageButton btnShuffle;
    private ImageButton btnRepeat;
    private ImageButton btnRepeatAll;
    ImageButton btnBack;
    static ImageButton btnPause;
    ImageButton btnNext;
    static ImageButton btnPlay;
    static TextView textNowPlaying;
    static TextView textAlbumArtist;
    static TextView textComposer;
    ProgressBar progressBar;
    static Context context;
    TextView textBufferDuration, textDuration;
    static ImageView imgvBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        setContentView(R.layout.playmusic_layout);
        context = this;
        init();
    }

    private void init() {
        getViews();
        setListeners();
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), Mode.SRC_IN);
        PlayerConstants.PROGRESSBAR_HANDLER = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Integer i[] = (Integer[]) msg.obj;
                textBufferDuration.setText(UtilsSong.getDuration(i[0]));
                textDuration.setText(UtilsSong.getDuration(i[1]));
                progressBar.setProgress(i[2]);
            }
        };
    }

    private void setListeners() {

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.previousControl(getApplicationContext());
            }
        });

        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.pauseControl(getApplicationContext());
            }
        });

        btnPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.playControl(getApplicationContext());
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.nextControl(getApplicationContext());
            }
        });
        btnShuffle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.shuffleControl(getApplicationContext());
                btnShuffle.setVisibility(View.GONE);
                btnRepeat.setVisibility(View.GONE);
                btnRepeatAll.setVisibility(View.VISIBLE);

            }
        });
        btnRepeat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.repeatControl(getApplicationContext());
                btnShuffle.setVisibility(View.VISIBLE);
                btnRepeat.setVisibility(View.GONE);
                btnRepeatAll.setVisibility(View.GONE);
            }
        });
        btnRepeatAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.repeatAllControl(getApplicationContext());
                btnShuffle.setVisibility(View.GONE);
                btnRepeat.setVisibility(View.VISIBLE);
                btnRepeatAll.setVisibility(View.GONE);
            }
        });
    }

    public static void changeUI() {
        updateUI();
        changeButton();
    }

    private void getViews() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        textNowPlaying = (TextView) findViewById(R.id.textNowPlaying);
        //linearLayoutPlayer = (LinearLayout) findViewById(R.id.linearLayoutPlayer);
        textAlbumArtist = (TextView) findViewById(R.id.textAlbumArtist);
        //textComposer = (TextView) findViewById(R.id.textComposer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textBufferDuration = (TextView) findViewById(R.id.textBufferDuration);
        textDuration = (TextView) findViewById(R.id.textDuration);
        textNowPlaying.setSelected(true);
        textAlbumArtist.setSelected(true);
        imgvBG = (ImageView) findViewById(R.id.imgvBG);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnRepeatAll = (ImageButton) findViewById(R.id.btnRepeatAll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), getApplicationContext());
        if (isServiceRunning) {
            updateUI();
        }
        changeButton();
    }

    public static void changeButton() {
        if (PlayerConstants.SONG_PAUSED) {
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        } else {
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
        }
        updateUI();
    }

    private static void updateUI() {
        try {
            String songName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getMusicName();
            String artist = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getArtist();
            String album = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbumName();
            String composer = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getComposerName();
            textNowPlaying.setText(songName);
            textAlbumArtist.setText(artist + " - " + album);
            if (composer != null && composer.length() > 0) {
                textComposer.setVisibility(View.VISIBLE);
                textComposer.setText(composer);
            } else {
                textComposer.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        long albumId = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbum_ID();
        Bitmap albumArt = UtilsSong.getAlbumart(context, albumId);
        if (albumArt != null) {
            imgvBG.setImageBitmap(albumArt);
        } else {
            imgvBG.setImageResource(R.mipmap.album_default_big);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
