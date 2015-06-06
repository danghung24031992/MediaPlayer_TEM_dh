package com.dh.mediaplayer.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dh.mediaplayer.ArtistsOfOnlineFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by DH on 5/13/2015.
 */
public class ServiceHandler {
    static String response = null;
    private Context mcontext;
    public ServiceHandler() {
    }

    public ServiceHandler(Context context) {

        this.mcontext = context;
    }

    public String getJsonData(String url){

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity=null;
            HttpResponse httpResponse=null;
            HttpPost httpPost = new HttpPost(url);
            httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
