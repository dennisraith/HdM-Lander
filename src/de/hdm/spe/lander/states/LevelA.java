
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.models.Lander;

import java.io.IOException;
import java.io.InputStream;


public class LevelA implements GameState {

    private Lander mLander;
    private Camera mCamera;

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mLander);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) {
        this.mCamera = new Camera();
        this.mLander = new Lander();

        Matrix4x4 projection = new Matrix4x4();
        projection.setOrthogonalProjection(-100f, 100f, -100f, 100f, 0f, 10f);
        this.mCamera = new Camera();
        this.mCamera.setProjection(projection);

        try {
            InputStream stream;
            this.mLander.prepare(context);
            stream = context.getAssets().open("space.png");
            this.mLander.setTexture(device.createTexture(stream));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float deltaSeconds) {
        // TODO Auto-generated method stub

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

    @Override
    public Camera getCamera() {
        return this.mCamera;
    }

}
