
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.models.Lander;
import de.hdm.spe.lander.models.Square;

import java.io.IOException;
import java.io.InputStream;


public class LevelA implements GameState {

    private Lander          mLander;
    private Square          mSquare;
    private final Matrix4x4 mProjection;
    private final Camera    mCamera;

    public LevelA() {
        this.mCamera = new Camera();
        this.mProjection = new Matrix4x4();
        this.mProjection.setOrthogonalProjection(-100f, 100f, -100f, 100f, -100f, 100f);
        this.mCamera.setProjection(this.mProjection);
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mSquare);
        renderer.draw(this.mLander);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream stream;
        //initialize Lander//initialize Square
        this.mLander = new Lander();

        //load textures
        this.mLander.prepare(context);
        stream = context.getAssets().open("texture2.bmp");
        this.mLander.getMaterial().setTexture(device.createTexture(stream));

        this.mSquare = new Square(0, 0, 10, 10);
        stream = context.getAssets().open("space.png");
        this.mSquare.prepare(context);
        this.mSquare.getMaterial().setTexture(device.createTexture(stream));
        this.mSquare.getWorld().translate(0, -50, 0);
        //        this.mLander.getWorld().translate(0, 0, 20);
        //        this.mSquare.getWorld().scale(.4f, 1, 1).translate(0, 0, 0);
    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.translate(-deltaSeconds * 3);
        if (this.mLander.intersects(this.mSquare)) {
            Log.d(this.getClass().getName(), "INTERSECTION!!!!!!!!");
        }
    }

    @Override
    public void resize(int width, int height) {

        Log.d(this.getClass().getName(), "Width: " + width + " Height: " + height);
        float aspect = (float) width / (float) height;

        if (aspect > 1) {
            //        projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 10f, 100.0f);            
            this.mProjection.setOrthogonalProjection(-100, 100, -100 * aspect, 100 * aspect, 0, 100.0f);
        }
        else {
            this.mProjection.setOrthogonalProjection(-100 * aspect, 100 * aspect, -100, 100, -100f, 100.0f);
            //            this.mProjection.scale(2);
        }
        this.mCamera.setProjection(this.mProjection);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
    @Override
    public void shutdown(){
    	
    }
    
    @Override
	public void initialize(){
		
	}
    
    @Override
	public void loadContent(){
		
	}

    @Override
    public Camera getCamera() {
        return this.mCamera;
    }

}
