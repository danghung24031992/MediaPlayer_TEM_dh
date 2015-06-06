package com.dh.mediaplayer.handler;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.bean.CategoryBean;
import com.dh.mediaplayer.bean.SingerBean;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.player_online.PlayMusicOnline;

import java.util.ArrayList;

/**
 * Created by DH on 5/14/2015.
 */
public class HandlerOnItemClickSongsOnline implements AdapterView.OnItemClickListener {
    private FragmentActivity fragmentActivity;
    PlayMusicOnline playMusic;// = new PlayMusicOnline();
    public HandlerOnItemClickSongsOnline(FragmentActivity activity, ArrayList<SongBean> listData) {
        this.fragmentActivity = activity;
        MainActivity.lstSong = listData;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MainActivity.OnPlayItemClick(position);
    }
}
