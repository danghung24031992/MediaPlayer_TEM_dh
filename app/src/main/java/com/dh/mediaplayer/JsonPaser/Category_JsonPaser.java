package com.dh.mediaplayer.JsonPaser;

import android.content.Context;
import android.util.Log;

import com.dh.mediaplayer.bean.CategoryBean;
import com.dh.mediaplayer.network.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DH on 5/13/2015.
 */
public class Category_JsonPaser {
    public static final String TAG_ID = "CategoriesId";
    public static final String TAG_NAME = "CategoriesName";
//    public static final String TAG_NAME_EN = "CategoriesName_EN";
    public static final String TAG_ThumImage = "ThumbImage";
    public static final String TAG_DESCRIPTION = "Description";

    private Context mContext;

    private static String url = "http://mediaplayer.cf/mediaservice.svc/json/categories/9ae1b119bb11524676e75582c2c23060";

    public Category_JsonPaser(Context mContext) {
        this.mContext = mContext;
    }

    private  ArrayList<CategoryBean> listData;

    public  ArrayList<CategoryBean> getData(){

        listData = new ArrayList<CategoryBean>();

        ServiceHandler sh = new ServiceHandler(mContext);

        String jsonStr = sh.getJsonData(url);

//        Log.d("JSONNNNNNNNNNNNNNNN",jsonStr);
        if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject c = jsonArray.getJSONObject(i);

                    int id = c.getInt(TAG_ID);
                    String name = c.getString(TAG_NAME);
//                    String name_EN = c.getString(TAG_NAME_EN);

//                    String thum = c.getString(TAG_ThumImage);
                    String desciption = c.getString(TAG_DESCRIPTION);

                    CategoryBean objectBean = new CategoryBean();
                    objectBean.setCategoriesId(id);
                    objectBean.setCategoriesName(name);
//                    objectBean.setCategoriesName_EN(name_EN);
//                    objectBean.setThumbImage(thum);
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
