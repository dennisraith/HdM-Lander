
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.DrawableObject;

import java.io.IOException;
import java.io.InputStream;


public class Fire implements DrawableObject {

    private Matrix4x4      mWorld    = new Matrix4x4();
    private Mesh           mMesh;
    private final Material mMaterial = new Material();

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open("fire.obj");
        this.mMesh = Mesh.loadFromOBJ(in);
        in = context.getAssets().open("fire.png");
        this.mMaterial.setTexture(device.createTexture(in));
        this.mWorld.scale(2);

    }

    @Override
    public Mesh getMesh() {
        return this.mMesh;
    }

    @Override
    public Material getMaterial() {
        return this.mMaterial;
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.mWorld;
    }

    public void setWorld(Matrix4x4 world) {
        this.mWorld = new Matrix4x4(world);
        this.mWorld.translate(0, -6, 0);
    }

}
