package com.example.aditya.videoapp;

/**
 * Created by aditya on 3/23/2017.
 */

public class mApps {
    private String title,desc,image;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public mApps() {

    }

    public mApps(String desc, String image, String title) {
        this.desc = desc;
        this.image = image;
        this.title = title;
    }
}
