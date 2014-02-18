
package de.hdm.spe.lander.graphics;

import android.opengl.GLSurfaceView.Renderer;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


@Deprecated
public class MGDRenderer implements Renderer {

    private float mLastTime;
    private float mDeltaTime;

    @Override
    public void onDrawFrame(GL10 gl) {
        long currentTime = System.currentTimeMillis();

        this.mDeltaTime = currentTime - this.mLastTime;

        float msPerFrame = 1000.0f * 1f;

        if (this.mDeltaTime < msPerFrame)
        {
            try {
                Thread.sleep((long) (msPerFrame - this.mDeltaTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.loop(gl);
        this.mLastTime = currentTime;

    }

    private void loop(GL10 gl) {

        float value = new Random().nextFloat();
        //        Log.d(this.getClass().getName(), "Value " + value);
        gl.glClearColor(value * 1, value * 1, value * 1, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

}
