
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.models.Lander;
import de.hdm.spe.lander.models.Square;

import java.io.IOException;
import java.io.InputStream;


public class LevelA implements GameState {

    private Lander mLander;
    private Square mSquare;

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mLander);
        renderer.draw(this.mSquare);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) {
        this.mLander = new Lander();
        this.mSquare = new Square();
        try {
            this.mSquare.prepare(context);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            InputStream stream;
            this.mLander.prepare(context);
            stream = context.getAssets().open(Lander.textureName);
            this.mLander.setTexture(device.createTexture(stream));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.getWorld().translate(0, -deltaSeconds, 0);
        //        this.mSquare.getWorld().translate(+deltaSeconds * 500, +deltaSeconds * 200, 0);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

}
