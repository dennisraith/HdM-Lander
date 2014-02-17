
package de.hdm.spe.lander.graphics;

import android.util.Log;

import de.hdm.spe.lander.engine.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;

import javax.microedition.khronos.opengles.GL10;


public class Renderer {

    private final GraphicsDevice graphics;
    private final Camera         mCamera;

    public Renderer(GraphicsDevice graphics) {
        this.graphics = graphics;

        this.mCamera = new Camera();
        Matrix4x4 projection = new Matrix4x4();
        Matrix4x4 view = new Matrix4x4();
        view.translate(0, 0, -7);
        projection.setPerspectiveProjection(-.1f, .1f, -.1f, .1f, .1f, 100f);
        this.mCamera.setProjectionMatrix(projection);
        this.mCamera.setViewMatrix(view);

    }

    public void initialize(GL10 context) {
        this.graphics.onSurfaceCreated(context);

        this.setCamera(this.mCamera);
    }

    public void setViewPort(int x, int y, int width, int height) {
        this.graphics.setViewPort(x, y, width, height);
        //        this.resize(width, height);
    }

    public void resize(int width, int height) {

        float aspect = (float) width / height;
        if (this.mCamera != null) {
            this.mCamera.getProjectionMatrix().setPerspectiveProjection(aspect * -.1f, aspect * .1f, -.1f, .1f, .1f, 100f);
            this.setCamera(this.mCamera);
            Log.d(this.getClass().getName(), "Resized with non-null camera");
        }
        this.setViewPort(0, 0, width, height);
        Log.d(this.getClass().getName(), "Resized");
    }

    public void setCamera(Camera cam) {
        this.graphics.setCamera(cam);

    }

    public void clearBuffer() {
        this.graphics.clear(.1f, .1f, 1f, 1.0f, 1.0f);
    }

    @Deprecated
    public void drawMesh(Mesh mesh, Matrix4x4 world) {
        this.setMaterial(new Material());

        this.graphics.bindVertexBuffer(mesh.getBuffer());

        this.graphics.setWorldMatrix(world);

        this.graphics.draw(mesh.getMode(), 0, mesh.getBuffer().getVertexCount());
        this.graphics.unbindVertexBuffer(mesh.getBuffer());
    }

    public void drawMesh(Material mat, Mesh mesh, Matrix4x4 world) {
        this.setMaterial(mat);

        this.graphics.bindVertexBuffer(mesh.getBuffer());

        this.graphics.setWorldMatrix(world);

        this.graphics.draw(mesh.getMode(), 0, mesh.getBuffer().getVertexCount());
        this.graphics.unbindVertexBuffer(mesh.getBuffer());
        this.graphics.unbindTexture();
    }

    private void setMaterial(Material mat) {
        if (mat == null) {
            Log.e(this.getClass().getName(), "ERROR - Received material is null!");
            return;
        }
        this.graphics.bindTexture(mat.getTexture());

        this.graphics.setAlphaTest(mat.getCompareValue(), mat.getAlphaTestReference());
        this.graphics.setBlendFactors(mat.getSourceBlendFactor(), mat.getDestinationBlendFactor());
        this.graphics.setCullSide(mat.getCullSide());
        this.graphics.setDepthRead(mat.getDepthTest());
        this.graphics.setDepthWrite(mat.isDepthWriteActive());
        this.graphics.setMaterialColor(mat.getMaterialColor());
        this.graphics.setTextureEnvironmentColor(mat.getTextureEnvironmentColor());
        this.graphics.setTextureBlendMode(mat.getTextureBlendMode());
        this.graphics.setTextureFilters(mat.getTextureFilterMin(), mat.getTextureFilterMag());
        this.graphics.setTextureWrapMode(mat.getWrapModeU(), mat.getWrapModeV());
    }

    public GraphicsDevice getGraphicsDevice() {
        return this.graphics;
    }

}
