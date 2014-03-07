
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.gameobjects.Lander;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.statics.Difficulty;


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

    public static float calculateHighscore(float time, Lander lander) {
        Vector2 speed = lander.getCurrentSpeed();
        Fuel fuel = lander.getFuel();
        Difficulty diff = lander.getDifficulty();

        float timeWeight = 1 / time;
        float speedWeight = 1 / speed.getY();
        float fuelWeight = fuel.getPercentage() / 100;

        float x = timeWeight * speedWeight * fuelWeight * (diff.ordinal() + 1);
        return Math.round(Math.abs(x * 10000));
    }

}
