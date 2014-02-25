
package de.hdm.spe.lander.game;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.view.View;
import android.widget.Toast;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.models.GameStateChangedListener;
import de.hdm.spe.lander.models.InputEventManager;
import de.hdm.spe.lander.states.GameState;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public abstract class Game implements Renderer, GameStateChangedListener {

    private boolean                               initialized;
    private long                                  lastTime;

    protected View                                view;
    protected Context                             context;
    protected InputEventManager                   mInputManager;
    protected GraphicsDevice                      graphicsDevice;
    protected de.hdm.spe.lander.graphics.Renderer renderer;

    protected int                                 screenWidth;
    protected int                                 screenHeight;
    protected GameState                           mCurrentState;
    private boolean                               isPaused = false;

    public abstract void initialize();

    public Game(View view) {
        this.view = view;
        this.context = view.getContext();

        this.mInputManager = new InputEventManager(this, view);
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

    public void loadContent() {
        try {
            this.mCurrentState.prepare(this.context, this.graphicsDevice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(float deltaSeconds) {
        this.mInputManager.check();
        if (!this.isPaused) {
            this.mCurrentState.update(deltaSeconds);
        }
    }

    public void draw(float deltaSeconds) {
        this.graphicsDevice.clear(0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
        this.graphicsDevice.setCamera(this.mCurrentState.getCamera());
        this.mCurrentState.draw(deltaSeconds, this.renderer);
    }

    public void resize(int width, int height) {
        this.mCurrentState.prepareCamera(width, height);
    };

    public void pause() {
        this.isPaused = true;
    };

    public void resume() {
        this.isPaused = false;
    }

    @Override
    public void onGameStateChanged(GameState newState) {
        this.mCurrentState = newState;
        this.mCurrentState.prepareCamera(this.getScreenWidth(), this.getScreenHeight());
        this.loadContent();
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public float getAspect() {
        return (float) this.screenWidth / (float) this.screenHeight;
    }

    public Context getContext() {
        return this.context;
    }

    public GraphicsDevice getGraphicsDevice() {
        return this.graphicsDevice;
    }

    public InputEventManager getInputSystem() {
        return this.mInputManager;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public de.hdm.spe.lander.graphics.Renderer getRenderer() {
        return this.renderer;
    }

    public GameState getCurrentGameState() {
        return this.mCurrentState;
    }

    public void postToast(final String msg) {
        this.view.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(Game.this.context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finish() {
        this.mCurrentState.shutdown();
        this.view.post(new Runnable() {

            @Override
            public void run() {
                ((Activity) Game.this.context).finish();
            }
        });
    }

}
