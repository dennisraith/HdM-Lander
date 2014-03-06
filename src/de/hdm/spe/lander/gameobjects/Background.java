
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.DrawableObject;

import java.io.IOException;
import java.io.InputStream;


public class Background implements DrawableObject {

    protected Mesh           mObject;
    protected final Material material;
    protected Matrix4x4      mWorld;

    private String           fileName = "bg_earth.jpg";

    public Background() {
        this.material = new Material();
        this.mWorld = new Matrix4x4();
    }

    public Background(String imgName) {
        this();
        this.fileName = imgName;
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open("bg_earth.obj");
        this.mObject = Mesh.loadFromOBJ(in);
        in = context.getAssets().open(this.fileName);
        this.material.setTexture(device.createTexture(in));

    }

    public Matrix4x4 autoScale(float dpWidth, float dpHeight) {

        return this.autoScale(dpWidth, dpHeight, 0, 0);
    }

    public Matrix4x4 autoScale(float dpWidth, float dpHeight, float customScaleX, float customScaleY) {

        float bgheight = this.mObject.getBounds().height();
        float bgwidth = this.mObject.getBounds().width();

        float scaleWidth = dpWidth / bgwidth;
        float scaleHeight = dpHeight / bgheight;
        if (customScaleX != 0) {
            scaleWidth = scaleWidth + (scaleWidth / customScaleX);
        }
        if (customScaleY != 0) {
            scaleHeight = scaleHeight + (scaleHeight / customScaleY);
        }
        this.mWorld = new Matrix4x4().scale(scaleWidth, scaleHeight - 2, 1).translate(0, 0, -2);
        return this.mWorld;
    }

    public void setBackground(String name) {
        this.fileName = name;
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
