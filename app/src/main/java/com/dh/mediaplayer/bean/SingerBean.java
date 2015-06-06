package com.dh.mediaplayer.bean;

/**
 * Created by DH on 5/13/2015.
 */
public class SingerBean {
    //Khai bao lai thumImage tu kieu String sang kieu Bitmap

    private int singerID;
    private String singerName;
    private String thumbImage;
    private String singerName_EN;
    private String description;

    public SingerBean() {
    }

    public SingerBean(int singerID, String singerName, String thumbImage, String singerName_EN, String description) {
        this.singerID = singerID;
        this.singerName = singerName;
        this.thumbImage = thumbImage;
        this.singerName_EN = singerName_EN;
        this.description = description;
    }

    public int getSingerID() {
        return singerID;
    }

    public void setSingerID(int singerID) {
        this.singerID = singerID;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getSingerName_EN() {
        return singerName_EN;
    }

    public void setSingerName_EN(String singerName_EN) {
        this.singerName_EN = singerName_EN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
