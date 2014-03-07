
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Background;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.models.InputEventManager.InputReceiver;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.models.OptionManager.Language;

import java.io.IOException;


public abstract class GameState implements InputReceiver {

    public enum StateType {
        CREDITSLEVEL,
        LEVEL1,
        LEVEL2,
        LEVEL3,
        LEVEL4,
        MENU,
        OPTIONS,
        LEVELMENU,
        DIFFICULTYOPTIONS
    }

    private final Game   mGame;
    protected Camera     mCamera     = new Camera();
    private boolean      mIsPrepared = false;
    protected Background mBackground;
    protected Language   mLanguage;

    public GameState(Game game) {
        this.mGame = game;
        this.mLanguage = OptionManager.getInstance().getLanguage();
    }

    public Game getGame() {
        return this.mGame;
    }

    /**
     * Function for preparing and loading ressources before state is drawn, only called once by game class if language does not change
     * @param context
     * @param device the {@link GraphicsDevice}
     * @throws IOException if asset files not found
     */
    public abstract void prepare(Context context, GraphicsDevice device) throws IOException;

    /**
     * convenience method for preparing the camera before the state is drawn
     * @param width screen width
     * @param height screen height
     */
    public abstract void prepareCamera(float width, float height);

    /**
     * corresponding update method which is called by the game class
     * @param deltaSeconds
     */
    public abstract void update(float deltaSeconds);

    /**corresponding draw method which is called by the game class
     * @param deltaSeconds
     * @param renderer
     */
    public abstract void draw(float deltaSeconds, Renderer renderer);

    /**
     * called when states change from {@link Level} to {@link Menu}, vice versa or when app is closed.
     * Calls the {@link GameState}'s onPause method
     * @param type
     */
    public final void shutdown(StateType type) {
        if (type == null) {
            this.onPause();
            return;
        }
        if (this.getStateType().ordinal() > StateType.LEVEL4.ordinal() && type.ordinal() <= StateType.LEVEL4.ordinal()) {
            this.onPause();
        }
        else if (this.getStateType().ordinal() <= StateType.LEVEL4.ordinal() && type.ordinal() > StateType.LEVEL4.ordinal()) {
            this.onPause();
        }
    }

    /**
     * called when game determines if state has to be prepared again due to language changes
     * @return the current Language of this state
     */
    public Language getLanguage() {
        return this.mLanguage;
    }

    /**
     * sets the states prepared state so it doesn't get prepared again to avoid performance issues
     * @param initialized
     */
    public void setPrepared(boolean initialized) {
        this.mIsPrepared = initialized;
    }

    public boolean isPrepared() {
        return this.mIsPrepared;
    }

    public void onPause() {
        MediaManager.getInstance().reset();

    };

    public void onResume() {

    };

    /**
     * called when the game has prepared and set the current 
     */
    public void onLoad() {
        this.mLanguage = OptionManager.getInstance().getLanguage();
    };

    /**
     * @return the {@link StateType} for this GameState class
     */
    public abstract StateType getStateType();

    public Camera getCamera() {
        return this.mCamera;
    }

    /**
     * convenience method for calling the game to change the current state
     * @param state
     */
    public void setGameState(StateType state) {
        this.mGame.setGameState(state);
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.InputEventManager.InputReceiver#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        if (this.getStateType() != StateType.MENU) {
            this.getGame().setGameState(StateType.MENU);
        }
        else {
            this.mGame.finish();
        }
    }

}
