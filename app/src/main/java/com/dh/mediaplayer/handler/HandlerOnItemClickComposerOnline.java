package com.dh.mediaplayer.handler;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

import com.dh.mediaplayer.activity_handler_online.Artist;
import com.dh.mediaplayer.activity_handler_online.Composer;
import com.dh.mediaplayer.bean.ComposerBean;

import java.util.ArrayList;

/**
 * Created by DH on 5/14/2015.
 */
public class HandlerOnItemClickComposerOnline implements AdapterView.OnItemClickListener {
    ArrayList<ComposerBean> mlistData;
    private FragmentActivity fragmentActivity;

    public HandlerOnItemClickComposerOnline(FragmentActivity activity, ArrayList<ComposerBean> listData) {
        this.fragmentActivity = activity;
        this.mlistData = listData;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.d("EEEEEEEEEEEEEEEE", mlistData.get(position).getSingerName());
        //load danh sach nhac theo tung ca si

        Intent intent = new Intent(fragmentActivity, Composer.class);
        intent.putExtra("ID",mlistData.get(position).getComposerID());
        fragmentActivity.startActivity(intent);
    }
}
