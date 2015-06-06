package com.dh.mediaplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dh.mediaplayer.PlayMusic.PlayListActivity;
import com.dh.mediaplayer.PlayMusic.SongsManagers;
import com.dh.mediaplayer.PlayMusic.Utilities;
import com.dh.mediaplayer.bean.Songslocals;
import com.dh.mediaplayer.player_online.PlayMusicOnline;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DH on 4/22/2015.
 * //
 */
public class PlayMusicActivity extends Activity implements View.OnClickListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private ImageButton btnBack;
    private ImageButton btnPlayList;
    private ImageButton btnPlay;
    private ImageButton btnPause;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private ImageButton btnShuffle;
    private ImageButton btnRepeat;
    private ImageButton btnRepeatAll;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;

    public static PlayMusicOnline playMusic;


    // Media Player
    ///  MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    public static ArrayList<Songslocals> listData;

    private SongsManagers songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    public static int currentSongIndex;
    private boolean isShuffle = false;
    private boolean isRepeatAll = true;
    private boolean isRepeat = false;
    private android.os.Handler m = new android.os.Handler();
    ;
    //private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmusic_layout);
        btnPlayList = (ImageButton) findViewById(R.id.PlayList);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnPrevious = (ImageButton) findViewById(R.id.Previous);
        btnNext = (ImageButton) findViewById(R.id.Next);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnRepeatAll = (ImageButton) findViewById(R.id.btnRepeatAll);
        songTitleLabel = (TextView) findViewById(R.id.tvSongTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.tvSecbarStart);
        songTotalDurationLabel = (TextView) findViewById(R.id.tvSecbarEnd);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        listData = new ArrayList<Songslocals>();
        //khoi tao doi tuong mediaPlayer
        // mp = new MediaPlayer();
        currentSongIndex = (int) 0;
        playMusic = new PlayMusicOnline(this);

        songManager = new SongsManagers();
        utils = new Utilities();
        songProgressBar.setOnSeekBarChangeListener(this);
        // mp.setOnCompletionListener(this);
        //lay tat ca bai hat
        listData = songManager.getAllAudio(".mp3", getBaseContext());
        playSong(0);


        btnPlayList.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent i = new Intent(PlayMusicActivity.this, PlayListActivity.class);
                                               startActivityForResult(i, 100);
                                           }
                                       }
        );
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean status = playMusic.playStopAudio();

                    if (!status) {
                        btnPlay.setVisibility(View.GONE);
                        //btnPlay.setImageResource(R.drawable.btn_pause);
                    } else {
                        btnPlay.setVisibility(View.VISIBLE);

                        //btnPlay.setImageResource(R.drawable.btn_play);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (listData != null) {
                        currentSongIndex = currentSongIndex + 1;
                        if (currentSongIndex > listData.size() - 1) {
                            currentSongIndex = 0;
                        }
                        playMusic.playAudio(listData.get(currentSongIndex).getLink());
                        songTitleLabel.setText(listData.get(currentSongIndex).getMusicName());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (listData != null) {
                        currentSongIndex = currentSongIndex - 1;
                        if (currentSongIndex < 0) {
                            currentSongIndex = listData.size() - 1;
                        }
                        playMusic.playAudio(listData.get(currentSongIndex).getLink());
                        songTitleLabel.setText(listData.get(currentSongIndex).getMusicName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShuffle = true;
                isRepeatAll = false;
                isRepeat = false;
                btnShuffle.setVisibility(View.GONE);
                btnRepeat.setVisibility(View.GONE);
                btnRepeatAll.setVisibility(View.VISIBLE);
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRepeat = true;
                isShuffle = false;
                isRepeatAll = false;
                btnShuffle.setVisibility(View.VISIBLE);
                btnRepeat.setVisibility(View.GONE);
                btnRepeatAll.setVisibility(View.GONE);
            }
        });
        btnRepeatAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRepeatAll = true;
                isRepeat = false;
                isShuffle = false;
                btnShuffle.setVisibility(View.GONE);
                btnRepeat.setVisibility(View.VISIBLE);
                btnRepeatAll.setVisibility(View.GONE);
            }
        });
        init();
    }

    private void init() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            currentSongIndex = data.getExtras().getInt("songIndex");
            //playMusic.playAudio(currentSongIndex);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Intent i1 = new Intent(PlayMusicActivity.this, MainActivity.class);
                startActivity(i1);
                overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // check for repeat is ON or OFF
        if (isRepeat == true) {
            // repeat is on play same song again
            playSong(currentSongIndex);
        }
        if (isShuffle == true) {
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((listData.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        }
        if (isRepeatAll == true){
            // no repeat or shuffle ON - play next song
            if (currentSongIndex < (listData.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                // play first song
                // playMusic.playAudio(0);
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        m.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        m.removeCallbacks(mUpdateTimeTask);

        //  int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        //   mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    public void playSong(int songIndex) {
        // Play song
        try {
            //mp.reset();
            //   mp.setDataSource(listData.get(songIndex).getLink(songIndex);
            // mp.prepare();
            //  mp.start();
            // Displaying Song title
            String songTitle = listData.get(songIndex).getMusicName();
            songTitleLabel.setText(songTitle);
            btnPlay.setImageResource(R.drawable.btn_pause);
            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            //} catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cap nhat thoi gian cho thanh progressBar
     */
    public void updateProgressBar() {
        m.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            //long totalDuration = mp.getDuration();
            //long currentDuration = mp.getCurrentPosition();

            // Hien thi tong thoi gian
            // songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // hien thi thoi gian hoan thanh
            // songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            // int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            ////   Log.d("Progress", "" + progress);
            //  songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            m.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
