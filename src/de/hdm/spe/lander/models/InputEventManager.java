
package de.hdm.spe.lander.models;

import android.view.KeyEvent;
import android.view.View;

import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.input.InputEvent;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.input.InputSystem;
import de.hdm.spe.lander.math.Vector3;


public class InputEventManager {

    InputSystem        mSystem;
    private final Game mGame;

    public InputEventManager(Game game, View view) {
        this.mSystem = new InputSystem(view);
        this.mGame = game;

    }

    public void check() {
        InputEvent inputEvent = this.mSystem.peekEvent();
        while (inputEvent != null) {

            switch (inputEvent.getDevice()) {
                case KEYBOARD:
                    switch (inputEvent.getAction()) {
                        case DOWN:
                            switch (inputEvent.getKeycode()) {
                                case KeyEvent.KEYCODE_MENU:
                                    this.mGame.getCurrentGameState().onKeyboardKeyPressed(KeyEvent.KEYCODE_MENU);
                                    break;
                            }
                            break;
                    }
                    break;

                case TOUCHSCREEN:
                    Vector3 screenTouchPosition = new Vector3(
                            (inputEvent.getValues()[0] / (this.mGame.getScreenWidth() / 2) - 1),
                            -(inputEvent.getValues()[1] / (this.mGame.getScreenHeight() / 2) - 1),
                            0);

                    Vector3 worldTouchPosition = this.mGame.getCurrentGameState().getCamera().unproject(screenTouchPosition, 1);

                    Point touchPoint = new Point(
                            worldTouchPosition.getX(),
                            worldTouchPosition.getY());

                    this.mGame.getCurrentGameState().onScreenTouched(touchPoint, inputEvent.getAction());
                    break;

                case ACCELEROMETER:
                    break;

                case ROTATION:
                    this.mGame.getCurrentGameState().onAccelerometerEvent(inputEvent.getValues());
                    break;
            }

            this.mSystem.popEvent();
            inputEvent = this.mSystem.peekEvent();
        }
    }

    public interface InputReceiver {

        public void onScreenTouched(Point point, InputAction action);

        public void onKeyboardKeyPressed(int keyEvent);

        public void onAccelerometerEvent(float[] values);

    }

}
