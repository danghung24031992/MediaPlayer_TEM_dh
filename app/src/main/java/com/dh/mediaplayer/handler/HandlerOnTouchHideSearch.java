package com.dh.mediaplayer.handler;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dh.mediaplayer.SongsOfOnlineFragment;

/**
 * Created by DH on 5/13/2015.
 */
public class HandlerOnTouchHideSearch implements View.OnTouchListener {
    private FragmentActivity mactivity;
    private View lvOnline;
    private LinearLayout linearLayoutSearch;
    final int DISTANCE = 2;

    float startY = 0;
    float dist = 0;
    boolean isMenuHide = false;
    public HandlerOnTouchHideSearch(FragmentActivity activity, View lv, LinearLayout Search) {
        this.mactivity = activity;
        this.lvOnline = lv;
        this.linearLayoutSearch = Search;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN) {
            startY = event.getY();
        } else if(action == MotionEvent.ACTION_MOVE) {
            dist = event.getY() - startY;

            if((pxToDp((int)dist) <= -DISTANCE) && !isMenuHide) {
                isMenuHide = true;
                hideMenuBar();
            } else if((pxToDp((int)dist) > DISTANCE) && isMenuHide) {
                isMenuHide = false;
                showMenuBar();
            }

            if((isMenuHide && (pxToDp((int)dist) <= -DISTANCE))
                    || (!isMenuHide && (pxToDp((int)dist) > 0))) {
                startY = event.getY();
            }
        } else if(action == MotionEvent.ACTION_UP) {
            startY = 0;
        }
        return false;
    }
    public int pxToDp(int px) {
        DisplayMetrics dm = mactivity.getResources().getDisplayMetrics();
        int dp = Math.round(px / (dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public void showMenuBar() {
        AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(linearLayoutSearch, View.TRANSLATION_Y, 0);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(lvOnline, View.TRANSLATION_Y, 0);
        animSet.playTogether(anim1,anim2);
        animSet.setDuration(300);
        animSet.start();

    }

    public void hideMenuBar() {
        AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(linearLayoutSearch, View.TRANSLATION_Y, -linearLayoutSearch.getHeight());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(lvOnline, View.TRANSLATION_Y, -linearLayoutSearch.getHeight());
        animSet.playTogether(anim1,anim2);
        animSet.setDuration(300);
        animSet.start();

    }
}
