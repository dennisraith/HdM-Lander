
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.math.Vector2;


public class Gravity {

    public enum Difficulty {
        EASY(0, .4f),
        MEDIUM(0, .7f),
        HARD(.1f, .7f);

        float verticalSpeed;
        float horizontalSpeed;

        Difficulty(float horSpeed, float vertSpeed) {
            this.verticalSpeed = vertSpeed;
            this.horizontalSpeed = horSpeed;
        }
    }

    private final float   verticalGravity;
    private final Vector2 mCurrentGravity;

    public Gravity(Difficulty diff) {
        this.mCurrentGravity = new Vector2(-diff.horizontalSpeed, -diff.verticalSpeed);
        this.verticalGravity = -diff.verticalSpeed;
    }

    private float calculateGravity(float elapsedTime) {
        float speed = this.verticalGravity;
        float ratio = elapsedTime - 1;
        if (ratio < 1 && ratio > 0) {
            speed = this.verticalGravity * ratio;
        }

        return speed;
    }

    public Vector2 getAbsoluteSpeed(Vector2 shipSpeed, float deltatime) {
        this.mCurrentGravity.v[1] = this.calculateGravity(deltatime);
        return shipSpeed.add(this.mCurrentGravity);

    }
}
