
package de.hdm.spe.lander.models;

import android.view.KeyEvent;
import android.view.View;

import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.input.InputEvent;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.input.InputSystem;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.states.GameState;


/**
 * Manager responsible for parsing and controlling input events and if necessary dispatch actions to the current {@link GameState}
 * @author Dennis
 *
 */
public class InputEventManager {

    InputSystem        mSystem;
    private final Game mGame;

    public InputEventManager(Game game, View view) {
        this.mSystem = new InputSystem(view);
        this.mGame = game;

    }

    /**
     * Loop method invoked by the Game class
     */
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

                                case KeyEvent.KEYCODE_BACK:
                                    this.mGame.getCurrentGameState().onBackPressed();
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
                    this.mGame.getCurrentGameState().onDeviceRotationEvent(inputEvent.getValues());
                    break;
            }

            this.mSystem.popEvent();
            inputEvent = this.mSystem.peekEvent();
        }
    }

    /**
     * Interface to wrap input events into certain important methods
     * @author Dennis
     *
     */
    public interface InputReceiver {

        /**
         * called when screen has been touched
         * @param point the unprojected point of touch
         * @param action the touch action, see {@link InputAction}
         */
        public void onScreenTouched(Point point, InputAction action);

        /**
         * Called when a Keyboard key has been pressed
         * @param keyEvent the corresponding {@link KeyEvent} value 
         */
        public void onKeyboardKeyPressed(int keyEvent);

        /**
         * Called upon rotation events 
         * @param values array containing the x,y,z values of the event
         */
        public void onDeviceRotationEvent(float[] values);

        /**
         * Called when the KeyEvent {@link KeyEvent.BACK} occured
         */
        public void onBackPressed();

    }

}
