
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;

import java.io.IOException;
import java.io.InputStream;


public class Lander extends Square implements DrawableObject {

    private final static String resName     = "landerv1.obj";
    public final static String  textureName = "space.png";

    private Mesh                mesh;
    private final Material      material;
    private final Matrix4x4     world       = new Matrix4x4();

    public Lander() {
        this.material = new Material();
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    public void applyGravity() {

    }

    public void translate(Vector2 direction) {
        this.world.translate(direction.getX(), direction.getY(), 0);
        Vector4 v4 = this.world.multiply(new Vector4(direction, 0, 1));
        this.setPosition(new Vector2(v4.getX(), v4.getY()));

        //        Log.d(this.getClass().getName(), "Lander Position ");
        //        this.getPosition().log();
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.world;
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream stream;
        stream = context.getAssets().open(Lander.resName);
        this.mesh = Mesh.loadFromOBJ(stream);
        this.setBounds(this.mesh.getBounds());
        stream = context.getAssets().open("texture2.bmp");
        this.material.setTexture(device.createTexture(stream));
    }
}
