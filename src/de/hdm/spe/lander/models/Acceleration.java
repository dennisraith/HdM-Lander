
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.math.Vector2;


public class Acceleration {

    private static Vector2 acceleration = new Vector2(0, 0.4f);
    private static float   Tmax         = 1;

    public static Vector2 getAcceleration(Vector2 speed, float elapsedTime) {
        float factor = elapsedTime;
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
        //        else if (factor < -1) {
        //            factor = -1;
        //        }
        return new Vector2(speed.getX(), speed.getY() * factor);
    }

    public static float calcTimeValue(Vector2 maxSpeed, float time) {
        float value = 0;
        float offset = Acceleration.Tmax - time;
        if (offset > 0 && offset < 1) {
            value = 0.5f * maxSpeed.getY() * time;
        }
        else {
            value = maxSpeed.getY();
        }

        return value;
    }

    public static float gravY(float time) {
        float value = 0;
        float offset = Acceleration.Tmax - time;
        if (offset > 0 && offset < 1) {
            value = 0.5f * Acceleration.acceleration.getY() * time;
        }
        else {
            value = Acceleration.acceleration.getY();
        }

        return value;
    }
}
