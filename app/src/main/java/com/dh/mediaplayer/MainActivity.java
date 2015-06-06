package com.dh.mediaplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.bean.UtilsSong;
import com.dh.mediaplayer.notification.AudioPlayerActivity;
import com.dh.mediaplayer.notification.Controls;
import com.dh.mediaplayer.notification.PlayerConstants;
import com.dh.mediaplayer.notification.SongService;

import java.util.List;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    public static Context context;
    public static List<SongBean> lstSong;

    static TextView tvMusicSinger;
    static TextView tvMusicName;

    static ImageButton btnPlay;
    static ImageButton btnNext;
    static ImageButton btnBack;
    static ImageButton btnPause;
    static ImageButton btnDelete;

    static ImageView imgvSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        setListeners();

        context = this;
        buildTabHost();

    }

    private void buildTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(this.getTabIndicator(mTabHost.getContext(), R.string.title_tabs_nav_one, R.drawable.img_tabs_home)),
                HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("local").setIndicator(this.getTabIndicator(mTabHost.getContext(), R.string.title_tabs_nav_two, R.drawable.img_tabs_local)),
                LocalFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("online").setIndicator(this.getTabIndicator(mTabHost.getContext(), R.string.title_tabs_nav_three, R.drawable.img_tabs_online)),
                OnlineFragment.class, null);
    }

    private View getTabIndicator(Context context, int title, int img) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_navigation, null);
        TextView tv = (TextView) view.findViewById(R.id.txt_tabs_nav);
        ImageView imgv = (ImageView) view.findViewById(R.id.imgv_tabs_nav);

        tv.setText(title);
        imgv.setImageResource(img);
        return view;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitByBackKey() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }


    public static void updateUI() {
        try {
            SongBean data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
            tvMusicName.setText(data.getMusicName() + " " + data.getArtist() + "-" + data.getAlbumName());
            tvMusicSinger.setText(data.getAlbumName());
            Bitmap albumArt = UtilsSong.getAlbumart(context, data.getAlbum_ID());
            if (albumArt != null) {
//                imgvSong.setBackgroundDrawable(new BitmapDrawable(albumArt));
                imgvSong.setImageBitmap(albumArt);
            } else {
                imgvSong.setImageResource(R.mipmap.ic_play_bottom_layout);
            }
            //linearLayoutPlayingSong.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public static void OnPlayItemClick(int position) {
        Log.d("TAG", "TAG Tapped INOUT(IN)");
        PlayerConstants.SONGS_LIST = MainActivity.lstSong;
        PlayerConstants.SONG_PAUSED = false;
        PlayerConstants.SONG_NUMBER = position;
        boolean  isServiceRunning = UtilsSong.isServiceRunning(SongService.class.getName(), context.getApplicationContext());
        if (!isServiceRunning) {
            Intent i = new Intent(context.getApplicationContext(), SongService.class);
            context.startService(i);
        } else {
            PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
        }
        updateUI();
        changeButton();
        Log.d("TAG", "TAG Tapped INOUT(OUT)");
    }

    public static void changeButton() {
        if (PlayerConstants.SONG_PAUSED) {
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        } else {
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
        }
    }

    public static void changeUI() {
        updateUI();
        changeButton();
    }

    private void setListeners() {

//        btnPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,AudioPlayerActivity.class);
//                startActivity(i);
//            }
//        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.playControl(context.getApplicationContext());
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controls.pauseControl(context.getApplicationContext());
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.nextControl(context.getApplicationContext());
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.previousControl(context.getApplicationContext());
            }
        });

       /* btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), SongService.class);
                stopService(i);
                //linearLayoutPlayingSong.setVisibility(View.GONE);
            }
        });*/


        imgvSong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (UtilsSong.isServiceRunning(SongService.class.getName(), context.getApplicationContext()) == true){
                   /* if (Build.VERSION.SDK_INT >= 21){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                                MainActivity.this, imgvSong, "ImgProfile");
                        Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
                        startActivity(intent, options.toBundle());
                    }
                    else {*/
                        Intent i = new Intent(context, AudioPlayerActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.bottom_in,R.anim.top_out);

                    /*}*/
                }
            }
        });
    }

    private void getViews() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);

        tvMusicSinger = (TextView) findViewById(R.id.tvMusicSinger);
        tvMusicName = (TextView) findViewById(R.id.tvMusicName);
        imgvSong = (ImageView) findViewById(R.id.imgvSong);

    }
}
