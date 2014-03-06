
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

    //    public static Highscore fromGame(float time, Vector2 speed, String name) {
    //        return new Highscore(name, Highscore.calculateHighscore(time, speed));
    //    }

    public static float calculateHighscore(float time, Vector2 speed, Fuel fuel) {
        float timeWeight = 1 / time;
        float speedWeight = 1 / speed.getY();
        float fuelWeight = fuel.getPercentage() / 100;

        float x = timeWeight * speedWeight * fuelWeight;
        return Math.round(Math.abs(x * 10000));
    }

}
