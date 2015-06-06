package com.dh.mediaplayer.handler;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

import com.dh.mediaplayer.activity_handler_offline.Album_Action;
import com.dh.mediaplayer.activity_handler_online.Category;
import com.dh.mediaplayer.bean.Albums;
import com.dh.mediaplayer.bean.CategoryBean;
import com.dh.mediaplayer.bean.SongBean;

import java.util.ArrayList;

/**
 * Created by CHUNGNHIM on 5/29/2015.
 */
public class HandlerOnItemClickAlbumOffline implements AdapterView.OnItemClickListener {

    ArrayList<Albums> mlistData;
    private FragmentActivity fragmentActivity;

    public HandlerOnItemClickAlbumOffline(FragmentActivity activity, ArrayList<Albums> listAlbum) {
        this.fragmentActivity = activity;
        this.mlistData = listAlbum;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(fragmentActivity, Album_Action.class);
        intent.putExtra("album",mlistData.get(position).getNameAlbum());
        fragmentActivity.startActivity(intent);
    }
}
