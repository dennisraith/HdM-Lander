
package de.hdm.spe.lander.graphics;

import de.hdm.spe.lander.math.Matrix4x4;


public class Camera {

    private Matrix4x4 mProjection;
    private Matrix4x4 mView;

    public Matrix4x4 getViewMatrix() {
        return this.mView;
    }

    public void setViewMatrix(Matrix4x4 mView) {
        this.mView = mView;
    }

    public Matrix4x4 getProjectionMatrix() {
        return this.mProjection;
    }

    public void setProjectionMatrix(Matrix4x4 mProjection) {
        this.mProjection = mProjection;
    }

}
