
package de.hdm.spe.lander.models;

public class GameTimer {

    private float   time = 0;
    private boolean isPaused;

    public void update(float deltaTime) {
        if (!this.isPaused) {
            this.time += deltaTime;
        }
    }

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    public float getTime() {
        return this.time;
    }

}
