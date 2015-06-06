package com.dh.mediaplayer.PlayMusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.Songslocals;

import java.util.ArrayList;

/**
 * Created by tiendat on 5/24/2015.
 */
public class CustomListViewSongs extends ArrayAdapter<Songslocals> {
    private Context mcontext;
    private LayoutInflater inflater;
    private ArrayList<Songslocals> mSongslocalses;

private  ListView listView;

    public CustomListViewSongs(Context context, int resource, ArrayList<Songslocals> objects) {
        super(context, resource, objects);
        mcontext = context;
        mSongslocalses = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




 public   static class ViewHolder {

        TextView tvArtistsName;
        TextView tvTitleSong;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.playlist_item,null);

            holder.tvArtistsName = (TextView) convertView.findViewById(R.id.songArtistName);
            holder.tvTitleSong = (TextView) convertView.findViewById(R.id.songTitle);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Songslocals songslocals = mSongslocalses.get(position);
        holder.tvTitleSong.setText(songslocals.getMusicName());
        holder.tvArtistsName.setText(songslocals.getNameArtist());

        return convertView;
    }
}
