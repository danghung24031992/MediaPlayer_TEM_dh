package com.dh.mediaplayer.handler;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.player_online.PlayMusicOnline;

import java.util.ArrayList;

/**
 * Created by DH on 5/14/2015.
 */
public class HandlerOnItemClickSongLocal implements AdapterView.OnItemClickListener {
    private FragmentActivity fragmentActivity;
    PlayMusicOnline playMusic;

    public HandlerOnItemClickSongLocal(FragmentActivity activity, ArrayList<SongBean> listData) {
        this.fragmentActivity = activity;
        MainActivity.lstSong = listData;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ERROR", MainActivity.lstSong.get(position).getLink());
        MainActivity.OnPlayItemClick(position);
    }
}
