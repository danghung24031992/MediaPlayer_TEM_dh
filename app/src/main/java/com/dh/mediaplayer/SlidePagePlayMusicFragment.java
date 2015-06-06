package com.dh.mediaplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dh.mediaplayer.R;

/**
 * Created by DH on 26/05/2015.
 */
public class SlidePagePlayMusicFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_playmusic_play, container, false);

        return rootView;
    }
}
