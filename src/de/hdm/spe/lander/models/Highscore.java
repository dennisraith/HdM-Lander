
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.math.Vector2;


public class Highscore {

    private final String name;
    private final float  score;

    public Highscore(String name, float score) {
        this.name = name;
        this.score = score;
    }

    public float getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    public static Highscore fromGame(float time, Vector2 speed, String name) {
        return new Highscore(name, Highscore.calculateHighscore(time, speed));
    }

    public static float calculateHighscore(float time, Vector2 speed) {
        float x = (1 / time) * (1 / speed.getY());
        return Math.abs(x * 10000);
    }

}
