
package de.hdm.spe.lander.graphics;

import android.graphics.Bitmap;


public class Texture {

    private final int handle;
    private final int width, height;
    private Bitmap    mBitmap;

    Texture(int handle, int width, int height) {
        this.handle = handle;
        this.width = width;
        this.height = height;
    }

    Texture(int handle, int width, int height, Bitmap bmp) {
        this.handle = handle;
        this.width = width;
        this.height = height;
        this.mBitmap = bmp;
    }

    public void setBitmap(Bitmap bmp) {
        this.mBitmap = bmp;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    int getHandle() {
        return this.handle;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

}
