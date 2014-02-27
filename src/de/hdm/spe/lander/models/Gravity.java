
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

    private final float   Yvelocity;
    private final Vector2 mCurrentGravity;
    private final float   accelerationTime = 1;

    public Gravity(Difficulty diff) {
        this.mCurrentGravity = new Vector2(diff.horizontalSpeed, diff.verticalSpeed);
        this.Yvelocity = diff.verticalSpeed;
    }

    private float calculateGravity(float elapsedTime) {
        float speed = this.Yvelocity;
        float ratio = 1 / (1 - elapsedTime);
        if (ratio > 0 && ratio < 1) {
            speed = this.Yvelocity * ratio;
        }

        return speed;
    }

    public Vector2 getGravity(float timeElapsed) {
        float y = this.mCurrentGravity.getY();
        float factor = timeElapsed;
        //        float factor = 1 - this.accelerationTime / timeElapsed;
        //        if (factor > 1) {
        //            factor = 1;
        //        }
        //        if(elapsedTime>1 || elapsedTime<-1){
        //           factor = 1 - Acceleration.Tmax / elapsedTime;
        //            
        //        }
        //        if (factor < 0 || factor > 1) {
        //            factor = 1;
        //        }
        if (factor > 1) {
            factor = 1;
        }

        //        return new Vector2(0, y * factor);
        return new Vector2(0, y);
    }

    public Vector2 getAbsoluteSpeed(Vector2 shipSpeed, float deltatime) {
        //        this.mCurrentGravity.v[1] = Acceleration.gravY(deltatime);
        return shipSpeed.subtract(this.mCurrentGravity);

    }
}
