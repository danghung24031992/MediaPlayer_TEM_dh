package com.dh.mediaplayer.bean;

/**
 * Created by tiendat on 5/24/2015.
 */
public class Songslocals {
     private String musicName;
    private String NameArtist;
    private String link;

    public Songslocals() {
    }

    public Songslocals(String musicName, String nameArtist, String link) {
        this.musicName = musicName;
        NameArtist = nameArtist;
        this.link = link;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getNameArtist() {
        return NameArtist;
    }

    public void setNameArtist(String nameArtist) {
        NameArtist = nameArtist;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
