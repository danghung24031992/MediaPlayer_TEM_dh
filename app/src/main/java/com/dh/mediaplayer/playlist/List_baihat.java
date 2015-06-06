package com.dh.mediaplayer.playlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dh.mediaplayer.R;

/**
 * Created by tiendat on 5/29/2015.
 */
public class List_baihat extends Activity {
    private LinearLayout baihat1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.list_baihat);
        super.onCreate(savedInstanceState);
        baihat1 = (LinearLayout) findViewById(R.id.layout_baihat);
        baihat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addSong(View view) {
        Intent add = new Intent(List_baihat.this, ActivityAddSongs.class);
        startActivity(add);
    }
}
