package com.dh.mediaplayer.bean;

import java.util.Date;

/**
 * Created by DH on 5/13/2015.
 */
public class ComposerBean {
    private int composerID;
    private String composerName;
    private String description;
    private String thumbImage;
    private String composerName_EN;

    public ComposerBean() {
    }

    public ComposerBean(int composerID, String composerName, String description, String thumbImage, String composerName_EN) {
        this.composerID = composerID;
        this.composerName = composerName;
        this.description = description;
        this.thumbImage = thumbImage;
        this.composerName_EN = composerName_EN;
    }

    public int getComposerID() {
        return composerID;
    }

    public void setComposerID(int composerID) {
        this.composerID = composerID;
    }

    public String getComposerName() {
        return composerName;
    }

    public void setComposerName(String composerName) {
        this.composerName = composerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getComposerName_EN() {
        return composerName_EN;
    }

    public void setComposerName_EN(String composerName_EN) {
        this.composerName_EN = composerName_EN;
    }
}
