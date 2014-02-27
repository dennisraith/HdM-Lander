
package de.hdm.spe.lander.statics;

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
}
