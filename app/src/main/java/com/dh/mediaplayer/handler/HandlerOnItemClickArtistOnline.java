package com.dh.mediaplayer.handler;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dh.mediaplayer.activity_handler_online.Artist;
import com.dh.mediaplayer.bean.SingerBean;

import java.util.ArrayList;

/**
 * Created by DH on 5/14/2015.
 */
public class HandlerOnItemClickArtistOnline implements AdapterView.OnItemClickListener {
    ArrayList<SingerBean> mlistData;
    private FragmentActivity fragmentActivity;

    public HandlerOnItemClickArtistOnline(FragmentActivity activity, ArrayList<SingerBean> listData) {
        this.fragmentActivity = activity;
        this.mlistData = listData;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.d("EEEEEEEEEEEEEEEE", mlistData.get(position).getSingerName());
        //load danh sach nhac theo tung ca si

        Intent intent = new Intent(fragmentActivity, Artist.class);
        intent.putExtra("ID",mlistData.get(position).getSingerID());
        fragmentActivity.startActivity(intent);
    }
}
