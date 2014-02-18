
package de.hdm.spe.lander.graphics;

import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.math.Vector4;


public class Camera {

    private Matrix4x4 projection;
    private Matrix4x4 view;

    public Camera() {
        this.projection = new Matrix4x4();
        this.view = new Matrix4x4();
    }

    public Matrix4x4 getProjection() {
        return this.projection;
    }

    public Matrix4x4 getView() {
        return this.view;
    }

    public void setProjection(Matrix4x4 projection) {
        this.projection = projection;
    }

    public void setView(Matrix4x4 view) {
        this.view = view;
    }

    public Vector3 project(Vector3 v, float w) {
        Matrix4x4 viewProjection = this.projection.multiply(this.view);
        Vector4 result = viewProjection.multiply(new Vector4(v, w));
        return new Vector3(
                result.getX() / result.getW(),
                result.getY() / result.getW(),
                result.getZ() / result.getW());
    }

    public Vector3 unproject(Vector3 v, float w) {
        Matrix4x4 viewProjection = this.projection.multiply(this.view);
        Matrix4x4 inverse = viewProjection.getInverse();
        Vector4 result = inverse.multiply(new Vector4(v, w));
        return new Vector3(
                result.getX() / result.getW(),
                result.getY() / result.getW(),
                result.getZ() / result.getW());
    }

}
