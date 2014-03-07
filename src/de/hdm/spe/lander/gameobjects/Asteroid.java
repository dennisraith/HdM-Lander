
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.Logger;
import de.hdm.spe.lander.collision.Circle;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;
import de.hdm.spe.lander.models.DrawableObject;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Dennis
 *
 */
public class Asteroid extends Circle implements DrawableObject {

    private Mesh            mesh;
    private final Matrix4x4 world       = new Matrix4x4();
    private final Material  material    = new Material();
    private float           scaleFactor = 5;

    public Asteroid() {
    }

    public static Asteroid newInstance(float scale, float x, float y) {
        float rotation = (float) (360 * Math.random());
        Asteroid ast = new Asteroid();
        ast.scaleFactor = scale;
        ast.world.translate(x, y, -20).rotateY(rotation).scale(ast.scaleFactor);
        return ast;
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open(Static.sAsteroidMesh);
        this.mesh = Mesh.loadFromOBJ(in);
        in = context.getAssets().open(Static.sAsteroidTex);
        this.material.setTexture(device.createTexture(in));
        Vector4 center = this.world.multiply(new Vector4(this.mesh.getBounds().centerX(), this.mesh.getBounds().centerY(), 0, 1));
        this.setPosition(new Vector2(center.getX(), center.getY()));
        this.setRadius(Math.abs(this.scaleFactor * (this.mesh.getBounds().width() / 2)));
        Logger.log("Asteroid pos", this.getPosition());
    }

    public void update(float deltaSeconds) {
        this.world.rotateY(deltaSeconds * 5);
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
