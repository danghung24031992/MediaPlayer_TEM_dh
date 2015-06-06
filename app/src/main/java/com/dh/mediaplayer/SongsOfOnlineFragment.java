package com.dh.mediaplayer;

/**
 * Created by DH on 5/12/2015.
 */

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dh.mediaplayer.JsonPaser.Songs_JsonPaser;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.custom_online.CustomListViewSongs;
import com.dh.mediaplayer.handler.HandlerOnItemClickSongsOnline;
import com.dh.mediaplayer.handler.HandlerOnTouchHideSearch;
import com.dh.mediaplayer.network.ConnectionNetwork;

import java.util.ArrayList;

public class SongsOfOnlineFragment extends Fragment {
    private ListView lvSongOnline;
    private LinearLayout linearSongsSearch;
    private ImageButton imageBNetWork;
    CustomListViewSongs adapter;
    ArrayList<SongBean> listData;
    private LinearLayout lvNetWorkSong;
    private ConnectionNetwork connect;
    private Boolean checkNetWork = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.song_child_of_online_fragment, container, false);
        lvSongOnline = (ListView) V.findViewById(R.id.lvSongsOnline);
        //linearSongsSearch = (LinearLayout) V.findViewById(R.id.searchSongsOnline);
        imageBNetWork = (ImageButton)V.findViewById(R.id.imgBCheckNetworkSong);
        lvNetWorkSong = (LinearLayout)V.findViewById(R.id.lvNetWorkSong);
        //lvSongOnline.setOnTouchListener(new HandlerOnTouchHideSearch(getActivity(), lvSongOnline, linearSongsSearch));

        iNetwork();
        imageBNetWork.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageBNetWork.setBackgroundColor(Color.GRAY);
                    iNetwork();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    imageBNetWork.setBackgroundColor(Color.TRANSPARENT);
                }
                return false;
            }
        });
        return V;
    }

    private void iNetwork() {
        connect = new ConnectionNetwork(getActivity());
        checkNetWork = connect.isConnectingToInternet();
        if (!checkNetWork){
            lvSongOnline.setVisibility(View.GONE);
            lvNetWorkSong.setVisibility(View.VISIBLE);
        }
        else{
            lvSongOnline.setVisibility(View.VISIBLE);
            lvNetWorkSong.setVisibility(View.GONE);
            new AsynTaskSongs().execute();
        }
    }


    private class AsynTaskSongs extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (listData == null) {
                Songs_JsonPaser jsonPaser = new Songs_JsonPaser(getActivity(),"getMusicAll");
                listData = jsonPaser.getData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            adapter = new CustomListViewSongs(getActivity(), listData);
            lvSongOnline.setAdapter(adapter);
            lvSongOnline.setOnItemClickListener(new HandlerOnItemClickSongsOnline(getActivity(), listData));
        }

    }
}
