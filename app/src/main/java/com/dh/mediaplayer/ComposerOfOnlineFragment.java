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

import com.dh.mediaplayer.JsonPaser.Composer_JsonPaser;
import com.dh.mediaplayer.bean.ComposerBean;
import com.dh.mediaplayer.custom_online.CustomComposerOnline;
import com.dh.mediaplayer.handler.HandlerOnItemClickComposerOnline;
import com.dh.mediaplayer.handler.HandlerOnTouchHideSearch;
import com.dh.mediaplayer.network.ConnectionNetwork;

import java.util.ArrayList;

public class ComposerOfOnlineFragment extends Fragment {
    ArrayList<ComposerBean> listData;
    private GridView gvComposerOnline;
    private LinearLayout lvNetWork;
    private ImageButton imageBNetWork;
    private ConnectionNetwork connect;
    private LinearLayout linearLayoutSearchComposer;
    private CustomComposerOnline adapter;
    private Boolean checkNetWork = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.composer_child_of_online_fragment, container, false);

        gvComposerOnline = (GridView)V.findViewById(R.id.gridViewComposer);
        lvNetWork = (LinearLayout)V.findViewById(R.id.lvNetWorkComposer);
        imageBNetWork = (ImageButton)V.findViewById(R.id.imgBCheckNetworkComposer);
        //linearLayoutSearchComposer = (LinearLayout)V.findViewById(R.id.linearLayoutSearchComposer);
        //gvComposerOnline.setOnTouchListener(new HandlerOnTouchHideSearch(getActivity(), gvComposerOnline, linearLayoutSearchComposer));

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
            lvNetWork.setVisibility(View.VISIBLE);
            gvComposerOnline.setVisibility(View.GONE);
        }
        else{
            lvNetWork.setVisibility(View.GONE);
            gvComposerOnline.setVisibility(View.VISIBLE);
            new AsynTaskArtists().execute();
        }

    }

    private class AsynTaskArtists extends AsyncTask<Void,Void,ArrayList<ComposerBean>> {
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
        protected ArrayList<ComposerBean> doInBackground(Void... params) {
            if (listData == null){
                Composer_JsonPaser composer_jsonPaser = new Composer_JsonPaser(getActivity());
                listData = composer_jsonPaser.getData();
            }

            return listData;
        }
        @Override
        protected void onPostExecute(ArrayList<ComposerBean> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter = new CustomComposerOnline(getActivity(),listData);
            gvComposerOnline.setAdapter(adapter);
            gvComposerOnline.setOnItemClickListener(new HandlerOnItemClickComposerOnline(getActivity(), listData));
        }
    }
}
