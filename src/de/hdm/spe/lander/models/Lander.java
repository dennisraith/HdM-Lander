
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.graphics.Texture;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.IOException;
import java.io.InputStream;


public class Lander implements DrawableObject {

    private final static String resName     = "landerv1.obj";
    public final static String  textureName = "space.png";

    private Mesh                mesh;
    private final Material      material;
    private Texture             texture;
    private final Matrix4x4     world       = new Matrix4x4();

    public Lander() {
        this.material = new Material();
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    public void setTexture(Texture t) {
        this.texture = t;
        this.material.setTexture(this.texture);
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.world;
    }

    @Override
    public void prepare(Context context) throws IOException {
        InputStream stream;
        stream = context.getAssets().open(Lander.resName);
        this.mesh = Mesh.loadFromOBJ(stream);
        this.world.scale(.3f);
    }
}
