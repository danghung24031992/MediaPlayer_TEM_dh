package com.dh.mediaplayer.custom_online;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;

/**
 * Created by CHUNGNHIM on 5/14/2015.
 */
public class CustomListViewSongLocal extends BaseAdapter {
    UtilsSong utilsSong = new UtilsSong();
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<SongBean> listItem;


    public CustomListViewSongLocal(Activity context, ArrayList<SongBean> listItem) {
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
        TextView tvArtistsDescription;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_listview_album, null);

            holder.imgArtists = (ImageView) convertView.findViewById(R.id.imgArtistsOffline);
            holder.tvArtistsName = (TextView) convertView.findViewById(R.id.tvArtistsNameOff);
            holder.tvArtistsDescription = (TextView) convertView.findViewById(R.id.tvArtistsDescriptionOff);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SongBean songLocal = listItem.get(position);

        holder.tvArtistsName.setText(songLocal.getMusicName());
        holder.tvArtistsDescription.setText(songLocal.getArtist());
        if (songLocal.getAlbumImage()!= null){
            holder.imgArtists.setImageBitmap(songLocal.getAlbumImage());
        }
        return convertView;
    }
}
