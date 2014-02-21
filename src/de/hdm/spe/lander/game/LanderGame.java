
package de.hdm.spe.lander.game;

import android.util.Log;
import android.view.View;

import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.states.LevelA;


public class LanderGame extends Game {

    private final GameState mCurrentState;
    private final Camera    mCamera;
    private final Matrix4x4 mProjection;

    public LanderGame(View view) {
        super(view);
        this.mCurrentState = new LevelA();
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
        this.mCurrentState.prepare(this.context, this.graphicsDevice);
    }

    @Override
    public void update(float deltaSeconds) {
        this.mCurrentState.update(deltaSeconds);
        /*
         * InputEvent inputEvent = this.inputSystem.peekEvent(); while (inputEvent != null && false) { switch (inputEvent.getDevice()) { case KEYBOARD: switch
         * (inputEvent.getAction()) { case DOWN: switch (inputEvent.getKeycode()) { case KeyEvent.KEYCODE_MENU: this.showMenu = !this.showMenu; break; } break;
         * } break; case TOUCHSCREEN: switch (inputEvent.getAction()) { case DOWN: Vector3 screenTouchPosition = new Vector3( (inputEvent.getValues()[0] /
         * (this.screenWidth / 2) - 1), -(inputEvent.getValues()[1] / (this.screenHeight / 2) - 1), 0); } break; } this.inputSystem.popEvent(); inputEvent =
         * this.inputSystem.peekEvent(); }
         */
    }

    @Override
    public void draw(float deltaSeconds) {
        this.graphicsDevice.clear(0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
        this.graphicsDevice.setCamera(this.mCamera);
        this.mCurrentState.draw(deltaSeconds, this.renderer);
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

}
