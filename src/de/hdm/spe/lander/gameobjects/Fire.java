
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.DrawableObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Dennis
 *
 */
public class Fire implements DrawableObject {

    private Matrix4x4      mWorld    = new Matrix4x4();
    private Mesh           mMesh;
    private final Material mMaterial = new Material();

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open("fire.obj");
        this.mMesh = Mesh.loadFromOBJ(in);
        in = context.getAssets().open("fire.png");
        this.mMaterial.setTexture(device.createTexture(in));
        this.mWorld.scale(2);

    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getMesh()
     */
    @Override
    public Mesh getMesh() {
        return this.mMesh;
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getMaterial()
     */
    @Override
    public Material getMaterial() {
        return this.mMaterial;
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getWorld()
     */
    @Override
    public Matrix4x4 getWorld() {
        return this.mWorld;
    }

    /**
     * assign the {@link Lander}'s world so the fire burst aligns to its position
     * @param world the {@link Lander}'s world
     */
    public void setWorld(Matrix4x4 world) {
        this.mWorld = new Matrix4x4(world);
        this.mWorld.translate(0, -6, 0);
    }

}
