
package de.hdm.spe.lander.game;

import android.view.View;

import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.states.LevelA;

import java.io.IOException;


public class LanderGame extends Game {

    private final GameState mCurrentState;
    private Camera          mCamera;

    public LanderGame(View view) {
        super(view);
        this.mCurrentState = new LevelA(this);

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
        this.mCurrentState.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
