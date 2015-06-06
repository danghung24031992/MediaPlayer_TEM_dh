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

import com.dh.mediaplayer.JsonPaser.Category_JsonPaser;
import com.dh.mediaplayer.bean.CategoryBean;
import com.dh.mediaplayer.custom_online.CustomCategoryOnline;
import com.dh.mediaplayer.handler.HandlerOnItemClickCategoryOnline;
import com.dh.mediaplayer.handler.HandlerOnTouchHideSearch;
import com.dh.mediaplayer.network.ConnectionNetwork;

import java.util.ArrayList;

public class CategoryOfOnlineFragment extends Fragment {
    GridView gvCategoryOnline;
    private LinearLayout linearBXHSearch;
    private ConnectionNetwork connect;
    private Boolean checkNetWork = false;
    private ImageButton imageBNetWork;
    CustomCategoryOnline adapter;
    ArrayList<CategoryBean> listData;
    private LinearLayout lvNetWorkCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.category_child_of_online_fragment, container, false);
        gvCategoryOnline = (GridView)V.findViewById(R.id.gvCategoryOnline);
        //linearBXHSearch = (LinearLayout)V.findViewById(R.id.searchCategoryOnline);
        //gvCategoryOnline.setOnTouchListener(new HandlerOnTouchHideSearch(getActivity(), gvCategoryOnline, linearBXHSearch));
        imageBNetWork = (ImageButton)V.findViewById(R.id.imgBCheckNetworkCategory);
        lvNetWorkCategory = (LinearLayout)V.findViewById(R.id.lvNetWorkCategory);

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
            gvCategoryOnline.setVisibility(View.GONE);
            lvNetWorkCategory.setVisibility(View.VISIBLE);
        }
        else{
            gvCategoryOnline.setVisibility(View.VISIBLE);
            lvNetWorkCategory.setVisibility(View.GONE);
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
                Category_JsonPaser jsonPaser = new Category_JsonPaser(getActivity());
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
            adapter = new CustomCategoryOnline(getActivity(), listData);
            gvCategoryOnline.setAdapter(adapter);
            gvCategoryOnline.setOnItemClickListener(new HandlerOnItemClickCategoryOnline(getActivity(), listData));
        }

    }
}
