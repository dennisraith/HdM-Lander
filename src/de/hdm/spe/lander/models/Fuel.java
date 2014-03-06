
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.statics.Difficulty;


public class Fuel {

    private float      mFuel;
    private Difficulty mDiff;
    private boolean    infinite = false;

    public Fuel(int capacity) {
        this.mFuel = capacity;
    }

    public Fuel(Difficulty diff) {
        this.mDiff = diff;
        this.mFuel = diff.getFuelCapacity();
    }

    public void onAccelerating(float deltatime) {
        if (!this.infinite) {
            this.mFuel = this.mFuel - deltatime;
        }
    }

    public boolean isEmpty() {
        return this.mFuel <= 0;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public float getPercentage() {
        return (this.getCurrentAmount() / this.mDiff.getFuelCapacity()) * 100;
    }

    public float getCurrentAmount() {
        return this.mFuel;
    }

}
