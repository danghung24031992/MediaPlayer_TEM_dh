package com.dh.mediaplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.bean.UtilsSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiendat on 5/4/2015.
 * lop nay se dung de tao sua xoa database
 */
public class MyDataHelper {
    //khai bao thong tin ve DB
    public static final String DB_NAME = "mediaplayer.db";
    public static final String TB_NAME = "playlist";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase database;
    private Context mContext;

    //Context la bien ngu canh
    public MyDataHelper(Context context) {
        this.mContext = context;
        OpenHelper helper = new OpenHelper(mContext);
        database = helper.getWritableDatabase();
    }

    //buoc 4 : tao ra 1 lop noi de thuc hien viec tao database
    public class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            //dinh nghia OpenHelper

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE IF NOT EXISTS playlist(_id INTEGER PRIMARY KEY AUTOINCREMENT ,Name TEXT)";
            String createTableMusic = "CREATE TABLE IF NOT EXISTS listmuisc(_id INTEGER PRIMARY KEY AUTOINCREMENT ,MusicName TEXT, AlbumID INTEGER, AlbumName TEXT, Link TEXT, playlistid INTEGER)";
            db.execSQL(createTable);
            db.execSQL(createTableMusic);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS playlist");
            db.execSQL("DROP TABLE IF EXISTS listmuisc");
            onCreate(db);
        }
    }

    //buoc 5 : xay dung tat ca cac phuong thuc lam viec voi DB trong du an
    //insert
    public void insertData(String name) {
        ContentValues data = new ContentValues();
        data.put("Name", name);

        database.insertOrThrow(TB_NAME, null, data);
    }

    public void deleteData(int _id) {
        database.delete(TB_NAME, "_id = " + _id, null);
    }

    //method get data
    public Cursor getData() {
        try {

            return database.query(TB_NAME, null, null, null, null, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void updateData(String name, int _id) {
        ContentValues data = new ContentValues();
        data.put("Name", name);
        database.update(TB_NAME, data, "_id = " + _id, null);
    }

    public void deleteDataMusic(int _id) {
        database.delete("listmuisc", "playlistid = " + _id, null);
    }

    public void insertDataMusic(String MusicName, long AlbumID, String AlbumName, String Link, int playlistid) {

        ContentValues data = new ContentValues();
        data.put("MusicName", MusicName);
        data.put("AlbumID", AlbumID);
        data.put("AlbumName", AlbumName);
        data.put("Link", Link);
        data.put("playlistid", playlistid);

        database.insertOrThrow("listmuisc", null, data);
    }

    public ArrayList<SongBean> getMusicByPlayListId(int playlistId) {
        try {
            ArrayList<SongBean> lstSong = new ArrayList<SongBean>();
            Cursor mCursor = database.query("listmuisc", null, null, null, null, null, null);
            if (mCursor!=null) {
                mCursor.moveToPosition(0);
                while (!mCursor.isAfterLast()) {
                    SongBean objSong = new SongBean();
                    objSong.setMusicName(mCursor.getString(mCursor.getColumnIndexOrThrow("MusicName")));
                    objSong.setAlbumName(mCursor.getString(mCursor.getColumnIndex("AlbumName")));
                    objSong.setLink(mCursor.getString(mCursor.getColumnIndexOrThrow("Link")));
                    objSong.setAlbum_ID(mCursor.getLong(mCursor.getColumnIndex("AlbumID")));
                    objSong.setAlbumImage(UtilsSong.getAlbumart(mContext, objSong.getAlbum_ID()));
                    lstSong.add(objSong);
                    mCursor.moveToNext();
                }
            }
            return lstSong;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
