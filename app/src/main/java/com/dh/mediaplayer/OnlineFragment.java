package com.dh.mediaplayer;

/**
 * Created by DH on 5/12/2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OnlineFragment extends Fragment {
    private FragmentTabHost tabHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.onlinefragment);

        tabHost.addTab(tabHost.newTabSpec("song").setIndicator(this.getTabIndicator(tabHost.getContext(),R.string.title_tabs_sub_online_one)), SongsOfOnlineFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("artist").setIndicator(this.getTabIndicator(tabHost.getContext(),R.string.title_tabs_sub_online_three)), CategoryOfOnlineFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("folder").setIndicator(this.getTabIndicator(tabHost.getContext(),R.string.title_tabs_sub_online_four)), ArtistsOfOnlineFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("album").setIndicator(this.getTabIndicator(tabHost.getContext(),R.string.title_tabs_sub_online_two)), ComposerOfOnlineFragment.class, null);

        return tabHost;
    }
    private View getTabIndicator(Context context, int title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_child_navigation, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_nav);
        tv.setText(title);
        return view;
    }
}
