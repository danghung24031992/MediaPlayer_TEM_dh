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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dh.mediaplayer.JsonPaser.Singer_JsonPaser;
import com.dh.mediaplayer.bean.SingerBean;
import com.dh.mediaplayer.custom_online.CustomArtistsOnline;
import com.dh.mediaplayer.handler.HandlerOnItemClickArtistOnline;
import com.dh.mediaplayer.handler.HandlerOnTouchHideSearch;
import com.dh.mediaplayer.network.ConnectionNetwork;

import java.util.ArrayList;

public class ArtistsOfOnlineFragment extends Fragment {
    ArrayList<SingerBean> listData;
    GridView gvArtistsOnline;
    private LinearLayout lvNetWorkArtist;
    private ImageButton imageBNetWork;
    private ConnectionNetwork connect;
    private LinearLayout linearArtistSearch;
    CustomArtistsOnline adapter;
    private Boolean checkNetWork = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.artist_child_of_online_fragment, container, false);

        gvArtistsOnline = (GridView)V.findViewById(R.id.gvArtists);
        lvNetWorkArtist = (LinearLayout)V.findViewById(R.id.lvNetWorkArtist);
        imageBNetWork = (ImageButton)V.findViewById(R.id.imgBCheckNetworkArtist);
        //linearArtistSearch = (LinearLayout)V.findViewById(R.id.searchArtistsOnline);
        //gvArtistsOnline.setOnTouchListener(new HandlerOnTouchHideSearch(getActivity(), gvArtistsOnline, linearArtistSearch));

        iNetwork();
        imageBNetWork.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageBNetWork.setBackgroundColor(Color.GRAY);
                    iNetwork();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
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
            lvNetWorkArtist.setVisibility(View.VISIBLE);
            gvArtistsOnline.setVisibility(View.GONE);

        }
        else{
            lvNetWorkArtist.setVisibility(View.GONE);
            gvArtistsOnline.setVisibility(View.VISIBLE);
            new AsynTaskArtists().execute();
        }

    }

    private class AsynTaskArtists extends AsyncTask<Void,Void,ArrayList<SingerBean>> {
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
        protected ArrayList<SingerBean> doInBackground(Void... params) {
            if (listData == null){
                Singer_JsonPaser singer = new Singer_JsonPaser(getActivity());
                listData = singer.getData();
            }

            return listData;
        }
        @Override
        protected void onPostExecute(ArrayList<SingerBean> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter = new CustomArtistsOnline(getActivity(),listData);
            gvArtistsOnline.setAdapter(adapter);
            gvArtistsOnline.setOnItemClickListener(new HandlerOnItemClickArtistOnline(getActivity(), listData));
        }
    }
}
