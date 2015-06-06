package com.dh.mediaplayer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // put the splash screen into full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        buildTabs();
    }
    private void buildTabs() {
        // get a reference to the tab host
        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);

        // set it up
        tabHost.setup();

        // create the easy tab
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Cua Tui");
        spec1.setIndicator(this.getTabIndicator(tabHost.getContext(), R.string.title_tabs_nav_one,R.drawable.img_tabs_one));
//        Intent intent1 = new Intent(this,Tab1.class);
//        spec1.setContent(intent1);
        spec1.setContent(R.id.tab1);

        // create the hard tab
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Thiet Bi");
        spec2.setIndicator(this.getTabIndicator(tabHost.getContext(), R.string.title_tabs_nav_two,R.drawable.img_tabs_two));
//        Intent intent2 = new Intent(this,Tab2.class);
//        spec2.setContent(intent2);
        spec2.setContent(R.id.tab2);

        // create the settings tab
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Online");
        spec3.setIndicator(this.getTabIndicator(tabHost.getContext(), R.string.title_tabs_nav_three,R.drawable.img_tabs_three));
//        Intent intent3 = new Intent(this,Tab3.class);
//        spec3.setContent(intent3);
        spec3.setContent(R.id.tab3);

        // add them to the tab host
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
    }

    /**
     * Inflates the tab indicator, sets the caption and returns the new view.
     * @param context
     * @param title
     * @return
     */
    private View getTabIndicator(Context context, int title,int img) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tabs_navigation, null);
        TextView tv = (TextView) view.findViewById(R.id.txt_tabs_nav);
        ImageView imgv = (ImageView) view.findViewById(R.id.imgv_tabs_nav);

        tv.setText(title);
        imgv.setImageResource(img);
        return view;
    }

}
