
package de.hdm.spe.lander.input;

public class InputEvent {

    public enum InputDevice {
        NONE,
        KEYBOARD,
        TOUCHSCREEN,
        ACCELEROMETER,
        GYROSCOPE,
        ROTATION
    }

    public enum InputAction {
        NONE,
        DOWN,
        UP,
        MOVE,
        UPDATE
    }

    private InputDevice   device;
    private InputAction   action;
    private float         time;
    private int           keycode;
    private final float[] values = new float[4];

    public InputDevice getDevice() {
        return this.device;
    }

    public InputAction getAction() {
        return this.action;
    }

    public float getTime() {
        return this.time;
    }

    public int getKeycode() {
        return this.keycode;
    }

    public float[] getValues() {
        return this.values;
    }

    public void set(InputDevice device, InputAction action, float time, int keycode, float v0, float v1, float v2, float v3)
    {
        this.device = device;
        this.action = action;
        this.time = time;
        this.keycode = keycode;
        this.values[0] = v0;
        this.values[1] = v1;
        this.values[2] = v2;
        this.values[3] = v3;
    }

}
