package com.dh.mediaplayer.bean;

/**
 * Created by DH on 21/05/2015.
 */
public class PlaylistOff {
    private long id;
    private String namePlaylist;

    public PlaylistOff() {
    }

    public PlaylistOff(int id, String namePlaylist) {
        this.id = id;
        this.namePlaylist = namePlaylist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }
}
