
package de.hdm.spe.lander.graphics;

public class Texture {

    private final int mHeight;
    private final int mWidth;
    private final int mHandle;

    Texture(int width, int height, int handle) {
        this.mHeight = height;
        this.mWidth = width;
        this.mHandle = handle;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    int getHandle() {
        return this.mHandle;
    }

}
