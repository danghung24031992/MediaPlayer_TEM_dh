package com.dh.mediaplayer.JsonPaser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.dh.mediaplayer.bean.SingerBean;
import com.dh.mediaplayer.handler.LoadImageFromURL;
import com.dh.mediaplayer.network.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by DH on 5/13/2015.
 */
public class Singer_JsonPaser {
    private Context mContext;

    public static final String TAG_ID = "SingerID";
    public static final String TAG_NAME = "SingerName";
    public static final String TAG_NAME_EN = "SingerName_EN";
    public static final String TAG_ThumImage = "ThumbImage";
    public static final String TAG_DESCRIPTION = "Description";

    private static String url = "http://mediaplayer.cf/mediaservice.svc/json/singer/9ae1b119bb11524676e75582c2c23060";

    public Singer_JsonPaser(Context mContext) {
        this.mContext = mContext;
    }

    private  ArrayList<SingerBean> listData;

    public  ArrayList<SingerBean> getData(){

        listData = new ArrayList<SingerBean>();

        ServiceHandler sh = new ServiceHandler(mContext);

        String jsonStr = sh.getJsonData(url);
        if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject c = jsonArray.getJSONObject(i);

                    int id = c.getInt(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String name_EN = c.getString(TAG_NAME_EN);

                    String thum = c.getString(TAG_ThumImage);
                    String desciption = c.getString(TAG_DESCRIPTION);

                    SingerBean objectBean = new SingerBean();
                    objectBean.setSingerID(id);
                    objectBean.setSingerName(name);
                    objectBean.setSingerName_EN(name_EN);
                    objectBean.setThumbImage(thum);
                    objectBean.setDescription(desciption);

                    listData.add(objectBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ServiceHandler", "Not get Data");
        }

        return listData;
    }
}
