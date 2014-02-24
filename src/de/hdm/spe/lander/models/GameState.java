
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;

import java.io.IOException;


public abstract class GameState {

    protected Game mGame;

    public GameState(Game game) {
        this.mGame = game;
    }

    public abstract void prepare(Context context, GraphicsDevice device) throws IOException;

    public abstract void update(float deltaSeconds);

    public abstract void draw(float deltaSeconds, Renderer renderer);

    public abstract void resize(int width, int height);

    public abstract void shutdown();

    public abstract void pause();

    public abstract void resume();

    public abstract Camera getCamera();

}
