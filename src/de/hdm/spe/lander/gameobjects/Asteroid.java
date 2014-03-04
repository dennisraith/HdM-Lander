
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.collision.Circle;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.models.DrawableObject;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;
import java.io.InputStream;


public class Asteroid extends Circle implements DrawableObject {

    private Mesh            mesh;
    private final Matrix4x4 world    = new Matrix4x4();
    private final Material  material = new Material();  ;

    public Asteroid() {
        this.world.translate(0, 0, -5).scale(15);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open(Static.sAsteroidMesh);
        this.mesh = Mesh.loadFromOBJ(in);
        in = context.getAssets().open(Static.sAsteroidTex);
        this.material.setTexture(device.createTexture(in));
        this.setCenter(new Vector2(this.mesh.getBounds().centerX(), this.mesh.getBounds().centerY()));
        this.setRadius(this.mesh.getBounds().width() / 2);
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.world;
    }

}
