
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;


public interface GameState {

    public void prepare(Context context, GraphicsDevice device);

    public void update(float deltaSeconds);

    public void draw(float deltaSeconds, Renderer renderer);

    public void resize(int width, int height);

    public void pause();

    public void resume();

}
