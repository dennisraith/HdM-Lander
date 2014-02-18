
package de.hdm.spe.lander.game;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.view.View;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.input.InputSystem;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public abstract class Game implements Renderer {

    private boolean                               initialized;
    private long                                  lastTime;

    protected View                                view;
    protected Context                             context;
    protected InputSystem                         inputSystem;
    protected GraphicsDevice                      graphicsDevice;
    protected de.hdm.spe.lander.graphics.Renderer renderer;

    protected int                                 screenWidth;
    protected int                                 screenHeight;

    public Game(View view) {
        this.view = view;
        this.context = view.getContext();

        this.inputSystem = new InputSystem(view);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        long currTime = System.currentTimeMillis();
        float deltaSeconds = (currTime - this.lastTime) / 1000.0f;

        this.update(deltaSeconds);
        this.draw(deltaSeconds);

        this.lastTime = currTime;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.graphicsDevice.resize(width, height);

        this.screenWidth = width;
        this.screenHeight = height;

        this.resize(width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.lastTime = System.currentTimeMillis();

        if (!this.initialized) {
            this.graphicsDevice = new GraphicsDevice();
            this.graphicsDevice.onSurfaceCreated(gl);

            this.renderer = new de.hdm.spe.lander.graphics.Renderer(this.graphicsDevice);

            this.initialize();
            this.initialized = true;

            this.loadContent();
        } else {
            this.graphicsDevice.onSurfaceCreated(gl);
            this.loadContent();
        }
    }

    public abstract void initialize();

    public abstract void loadContent();

    public abstract void update(float deltaSeconds);

    public abstract void draw(float deltaSeconds);

    public abstract void resize(int width, int height);

    public abstract void pause();

    public abstract void resume();
}
