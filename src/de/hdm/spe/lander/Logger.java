
package de.hdm.spe.lander;

import android.util.Log;

import de.hdm.spe.lander.math.Vector2;


public class Logger {

    public static void log(String name, Vector2 vect) {
        Log.d("###Vector2###", name + " x: " + vect.getX() + " y:" + vect.getY());
    }

    public static void log(String name, float f) {
        Log.d("###float###", name + " : " + f);
    }

    public static void log(String name, long f) {
        Log.d("###float###", name + " : " + Long.toString(f));
    }

    public static void log(String name, String msg) {
        Log.d("###String###", name + " : " + msg);
    }

}
