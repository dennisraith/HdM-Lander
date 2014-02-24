
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;

import java.io.IOException;


public interface GameState {

    public void prepare(Context context, GraphicsDevice device) throws IOException;

    public void update(float deltaSeconds);

    public void draw(float deltaSeconds, Renderer renderer);

    public void resize(int width, int height);

    public void pause();

    public void resume();
    
    public void shutdown();

	public void initialize();

	public void loadContent();


    public Camera getCamera();

}
