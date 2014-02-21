
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

    private Lander  mLander;
    private Square mSquare;

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mSquare);
        renderer.draw(this.mLander);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) {
        this.mLander = new Lander();
        this.mSquare = new Square(0, 0, 10, 10);
        try {
            this.mSquare.prepare(context);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //        this.mSquare.getWorld().scale(.4f, 1, 1).translate(0, 0, 0);
        this.mLander.getWorld().translate(0, 0, 20);
        try {
            InputStream stream;
            stream = context.getAssets().open("space.png");
            this.mSquare.getMaterial().setTexture(device.createTexture(stream));
            this.mLander.prepare(context);
            stream = context.getAssets().open("texture2.bmp");
            this.mLander.setTexture(device.createTexture(stream));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float deltaSeconds) {
        //        this.mLander.getWorld().translate(0, -deltaSeconds * 3, 0);
        //        this.mLander.getWorld().translate(0, -deltaSeconds * 5, 0);
        this.mLander.getWorld().translate((float) (10 * Math.sin(deltaSeconds)), 0, 0);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
