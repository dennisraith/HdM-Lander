
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.models.InputEventManager.InputReceiver;

import java.io.IOException;


public abstract class GameState implements InputReceiver {

    public enum StateType {
        MENU,
        LEVEL1,
        LEVEL2,
        OPTIONS
    }

    private final Game mGame;
    protected Camera   mCamera = new Camera();

    public GameState(Game game) {
        this.mGame = game;
    }

    public Game getGame() {
        return this.mGame;
    }

    public abstract void prepare(Context context, GraphicsDevice device) throws IOException;

    public abstract void prepareCamera(float width, float height);

    public abstract void update(float deltaSeconds);

    public abstract void draw(float deltaSeconds, Renderer renderer);

    public abstract void shutdown();

    public void pause() {
        this.mGame.pause();
    };

    public void resume() {
        this.mGame.resume();
    }

    public abstract StateType getStateType();

    public Camera getCamera() {
        return this.mCamera;
    }

    public void setGameState(StateType state) {
        this.mGame.setGameState(state);
    }

}
