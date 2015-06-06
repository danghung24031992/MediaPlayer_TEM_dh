package com.dh.mediaplayer.activity_handler_offline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.bean.UtilsSong;
import com.dh.mediaplayer.custom_online.CustomListViewSongs;
import com.dh.mediaplayer.handler.AsynTaskSongs;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by CHUNGNHIM on 5/29/2015.
 */
public class Album_Action extends Activity {
    private ListView listView;
    CustomListViewSongs adapter;
    ArrayList<SongBean> listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_artist_show_list_music);
        init();
    }

    private void init() {
        listView = (ListView)findViewById(R.id.lvArtist_listmusic);
        String albumName = getIntent().getStringExtra("album");
        try {
            listData = UtilsSong.getAllAudioByAlbum(albumName,Album_Action.this);

            adapter = new CustomListViewSongs(Album_Action.this, listData);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity.lstSong = listData;
                    MainActivity.OnPlayItemClick(position);
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
