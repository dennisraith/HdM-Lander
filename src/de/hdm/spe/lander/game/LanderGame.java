
package de.hdm.spe.lander.game;

import android.view.View;

import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.states.LevelA;


public class LanderGame extends Game {

    private final GameState mCurrentState;

    public LanderGame(View view) {
        super(view);
        this.mCurrentState = new LevelA();
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
        this.mCurrentState.draw(deltaSeconds, this.renderer);
        this.graphicsDevice.setCamera(this.mCurrentState.getCamera());
    }

    @Override
    public void resize(int width, int height) {
        float aspect = (float) width / (float) height;
        Matrix4x4 projection;

        projection = new Matrix4x4();
        projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 100.0f);
        this.mCurrentState.getCamera().setProjection(projection);

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
