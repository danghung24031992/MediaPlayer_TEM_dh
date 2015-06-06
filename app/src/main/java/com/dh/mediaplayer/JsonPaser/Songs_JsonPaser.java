package com.dh.mediaplayer.JsonPaser;

import android.content.Context;
import android.util.Log;

import com.dh.mediaplayer.bean.SingerBean;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.network.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DH on 5/13/2015.
 */
public class Songs_JsonPaser {
    private Context mContext;
    private  ArrayList<SongBean> listData;

    public static final String TAG_ID = "MusicID";
    public static final String TAG_NAME = "MusicName";
    public static final String TAG_NAME_EN = "Music_EN";
    public static final String TAG_LINK = "Link";
    public static final String TAG_SINGER_ID = "SingerID";
    public static final String TAG_ThumImage = "ThumbImage";
    public static final String TAG_CATEGORY_ID = "CategoriesId";
    public static final String TAG_COMPOSER_ID = "ComposerID";
    public static final String TAG_DESCRIPTION = "Description";

    private static String url;

    public Songs_JsonPaser(Context mContext, String str) {
        this.mContext = mContext;
        url = "http://mediaplayer.cf/mediaservice.svc/json/"+str+"/9ae1b119bb11524676e75582c2c23060";
    }


    public  ArrayList<SongBean> getData(){

        listData = new ArrayList<SongBean>();

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
                    String link = c.getString(TAG_LINK);
                    int singer_id = c.getInt(TAG_SINGER_ID);
                    String thum = c.getString(TAG_ThumImage);
                    int categories_id = c.getInt(TAG_CATEGORY_ID);
                    int composer_id = c.getInt(TAG_COMPOSER_ID);
                    String desciption = c.getString(TAG_DESCRIPTION);

                    SongBean objectBean = new SongBean();
                    objectBean.setMusicID(id);
                    objectBean.setMusicName(name);
                    objectBean.setMusic_EN(name_EN);
                    objectBean.setLink(link);
                    objectBean.setSingerID(singer_id);
                    objectBean.setThumbImage(thum);
                    objectBean.setCategoriesId(categories_id);
                    objectBean.setComposerID(composer_id);
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
