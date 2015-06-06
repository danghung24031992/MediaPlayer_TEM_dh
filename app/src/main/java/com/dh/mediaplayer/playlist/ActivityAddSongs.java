package com.dh.mediaplayer.playlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.bean.UtilsSong;
import com.dh.mediaplayer.customListView_Offline.AdapterSongs;
import com.dh.mediaplayer.custom_online.CustomListViewSongLocal;
import com.dh.mediaplayer.database.MyDataHelper;
import com.dh.mediaplayer.handler.HandlerOnItemClickCategoryOnline;
import com.dh.mediaplayer.handler.HandlerOnItemClickSongLocal;

import java.util.ArrayList;

/**
 * Created by tiendat on 5/29/2015.
 */
public class ActivityAddSongs extends Activity {
    private LinearLayout layout_add;
    private ListView listbaihat;
    ArrayList<SongBean> listSongs;
    ArrayList<SongBean> listSongsPlay;
    AdapterSongs adapterSongs;
    MyDataHelper myDataHelper;
    CustomListViewSongLocal adapter;
    TextView tvView;
    String addNew = "no";
    Button btnAccept;
    int playlistId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_baihat);
        listbaihat = (ListView) findViewById(R.id.listViewBaihat);
        layout_add = (LinearLayout) findViewById(R.id.layout_add);
        tvView = (TextView) findViewById(R.id.tvView);
        btnAccept = (Button) findViewById(R.id.btnAccept);

        Bundle extras = getIntent().getExtras();
        playlistId = extras.getInt("playlistid");
        addNew = extras.getString("add");

        myDataHelper = new MyDataHelper(this);

        if (addNew.equals("no")) {
            tvView.setText("Nghe nhạc");
            btnAccept.setText("Thêm nhạc");
            listSongs = myDataHelper.getMusicByPlayListId(playlistId);

            if (listSongs != null && listSongs.size() >= 0) {

                adapter = new CustomListViewSongLocal(ActivityAddSongs.this, listSongs);
                listbaihat.setAdapter(adapter);
                listbaihat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainActivity.lstSong = listSongs;
                        MainActivity.OnPlayItemClick(position);
                    }
                });
            } else {
                Intent add = new Intent(this, ActivityAddSongs.class);
                add.putExtra("playlistid", playlistId);
                add.putExtra("add", "yes");
                startActivity(add);
                finish();
            }
        } else {
            tvView.setText("Thêm nhạc");
            btnAccept.setText("Cập nhật");
            UtilsSong utilsListSong = new UtilsSong();
            listSongs = utilsListSong.getAllAudio(".mp3", ActivityAddSongs.this);
            listSongsPlay = myDataHelper.getMusicByPlayListId(playlistId);
            if (listSongsPlay != null && listSongsPlay.size() > 0) {
                for (int i = 0; i < listSongs.size(); i++) {

                    for (int j = 0; j < listSongsPlay.size(); j++) {
                        if (listSongsPlay.get(j).getLink().equals(listSongs.get(i).getLink())) {
                            listSongs.get(i).setSongsChecked(true);
                        }
                    }
                }
            }
            adapterSongs = new AdapterSongs(listSongs, ActivityAddSongs.this);
            listbaihat.setAdapter(adapterSongs);
        }

        layout_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onAddNewSong(View v) {
        if (addNew.equals("no")) {
            Intent add = new Intent(this, ActivityAddSongs.class);
            add.putExtra("playlistid", playlistId);
            add.putExtra("add", "yes");
            startActivity(add);
            finish();
        } else {
            myDataHelper = new MyDataHelper(this);
            myDataHelper.deleteDataMusic(playlistId);
            for (int i = 0; i < listSongs.size(); i++) {
                if (listSongs.get(i).isSongsChecked()) {
                    myDataHelper.insertDataMusic(listSongs.get(i).getMusicName(), listSongs.get(i).getAlbum_ID(), listSongs.get(i).getAlbumName(), listSongs.get(i).getLink(), playlistId);
                }
            }

            Toast.makeText(this, R.string.add_toast, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ActivityAddSongs.class);
            intent.putExtra("playlistid", playlistId);
            intent.putExtra("add", "no");
            startActivity(intent);
            finish();
        }
    }
}
