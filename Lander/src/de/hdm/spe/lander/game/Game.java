
package de.hdm.spe.lander.game;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public abstract class Game implements Renderer {

    private float           mDeltaTime;
    private float           mLastTime;

    protected final Context mContext;

    public Game(Context context) {
        this.mContext = context;
        this.initialize();
    }

    public abstract void initialize();

    public abstract void update(float deltaSeconds);

    public abstract void draw(float deltaSeconds);

    public abstract void loadContent();

    public abstract void resize(int width, int height);

    @Override
    public void onDrawFrame(GL10 gl) {

        long currentTime = System.currentTimeMillis();

        this.mDeltaTime = currentTime - this.mLastTime;

        float msPerFrame = 100.0f * 1f;

        if (this.mDeltaTime < msPerFrame)
        {
            try {
                Thread.sleep((long) (msPerFrame - this.mDeltaTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.update(this.mDeltaTime / 1000);
        this.draw(this.mDeltaTime);
        this.mLastTime = currentTime;

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.loadContent();
    }
}
