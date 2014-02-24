
package de.hdm.spe.lander.game;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.input.InputEvent;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.states.LevelA;
import de.hdm.spe.lander.states.Menu;
import de.hdm.spe.lander.game.Game;


import java.io.IOException;


public class LanderGame extends Game {

    private GameState mCurrentState;
    private Camera          mCamera;
	private Matrix4x4 		mProjection;

    public LanderGame(View view) {
    	
        super(view);
        this.mCurrentState = new Menu();
        this.mCamera = new Camera();
        this.mProjection = new Matrix4x4();
        this.mProjection.setOrthogonalProjection(-100f, 100f, -100f, 100f, -100f, 100f);
        this.mCamera.setProjection(this.mProjection);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void loadContent() {
        try {
            this.mCurrentState.prepare(this.context, this.graphicsDevice);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.mCamera = this.mCurrentState.getCamera();
    }

    @Override
    public void update(float deltaSeconds) {
        this.mCurrentState.update(deltaSeconds);
        
//		InputEvent inputEvent = this.inputSystem.peekEvent();
//		while (inputEvent != null) {
//			switch (inputEvent.getDevice()) {
//			case KEYBOARD:
//				switch (inputEvent.getAction()) {
//				case DOWN:
//					switch (inputEvent.getKeycode()) {
//					case KeyEvent.KEYCODE_MENU:
//						this.mCurrentState = new Menu();
//						break;
//					}
//					break;
//				}
//				break;
//			case TOUCHSCREEN:
//				switch (inputEvent.getAction()) {
//				case DOWN:
//					Log.d("touch", "bla bla key event");
//					Vector3 screenTouchPosition = new Vector3(
//							(inputEvent.getValues()[0] / (this.screenWidth / 2) - 1),
//							-(inputEvent.getValues()[1]
//									/ (this.screenHeight / 2) - 1), 0);
//					
//				}
//				break;
//			}
//			this.inputSystem.popEvent();
//			inputEvent = this.inputSystem.peekEvent();
//		}
         
    }

    @Override
    public void draw(float deltaSeconds) {
        this.graphicsDevice.clear(0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
        this.graphicsDevice.setCamera(this.mCamera);
        this.mCurrentState.draw(deltaSeconds, this.renderer);
    }

    @Override
    public void resize(int width, int height) {
        this.mCurrentState.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
