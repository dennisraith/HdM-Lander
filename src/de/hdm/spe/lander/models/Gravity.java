
package de.hdm.spe.lander.models;

import android.util.Log;

import de.hdm.spe.lander.math.Vector2;


public class Gravity {

    public enum Difficulty {
        EASY(0, .15f),
        MEDIUM(0, .2f),
        HARD(.1f, .3f);

        float verticalSpeed;
        float horizontalSpeed;

        Difficulty(float vertSpeed, float horSpeed) {
            this.verticalSpeed = vertSpeed;
            this.horizontalSpeed = horSpeed;
        }
    }

    private final Vector2 mGravity;

    public Gravity(Difficulty diff) {
        this.mGravity = new Vector2(-diff.verticalSpeed, -diff.horizontalSpeed);
    }

    public Vector2 getGravity() {
        return this.mGravity;
    }

    public Vector2 getAbsoluteSpeed(Vector2 shipSpeed) {
        Log.d(this.getClass().getName(), "##ShipSpeed: X: " + shipSpeed.getX() + " Y: " + shipSpeed.getY());
        return shipSpeed.add(this.mGravity);

    }
}
