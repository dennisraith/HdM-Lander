
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.statics.Difficulty;


public class Fuel {

    private float      mFuel;
    private Difficulty mDiff;

    public Fuel(int capacity) {
        this.mFuel = capacity;
    }

    public Fuel(Difficulty diff) {
        this.mDiff = diff;
        this.mFuel = diff.getFuelCapacity();
    }

    public void onAccelerating(float deltatime) {
        this.mFuel = this.mFuel - deltatime;
    }

    public boolean isEmpty() {
        return this.mFuel <= 0;
    }

    public float getPercentage() {
        return (this.getCurrentAmount() / this.mDiff.getFuelCapacity()) * 100;
    }

    public float getCurrentAmount() {
        return this.mFuel;
    }

}
