package com.dh.mediaplayer;

/**
 * Created by DH on 5/12/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dh.mediaplayer.bean.Albums;
import com.dh.mediaplayer.bean.UtilsAlbum;
import com.dh.mediaplayer.customListView_Offline.Custom_ListView_Album;
import com.dh.mediaplayer.handler.HandlerOnItemClickAlbumOffline;

import java.util.ArrayList;

public class AlbumsOfLocalFragment extends Fragment {
    private ListView listViewAlbum;
    Custom_ListView_Album adapter;
    private ArrayList<Albums> listAlbum;
    private FragmentActivity fragmentActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.album_child_of_localfragment, container, false);
        listViewAlbum = (ListView)V.findViewById(R.id.listAlbum);
        UtilsAlbum utilsAlbum = new UtilsAlbum();

        listAlbum = utilsAlbum.getAllAudio(".mp3",getActivity());
        adapter = new Custom_ListView_Album(getActivity(),listAlbum);
        listViewAlbum.setAdapter(adapter);
        listViewAlbum.setOnItemClickListener(new HandlerOnItemClickAlbumOffline(getActivity(),listAlbum));
        return V;
    }


}
