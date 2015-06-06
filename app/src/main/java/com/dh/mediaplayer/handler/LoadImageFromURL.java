package com.dh.mediaplayer.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DH on 23/05/2015.
 */
public class LoadImageFromURL extends AsyncTask<String,Void,Bitmap> {
    Bitmap bitMap;
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            InputStream is = url.openConnection().getInputStream();
            bitMap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitMap;
    }
}
