
package de.hdm.spe.lander.math;

import android.opengl.Matrix;


public class Matrix4x4 {

    public float[] data = new float[16];

    public Matrix4x4() {
        this.setIdentity();
    }

    public Matrix4x4(float[] m) {
        this.copy(m);
    }

    private void copy(float[] m) {
        for (int i = 0; i < m.length; i++) {
            this.data[i] = m[i];
        }
    }

    public Matrix4x4(Matrix4x4 m) {
        this.copy(m.data);
    }

    public Matrix4x4(float m00, float m10, float m20, float m30, float m01, float m11, float m21, float m31, float m02, float m12, float m22, float m32,
            float m03, float m13, float m23, float m33) {
        this.data[0] = m00;
        this.data[1] = m10;
        this.data[2] = m20;
        this.data[3] = m30;
        this.data[4] = m01;
        this.data[5] = m11;
        this.data[6] = m21;
        this.data[7] = m31;
        this.data[8] = m02;
        this.data[9] = m12;
        this.data[10] = m22;
        this.data[11] = m32;
        this.data[12] = m03;
        this.data[13] = m13;
        this.data[14] = m23;
        this.data[15] = m33;
    }

    private void setIdentity() {
        Matrix.setIdentityM(this.getArray(), 0);
    }

    public static Matrix4x4 createRotationX(float angle) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.setRotateEulerM(matrix.getArray(), 0, angle, 0, 0);
        return matrix;
    }

    public static Matrix4x4 createRotationY(float angle) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.setRotateEulerM(matrix.getArray(), 0, 0, angle, 0);
        return matrix;
    }

    public static Matrix4x4 createRotationZ(float angle) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.setRotateEulerM(matrix.getArray(), 0, 0, 0, angle);
        return matrix;
    }

    public static Matrix4x4 createScale(float scale) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.scaleM(matrix.getArray(), 0, scale, scale, scale);
        return matrix;
    }

    public static Matrix4x4 createScale(float x, float y, float z) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.scaleM(matrix.getArray(), 0, x, y, z);
        return matrix;
    }

    public static Matrix4x4 createTranslation(float x, float y, float z) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.translateM(matrix.getArray(), 0, x, y, z);
        return matrix;
    }

    public static Matrix4x4 multiply(Matrix4x4 m1, Matrix4x4 m2) {
        Matrix4x4 result = new Matrix4x4();
        Matrix.multiplyMM(result.getArray(), 0, m1.getArray(), 0, m2.getArray(), 0);
        return result;
    }

    public static Vector4 multiply(Matrix4x4 m1, Vector4 m2) {
        float[] resultVector = new float[4];

        Matrix.multiplyMV(resultVector, 0, m1.getArray(), 0, m2.vect, 0);

        return new Vector4(resultVector[0], resultVector[1], resultVector[2], resultVector[3]);

    }

    public Vector4 getTranspose() {
        //        Matrix4x4 matrix = new Matrix4x4();
        //        Matrix.tr(matrix.getArray(), 0, x, y, z);
        return null;
    }

    public Matrix4x4 multiply(Matrix4x4 m1) {

        Matrix.multiplyMM(this.getArray(), 0, this.getArray(), 0, m1.getArray(), 0);
        return this;
    }

    public Vector4 multiply(Vector4 v) {
        float[] resultVector = new float[4];

        Matrix.multiplyMV(resultVector, 0, this.getArray(), 0, v.vect, 0);

        return new Vector4(resultVector[0], resultVector[1], resultVector[2], resultVector[3]);

    }

    public Matrix4x4 rotate(float angle, float x, float y, float z) {

        Matrix.setRotateEulerM(this.getArray(), 0, x, y, z);
        return this;

    }

    public Matrix4x4 rotateX(float angle) {
        Matrix.setRotateM(this.getArray(), 0, angle, 1, 0, 0);
        return this;
    }

    public Matrix4x4 rotateY(float angle) {
        Matrix.setRotateM(this.getArray(), 0, angle, 0, 1, 0);
        return this;
    }

    public Matrix4x4 rotateZ(float angle) {
        Matrix.setRotateM(this.getArray(), 0, angle, 0, 0, 1);
        return this;
    }

    public Matrix4x4 scale(float s) {
        Matrix.scaleM(this.getArray(), 0, s, s, s);
        return this;

    }

    public Matrix4x4 scale(float x, float y, float z) {
        Matrix.scaleM(this.getArray(), 0, x, y, z);
        return this;

    }

    public Matrix4x4 translate(float x, float y, float z) {
        Matrix.translateM(this.getArray(), 0, x, y, z);
        return this;
    }

    public Matrix4x4 setOrthogonalProjection(float left, float right, float bottom, float top, float near, float far) {
        Matrix4x4 matrix = new Matrix4x4();
        Matrix.orthoM(matrix.getArray(), 0, left, right, bottom, top, near, far);
        return matrix;
    }

    public Matrix4x4 setPerspectiveProjection(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(this.getArray(), 0, left, right, bottom, top, near, far);
        return this;
    }

    public float[] getArray() {
        return this.data;
    }

}
