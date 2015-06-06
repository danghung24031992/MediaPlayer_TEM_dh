package com.dh.mediaplayer;

/**
 * Created by DH on 5/12/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dh.mediaplayer.bean.Artist;
import com.dh.mediaplayer.bean.UtilsArtist;
import com.dh.mediaplayer.customListView_Offline.CustomListViewArtistOffLine;

import java.util.ArrayList;

public class ArtistsOfLocalFragment extends Fragment {

    private ListView listViewArtist;
    CustomListViewArtistOffLine adapter;
    private ArrayList<Artist> listArtist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.artist_child_of_localfragment, container, false);
        listViewArtist = (ListView)V.findViewById(R.id.listViewArtist);
        UtilsArtist utilsArtist = new UtilsArtist();
        listArtist = utilsArtist.getAllAudio(".mp3",getActivity());
        adapter = new CustomListViewArtistOffLine(getActivity(),listArtist);
        listViewArtist.setAdapter(adapter);
        return V;
    }
}
