package com.christian_hernandez.medilink.weatherapplication.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by christian_hernandez on 8/9/2018.
 */

public class Prague implements Serializable {
    private String id;
    private String main;
    private String description;
    private String icon;
    private String temp;
    private String speed;
    private String deg;
    private Bitmap icons;

    public Bitmap getIcons() {
        return icons;
    }

    public void setIcons(Bitmap icons) {
        this.icons = icons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }
}
