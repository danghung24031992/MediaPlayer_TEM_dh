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

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.bean.UtilsSong;
import com.dh.mediaplayer.handler.LoadImageFromURL;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//import com.dh.mediaplayer.handler.LoadImageFromURL;

/**
 * Created by DH on 5/14/2015.
 */
public class CustomListViewSongs extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;


    public CustomListViewSongs(Activity context, ArrayList<SongBean> listItem) {
        super();
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MainActivity.lstSong = listItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MainActivity.lstSong.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return MainActivity.lstSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    static class ViewHolder{
        ImageView imgArtists;
        TextView tvArtistsName;
        TextView tvArtistsDescription;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_listview_artists_online_layout, null);

            holder.imgArtists = (ImageView) convertView.findViewById(R.id.imgArtists);
            holder.tvArtistsName = (TextView) convertView.findViewById(R.id.tvArtistsName);
            holder.tvArtistsDescription = (TextView) convertView.findViewById(R.id.tvArtistsDescription);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        SongBean song = MainActivity.lstSong.get(position);
        try {
            Bitmap bitmap = new LoadImageFromURL().execute(song.getThumbImage()).get();
            if (bitmap != null){
                holder.imgArtists.setImageBitmap(bitmap);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //holder.imgArtists.setImageResource(Integer.parseInt(singer.getThumbImage()));
        holder.tvArtistsName.setText(song.getMusicName());
        holder.tvArtistsDescription.setText(song.getDescription());
        //holder.imgArtists.setImageBitmap(UtilsSong.getAlbumart(this.context,song.getAlbum_ID()));
        return convertView;
    }

}
