
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.math.Vector2;


public class Gravity {

    public enum Difficulty {
        EASY(4, 0),
        MEDIUM(6, 0),
        HARD(7, 2);

        int verticalSpeed;
        int horizontalSpeed;

        Difficulty(int vertSpeed, int horSpeed) {
            this.verticalSpeed = vertSpeed;
            this.horizontalSpeed = horSpeed;
        }
    }

    private final Vector2 mGravity;

    public Gravity(Difficulty diff) {
        this.mGravity = new Vector2(-diff.verticalSpeed, -diff.horizontalSpeed);
    }

    public Vector2 getSpeed() {
        return this.mGravity;
    }
}
