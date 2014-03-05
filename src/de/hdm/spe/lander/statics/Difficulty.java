
package de.hdm.spe.lander.statics;

import de.hdm.spe.lander.math.Vector2;


public enum Difficulty {
    EASY(0, .4f, 10),
    MEDIUM(0, .7f, 7),
    HARD(.1f, .7f, 5);

    float verticalSpeed;
    float horizontalSpeed;
    float fuelSeconds;

    Difficulty(float horSpeed, float vertSpeed, float fuelSeconds) {
        this.verticalSpeed = vertSpeed;
        this.horizontalSpeed = horSpeed;
        this.fuelSeconds = fuelSeconds;
    }

    public float getSpeedY() {
        return this.verticalSpeed;
    }

    public float getFuelCapacity() {
        return this.fuelSeconds;
    }

    public float getSpeedX() {
        return this.horizontalSpeed;
    }

    public Vector2 getGravityVector() {
        return new Vector2(this.horizontalSpeed, this.verticalSpeed);
    }
}
