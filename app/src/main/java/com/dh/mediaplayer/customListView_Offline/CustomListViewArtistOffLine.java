package com.dh.mediaplayer.customListView_Offline;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.Artist;

import java.util.ArrayList;

/**
 * Created by tiendat on 5/21/2015.
 */
public class CustomListViewArtistOffLine extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<Artist> listItem;

    public CustomListViewArtistOffLine(Activity context, ArrayList<Artist> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder{

        TextView textViewNameArtistOff;
        TextView textViewDescription;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_listview_artist_offline,null);

            holder.textViewDescription = (TextView) convertView.findViewById(R.id.tvArtistsDescriptionOff);
            holder.textViewNameArtistOff = (TextView) convertView.findViewById(R.id.tvArtistsNameOff);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Artist artist = listItem.get(position);
        holder.textViewNameArtistOff.setText(artist.getNameArtist());
        holder.textViewDescription.setText(artist.getDescrip());

        return convertView;
    }
}
