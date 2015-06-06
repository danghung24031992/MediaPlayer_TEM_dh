package com.dh.mediaplayer.JsonPaser;

import android.content.Context;
import android.util.Log;

import com.dh.mediaplayer.bean.ComposerBean;
import com.dh.mediaplayer.network.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DH on 5/13/2015.
 */
public class Composer_JsonPaser {
    public static final String TAG_ID = "ComposerID";
    public static final String TAG_NAME = "ComposerName";
    public static final String TAG_NAME_EN = "ComposerName_EN";
    public static final String TAG_ThumImage = "ThumbImage";
    public static final String TAG_DESCRIPTION = "Description";

    private Context mContext;

    private static String url = "http://mediaplayer.cf/mediaservice.svc/json/composer/9ae1b119bb11524676e75582c2c23060";

    public Composer_JsonPaser(Context mContext) {
        this.mContext = mContext;
    }

    private  ArrayList<ComposerBean> listData;

    public  ArrayList<ComposerBean> getData(){

        listData = new ArrayList<ComposerBean>();

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

                    ComposerBean objectBean = new ComposerBean();
                    objectBean.setComposerID(id);
                    objectBean.setComposerName(name);
                    objectBean.setComposerName_EN(name_EN);
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
