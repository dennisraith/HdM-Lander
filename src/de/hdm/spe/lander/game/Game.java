
package de.hdm.spe.lander.game;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView.Renderer;
import android.view.View;
import android.widget.Toast;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.InputEventManager;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.models.OptionManager.Language;
import de.hdm.spe.lander.models.OptionManager.LocaleChangeListener;
import de.hdm.spe.lander.states.CreditsLevel;
import de.hdm.spe.lander.states.DifficultyOptions;
import de.hdm.spe.lander.states.GameState;
import de.hdm.spe.lander.states.GameState.StateType;
import de.hdm.spe.lander.states.Level1;
import de.hdm.spe.lander.states.Level2;
import de.hdm.spe.lander.states.Level3;
import de.hdm.spe.lander.states.Level4;
import de.hdm.spe.lander.states.LevelMenu;
import de.hdm.spe.lander.states.Menu;
import de.hdm.spe.lander.states.Options;
import de.hdm.spe.lander.statics.Lang;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public abstract class Game implements Renderer, LocaleChangeListener {

    private boolean                               initialized;
    private long                                  lastTime;

    protected View                                view;
    protected Context                             context;
    protected InputEventManager                   mInputManager;
    protected GraphicsDevice                      graphicsDevice;
    protected de.hdm.spe.lander.graphics.Renderer renderer;

    protected int                                 screenWidth  = 1;
    protected int                                 screenHeight = 1;
    private boolean                               isPaused     = false;
    private Menu                                  mMenu;
    private Options                               mOptions;
    private LevelMenu                             mLevelMenu;
    private DifficultyOptions                     mDiffOptions;
    protected GameState                           mCurrentState;

    public abstract void initialize();

    public Game(View view) {
        this.view = view;
        this.context = view.getContext();
        this.screenHeight = this.context.getResources().getDisplayMetrics().heightPixels;
        this.screenWidth = this.context.getResources().getDisplayMetrics().widthPixels;

        this.mMenu = new Menu(this);
        this.mInputManager = new InputEventManager(this, view);
        HighscoreManager.initialize(this.context);
        MediaManager.initialize(this.context);
        OptionManager.initialize(this.context, this);
        Lang.prepare(this.context);

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

            this.loadContent(this.mCurrentState);
        } else {
            this.graphicsDevice.onSurfaceCreated(gl);
            this.loadContent(this.mCurrentState);
        }
    }

    private GameState getStateInstance(StateType type) {
        switch (type) {
            case LEVEL1:
                return new Level1(this);
            case LEVEL2:
                return new Level2(this);
            case LEVEL3:
                return new Level3(this);
            case LEVEL4:
                return new Level4(this);
            case MENU:
                if (this.mMenu == null) {
                    this.mMenu = new Menu(this);
                }
                return this.mMenu;
            case OPTIONS:
                if (this.mOptions == null) {
                    this.mOptions = new Options(this);
                }
                return this.mOptions;
            case CREDITSLEVEL:
                return new CreditsLevel(this);
            case LEVELMENU:
                if (this.mLevelMenu == null) {
                    this.mLevelMenu = new LevelMenu(this);
                }
                return this.mLevelMenu;
            case DIFFICULTYOPTIONS:
                if (this.mDiffOptions == null) {
                    this.mDiffOptions = new DifficultyOptions(this);
                }
                return this.mDiffOptions;

            default:
                return null;
        }
    }

    public void setGameState(GameState.StateType type) {
        if (this.mCurrentState != null) {
            this.mCurrentState.shutdown(type);
        }
        if (this.mCurrentState == null || this.mCurrentState.getStateType() != type) {
            GameState state = this.getStateInstance(type);
            this.onGameStateChanged(state);
        }
    }

    protected void onGameStateChanged(GameState newState) {
        newState.prepareCamera(this.screenWidth, this.screenHeight);
        this.loadContent(newState);

        this.mCurrentState = newState;
        if (this.isPaused) {
            this.resume();
        }

    }

    public void loadContent(GameState state) {
        if (!state.isPrepared()) {
            try {
                state.prepare(this.context, this.graphicsDevice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setLocale(Language locale) {
        Configuration conf = new Configuration(this.context.getResources().getConfiguration());
        conf.locale = locale.getLocale();
        this.getContext().getResources().updateConfiguration(conf, this.context.getResources().getDisplayMetrics());
        Lang.prepare(this.context);
    }

    @Override
    public void onLocaleChanged(Language locale) {
        this.setLocale(locale);

        try {
            this.mMenu.prepare(this.context, this.graphicsDevice);
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
        if (this.mCurrentState != null) {
            try {
                this.mCurrentState.prepare(this.context, this.graphicsDevice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void postToast(final int resId) {
        this.view.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(Game.this.context, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finish() {
        this.mCurrentState.shutdown(null);
        this.view.post(new Runnable() {

            @Override
            public void run() {
                ((Activity) Game.this.context).finish();
            }
        });
    }

}
