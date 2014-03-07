
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.gameobjects.Lander;
import de.hdm.spe.lander.statics.Difficulty;


/**
 * Class representing the Amount of fuel the {@link Lander} has.
 * If this is empty, the Lander cannot accelerate anymore
 * 
 * @author Dennis
 *
 */
public class Fuel {

    private float      mFuel;
    private Difficulty mDiff;
    private boolean    infinite = false;

    /**
     * Initialize the instance with a specified capacity
     * @param capacity
     */
    public Fuel(int capacity) {
        this.mFuel = capacity;
    }

    /**
     * @param diff
     */
    public Fuel(Difficulty diff) {
        this.mDiff = diff;
        this.mFuel = diff.getFuelCapacity();
    }

    /**
     * Withdraw fuel capacity upon acceleration of the Lander
     * @param deltatime
     */
    public void onAccelerating(float deltatime) {
        if (!this.infinite) {
            this.mFuel = this.mFuel - deltatime;
        }
    }

    /**
     * @return whether fuel is empty
     */
    public boolean isEmpty() {
        return this.mFuel <= 0;
    }

    /**
     * @param infinite
     */
    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    /**
     * @return
     */
    public float getPercentage() {
        return (this.getCurrentAmount() / this.mDiff.getFuelCapacity()) * 100;
    }

    /**
     * @return
     */
    public float getCurrentAmount() {
        return this.mFuel;
    }

}
