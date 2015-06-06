package com.dh.mediaplayer;

/**
 * Created by DH on 5/12/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dh.mediaplayer.playlist.List_PlayList;
import com.dh.mediaplayer.playlist.List_baihat;

public class HomeFragment extends Fragment {

    private LinearLayout linearLayout_playlist;
   // private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.homefragment, container, false);

        linearLayout_playlist = (LinearLayout) V.findViewById(R.id.layout_playlistOffline);
        //layout = (LinearLayout) V.findViewById(R.id.baihat);

        linearLayout_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), List_PlayList.class);
                startActivity(i);
            }
        });

//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(getActivity(), List_baihat.class);
//                startActivity(a);
//            }
//        });
        return V;
    }
}
