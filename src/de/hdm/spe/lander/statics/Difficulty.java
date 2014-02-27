
package de.hdm.spe.lander.statics;

import de.hdm.spe.lander.math.Vector2;


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

    public float getSpeedY() {
        return this.verticalSpeed;
    }

    public float getSpeedX() {
        return this.horizontalSpeed;
    }

    public Vector2 getGravityVector() {
        return new Vector2(this.horizontalSpeed, this.verticalSpeed);
    }
}
