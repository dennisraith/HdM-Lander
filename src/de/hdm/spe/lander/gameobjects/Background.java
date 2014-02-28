
package de.hdm.spe.lander.gameobjects;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.DrawableObject;

import java.io.IOException;
import java.io.InputStream;


public class Background implements DrawableObject {

    private Mesh            mObject;
    private final Material  material;
    private final Matrix4x4 mWorld;

    public Background() {
        this.material = new Material();
        this.mWorld = new Matrix4x4();
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open("spacebg.obj");
        this.mObject = Mesh.loadFromOBJ(in);
        in = context.getAssets().open("space.png");
        this.material.setTexture(device.createTexture(in));
        Log.d(this.getClass().getName(), "BG dimension: height:" + this.mObject.getBounds().height() + " width:" + this.mObject.getBounds().width());
    }

    @Override
    public Mesh getMesh() {
        return this.mObject;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.mWorld;
    }

}
