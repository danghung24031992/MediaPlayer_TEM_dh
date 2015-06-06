package com.dh.mediaplayer.PlayMusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dh.mediaplayer.PlayMusicActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.Songslocals;

import java.util.ArrayList;

/**
 * Created by tiendat on 4/23/2015.
 */
public class PlayListActivity extends ActionBarActivity {
    private ListView listView;
    ArrayList<Songslocals> listData;
    CustomListViewSongs adapter;

    // Songs list


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        listView = (ListView) findViewById(R.id.list);


        SongsManagers songsManager = new SongsManagers();
        listData = songsManager.getAllAudio(".mp3", getBaseContext());
        adapter = new CustomListViewSongs(PlayListActivity.this, R.layout.playlist_item, listData);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting listitem index
                int songIndex = position;

                // Starting new intentc
                Intent in = new Intent(getApplicationContext(),
                        PlayMusicActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Closing PlayListView
                startActivity(in);
                finish();
            }
        });


    }
}
