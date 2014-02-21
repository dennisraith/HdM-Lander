
package de.hdm.spe.lander.models;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;

import java.io.IOException;
import java.io.InputStream;


public class Lander extends Square implements DrawableObject {

    private final static String resName     = "landerv1.obj";
    public final static String  textureName = "space.png";

    private Mesh                mesh;
    private final Material      material;
    private Matrix4x4           world       = new Matrix4x4();

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

    public void translate(float length) {
        this.world = this.world.translate(0, length, 0);

        this.setPosition(Vector2.add(this.getPosition(), new Vector2(0, length)));
        Log.d(this.getClass().getName(), "Lander Position ");
        this.getPosition().log();
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
        this.setBounds(this.mesh.getBounds());
    }
}
