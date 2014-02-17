
package de.hdm.spe.lander.math;

import android.util.Log;


public class Vector3 {

    float[] vect = new float[3];

    public Vector3() {
    }

    public Vector3(float x, float y, float z) {
        this.vect[0] = x;
        this.vect[1] = y;
        this.vect[2] = z;
    }

    public Vector3(Vector2 v, float z) {
        this.vect[0] = v.getX();
        this.vect[1] = v.getY();
        this.vect[2] = z;
    }

    public static Vector3 add(Vector3 v1, Vector3 v2) {
        v1.set(0, v1.getX() + v2.getX());
        v1.set(1, v1.getY() + v2.getY());
        v1.set(2, v1.getZ() + v2.getZ());
        return v1;
    }

    public static Vector3 divide(Vector3 v1, float s) {
        v1.set(0, v1.getX() / s);
        v1.set(1, v1.getY() / s);
        v1.set(2, v1.getZ() / s);
        return v1;
    }

    public static float dot(Vector3 v1, Vector3 v2) {
        float dot = v1.getX() * v2.getX() +
                v1.getY() * v2.getY() +
                v1.getZ() * v2.getZ();

        return dot;
    }

    public static Vector3 multiply(float s, Vector3 v1) {
        v1.set(0, v1.getX() * s);
        v1.set(1, v1.getY() * s);
        v1.set(2, v1.getZ() * s);
        return v1;
    }

    public static Vector3 subtract(Vector3 v1, Vector3 v2) {
        v1.set(0, v1.getX() - v2.getX());
        v1.set(1, v1.getY() - v2.getY());
        v1.set(2, v1.getZ() - v2.getZ());
        return v1;
    }

    public static Vector3 cross(Vector3 v1, Vector3 v2) {
        float x = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();
        float y = v1.getZ() * v2.getX() - v1.getX() * v2.getZ();
        float z = v1.getX() * v2.getY() - v1.getY() * v2.getX();

        return new Vector3(x, y, z);
    }

    public float get(int index) {
        return this.vect[index];
    }

    public float getX() {
        return this.vect[0];
    }

    public float getY() {
        return this.vect[1];
    }

    public float getZ() {
        return this.vect[2];
    }

    public float getLength() {

        return (float) Math.sqrt(this.getLengthSqr());
    }

    public float getLengthSqr() {

        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }

    public Vector3 normalize() {
        float norm = 1 / this.getLengthSqr();

        return Vector3.multiply(norm, this);
    }

    public static Vector3 normalize(Vector3 v) {
        float norm = 1 / v.getLengthSqr();

        return Vector3.multiply(norm, v);

    }

    public void set(int index, float value) {
        this.vect[index] = value;
    }

    public void setX(float x) {
        this.vect[0] = x;
    }

    public void setY(float y) {
        this.vect[1] = y;
    }

    public void setZ(float z) {
        this.vect[2] = z;
    }

    public void log() {
        String msg = null;
        msg = "(" + this.getX() + ")\n";
        msg += "(" + this.getY() + ")\n";
        msg += "(" + this.getZ() + ")\n";
        Log.d(this.getClass().getSimpleName(), msg);
    }
}
