
package de.hdm.spe.lander.graphics;

import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.DrawableObject;


public class Renderer {

    private final GraphicsDevice graphicsDevice;

    public Renderer(GraphicsDevice graphicsDevice) {
        this.graphicsDevice = graphicsDevice;
    }

    public void drawMesh(Mesh mesh, Material material, Matrix4x4 world) {
        this.graphicsDevice.setWorldMatrix(world);

        this.setupMaterial(material);

        VertexBuffer vertexBuffer = mesh.getVertexBuffer();
        this.graphicsDevice.bindVertexBuffer(vertexBuffer);
        this.graphicsDevice.draw(mesh.getMode(), 0, vertexBuffer.getNumVertices());
        this.graphicsDevice.unbindVertexBuffer(vertexBuffer);
    }

    public void drawText(TextBuffer textBuffer, Matrix4x4 world) {
        this.drawMesh(textBuffer.getMesh(), textBuffer.getSpriteFont().getMaterial(), world);
    }

    public void draw(DrawableObject object) {
        this.drawMesh(object.getMesh(), object.getMaterial(), object.getWorld());
    }

    public GraphicsDevice getGraphicsDevice() {
        return this.graphicsDevice;
    }

    private void setupMaterial(Material material) {
        this.graphicsDevice.bindTexture(material.getTexture());
        this.graphicsDevice.setTextureFilters(material.getTextureFilterMin(), material.getTextureFilterMag());
        this.graphicsDevice.setTextureWrapMode(material.getTextureWrapModeU(), material.getTextureWrapModeV());
        this.graphicsDevice.setTextureBlendMode(material.getTextureBlendMode());
        this.graphicsDevice.setTextureBlendColor(material.getTextureBlendColor());

        this.graphicsDevice.setMaterialColor(material.getMaterialColor());
        this.graphicsDevice.setBlendFactors(material.getBlendSourceFactor(), material.getBlendDestFactor());

        this.graphicsDevice.setCullSide(material.getCullSide());
        this.graphicsDevice.setDepthTest(material.getDepthTestFunction());
        this.graphicsDevice.setDepthWrite(material.getDepthWrite());
        this.graphicsDevice.setAlphaTest(material.getAlphaTestFunction(), material.getAlphaTestValue());
    }

}
