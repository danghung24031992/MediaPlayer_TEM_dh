package com.dh.mediaplayer.bean;

import android.graphics.Bitmap;

/**
 * Created by tiendat on 5/21/2015.
 */
public class Artist {
    private Bitmap imageArtist;
    private String NameArtist;
    private String descrip;

    public Artist() {
    }

    public Artist(Bitmap imageArtist, String nameArtist, String descrip) {
        this.imageArtist = imageArtist;
        NameArtist = nameArtist;
        this.descrip = descrip;
    }

    public Bitmap getImageArtist() {
        return imageArtist;
    }

    public void setImageArtist(Bitmap imageArtist) {
        this.imageArtist = imageArtist;
    }

    public String getNameArtist() {
        return NameArtist;
    }

    public void setNameArtist(String nameArtist) {
        NameArtist = nameArtist;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
