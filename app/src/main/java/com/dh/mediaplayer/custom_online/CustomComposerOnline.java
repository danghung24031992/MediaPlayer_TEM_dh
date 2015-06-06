package com.dh.mediaplayer.custom_online;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.ComposerBean;
import com.dh.mediaplayer.bean.SingerBean;
import com.dh.mediaplayer.handler.LoadImageFromURL;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by DH on 5/14/2015.
 */
public class CustomComposerOnline extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<ComposerBean> listItem;


    public CustomComposerOnline(Activity context, ArrayList<ComposerBean> listItem) {
        super();
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    static class ViewHolder {
        ImageView imgArtists;
        TextView tvArtistsName;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_grid_view, null);

            holder.imgArtists = (ImageView) convertView.findViewById(R.id.imgArtists);
            holder.tvArtistsName = (TextView) convertView.findViewById(R.id.tvArtistsName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ComposerBean composerBean = listItem.get(position);
        try {
            Bitmap bitmap = new LoadImageFromURL().execute(composerBean.getThumbImage()).get();
            if (bitmap != null){
                holder.imgArtists.setImageBitmap(bitmap);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Picasso.with(context).load(singer.getThumbImage()).into(holder.imgArtists);

//        holder.imgArtists.setImageResource(Integer.parseInt(singer.getThumbImage()));
        holder.tvArtistsName.setText(composerBean.getComposerName());

        return convertView;
    }
}
