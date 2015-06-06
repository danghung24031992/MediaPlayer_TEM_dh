package com.dh.mediaplayer.activity_handler_online;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.custom_online.CustomListViewSongs;
import com.dh.mediaplayer.handler.AsynTaskSongs;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by DH on 25/05/2015.
 */
public class Artist extends Activity {
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
        int ID = getIntent().getIntExtra("ID",5);
        String url = "musicbysinger/"+ID;
        try {
            listData = new AsynTaskSongs(getBaseContext()).execute(url).get();
            adapter = new CustomListViewSongs(Artist.this, listData);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    MainActivity.lstSong = listData;
                    MainActivity.OnPlayItemClick(position);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
