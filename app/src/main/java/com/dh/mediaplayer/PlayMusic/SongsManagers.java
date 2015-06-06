package com.dh.mediaplayer.PlayMusic;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.dh.mediaplayer.bean.Songslocals;

import java.util.ArrayList;

/**
 * Created by tiendat on 5/24/2015.
 */
public class SongsManagers {

    /**
     * Lay all danh sach dinh dang file mp3 trong the nho ngoai
     */

    public ArrayList<Songslocals> getAllAudio(final String endWith, Context context) {
        ArrayList<Songslocals> arraySong = null;
        try {

            Songslocals objSong;
            arraySong = new ArrayList<Songslocals>();
            Uri uri;
            Cursor mCursor;

            String[] m_data = {MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.TRACK,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DATA};

            //Lay danh sach file nhac trong bo nho may
            uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
            mCursor = context.getContentResolver().query(uri, m_data,null, null, null);;

            if (mCursor != null) {
                for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                    if(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)).indexOf(endWith)>0)
                    {
                        objSong = new Songslocals();
                        objSong.setMusicName(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                        objSong.setNameArtist(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                        objSong.setLink(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                        arraySong.add(objSong);
                    }
                }
            }
            //Lay danh sach file nhac trong the nho ngoai
            uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            mCursor = context.getContentResolver().query(uri, m_data, null, null, null);;

            if (mCursor != null) {
                for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                    if(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)).indexOf(endWith)>0)
                    {
                        objSong = new Songslocals();
                        objSong.setMusicName(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                        objSong.setNameArtist(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                        objSong.setLink(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                        arraySong.add(objSong);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return arraySong;
    }
}
