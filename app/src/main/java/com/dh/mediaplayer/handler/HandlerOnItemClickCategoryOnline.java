package com.dh.mediaplayer.handler;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dh.mediaplayer.activity_handler_online.Artist;
import com.dh.mediaplayer.activity_handler_online.Category;
import com.dh.mediaplayer.bean.CategoryBean;

import java.util.ArrayList;

/**
 * Created by DH on 5/14/2015.
 */
public class HandlerOnItemClickCategoryOnline implements AdapterView.OnItemClickListener {
    ArrayList<CategoryBean> mlistData;
    private FragmentActivity fragmentActivity;

    public HandlerOnItemClickCategoryOnline(FragmentActivity activity, ArrayList<CategoryBean> listData) {
        this.fragmentActivity = activity;
        this.mlistData = listData;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.d("EEEEEEEEEEEEEEEE", mlistData.get(position).getCategoriesName());

        Intent intent = new Intent(fragmentActivity, Category.class);
        intent.putExtra("ID",mlistData.get(position).getCategoriesId());
        fragmentActivity.startActivity(intent);
    }
}
