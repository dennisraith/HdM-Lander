
package de.hdm.spe.lander.math;

import android.util.Log;


public class Vector4 {

    float[] vect = new float[4];

    public Vector4() {
    }

    public Vector4(float x, float y, float z, float w) {
        this.vect[0] = x;
        this.vect[1] = y;
        this.vect[2] = z;
        this.vect[3] = w;
    }

    public Vector4(Vector3 v, float w) {
        this.vect[0] = v.getX();
        this.vect[1] = v.getY();
        this.vect[2] = v.getZ();
        this.vect[3] = w;
    }

    public Vector4(Vector2 v, float z, float w) {
        this.vect[0] = v.getX();
        this.vect[1] = v.getY();
        this.vect[2] = z;
        this.vect[3] = w;
    }

    public static Vector4 add(Vector4 v1, Vector4 v2) {
        v1.set(0, v1.getX() + v2.getX());
        v1.set(1, v1.getY() + v2.getY());
        v1.set(2, v1.getZ() + v2.getZ());
        v1.set(3, v1.getW() + v2.getW());
        return v1;
    }

    public static Vector4 divide(Vector4 v1, float s) {
        v1.set(0, v1.getX() / s);
        v1.set(1, v1.getY() / s);
        v1.set(2, v1.getZ() / s);
        v1.set(3, v1.getW() / s);
        return v1;
    }

    public static float dot(Vector4 v1, Vector4 v2) {
        float dot = v1.getX() * v2.getX() +
                v1.getY() * v2.getY() +
                v1.getZ() * v2.getZ() +
                v1.getW() * v2.getW();

        return dot;
    }

    public static Vector4 multiply(float s, Vector4 v1) {
        v1.set(0, v1.getX() * s);
        v1.set(1, v1.getY() * s);
        v1.set(2, v1.getZ() * s);
        v1.set(3, v1.getW() * s);
        return v1;
    }

    public static Vector4 subtract(Vector4 v1, Vector4 v2) {
        v1.set(0, v1.getX() - v2.getX());
        v1.set(1, v1.getY() - v2.getY());
        v1.set(2, v1.getZ() - v2.getZ());
        v1.set(3, v1.getW() - v2.getW());
        return v1;
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

    public float getW() {
        return this.vect[3];
    }

    public float getLength() {
        return (float) Math.sqrt(this.getLengthSqr());
    }

    public float getLengthSqr() {

        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() + this.getW() * this.getW();
    }

    public Vector4 normalize() {
        float norm = 1 / this.getLengthSqr();

        return Vector4.multiply(norm, this);
    }

    public static Vector4 normalize(Vector4 v) {
        float norm = 1 / v.getLengthSqr();

        return Vector4.multiply(norm, v);

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

    public void setW(float w) {
        this.vect[3] = w;
    }

    public void log() {
        String msg = null;
        msg = "(" + this.getX() + ")\n";
        msg += "(" + this.getY() + ")\n";
        msg += "(" + this.getZ() + ")\n";
        msg += "(" + this.getW() + ")\n";
        Log.d(this.getClass().getSimpleName(), msg);
    }
}
