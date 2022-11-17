package com.example.paroapplication.bean;

import android.graphics.drawable.Drawable;

public class Color {
    private Drawable color;
    private int type;
    public boolean isSelect;

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public Color(Drawable color, int type) {
        this.color = color;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Drawable getColor() {
        return color;
    }

    public void setColor(Drawable color) {
        this.color = color;
    }
}
