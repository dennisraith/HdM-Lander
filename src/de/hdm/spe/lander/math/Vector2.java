
package de.hdm.spe.lander.math;

import android.util.Log;


public class Vector2 {

    public float[] v = new float[2];

    public Vector2() {
    }

    public Vector2(float x, float y) {
        this.v[0] = x;
        this.v[1] = y;
    }

    public Vector2(float x, float y, float z) {
        this.v[0] = x;
        this.v[1] = y;
    }

    public static Vector2 add(Vector2 v1, Vector2 v2) {
        v1.set(0, v1.getX() + v2.getX());
        v1.set(1, v1.getY() + v2.getY());
        return v1;
    }

    public Vector2 add(Vector2 v2) {
        Vector2 v1 = new Vector2();
        v1.set(0, this.getX() + v2.getX());
        v1.set(1, this.getY() + v2.getY());
        return v1;
    }

    public static Vector2 divide(Vector2 v1, float s) {
        v1.set(0, v1.getX() / s);
        v1.set(1, v1.getY() / s);
        return v1;
    }

    public static float dot(Vector2 v1, Vector2 v2) {
        float dot = v1.getX() * v2.getX() +
                v1.getY() * v2.getY();

        return dot;
    }

    public static Vector2 multiply(float s, Vector2 v1) {
        v1.set(0, v1.getX() * s);
        v1.set(1, v1.getY() * s);
        return v1;
    }

    public static Vector2 subtract(Vector2 v1, Vector2 v2) {
        v1.set(0, v1.getX() - v2.getX());
        v1.set(1, v1.getY() - v2.getY());
        return v1;
    }

    public float get(int index) {
        return this.v[index];
    }

    public float getX() {
        return this.v[0];
    }

    public float getY() {
        return this.v[1];
    }

    public float getLength() {

        return (float) Math.sqrt(this.getLengthSqr());
    }

    public float getLengthSqr() {

        return this.getX() * this.getX() + this.getY() * this.getY();
    }

    public Vector2 normalize() {
        float norm = 1 / this.getLengthSqr();

        return Vector2.multiply(norm, this);
    }

    public static Vector2 normalize(Vector2 v) {
        float norm = 1 / v.getLengthSqr();

        return Vector2.multiply(norm, v);

    }

    public void set(int index, float value) {
        this.v[index] = value;
    }

    public void setX(float x) {
        this.v[0] = x;
    }

    public void setY(float y) {
        this.v[1] = y;
    }

    public void log() {
        String msg = null;
        msg = "(" + this.getX() + ")\n";
        msg += "(" + this.getY() + ")\n";
        Log.d(this.getClass().getSimpleName(), msg);
    }
}
