package com.training.digginggame.object;

import android.graphics.Bitmap;

public class Item {
    public int x;
    public int y;
    public String name;
    public int value;
    public Bitmap texture;
    public boolean isFound;
    public int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public void setTexture(Bitmap texture) {
        this.texture = texture;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    @Override
    public String toString() {
        return id + " : " + name + ", " + value;
    }
}
