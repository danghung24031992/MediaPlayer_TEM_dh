package com.dh.mediaplayer.handler;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dh.mediaplayer.JsonPaser.Songs_JsonPaser;
import com.dh.mediaplayer.bean.SongBean;

import java.util.ArrayList;

/**
 * Created by DH on 25/05/2015.
 */
public class AsynTaskSongs extends AsyncTask<String, Void, ArrayList<SongBean>> {
    private ArrayList<SongBean> listData;
    private Context mContext;

    public AsynTaskSongs(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext,"loading...",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ArrayList<SongBean> doInBackground(String... params) {
        Songs_JsonPaser jsonPaser = new Songs_JsonPaser(mContext,params[0]);
        listData = jsonPaser.getData();
        return listData;
    }

    @Override
    protected void onPostExecute(ArrayList<SongBean> result) {
        super.onPostExecute(result);

    }
}
