
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

    /**
     * Constructor for specifiying a custom image from the assets
     * @param imgName file name of the file in the asset directory
     */
    public Background(String imgName) {
        this();
        this.fileName = imgName;
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open("bg_earth.obj");
        this.mObject = Mesh.loadFromOBJ(in);
        in = context.getAssets().open(this.fileName);
        this.material.setTexture(device.createTexture(in));

    }

    /**
     * method for auto-scaling the background of the menus to the screen size
     * @param dpWidth
     * @param dpHeight
     * @param customScaleX
     * @param customScaleY
     * @return
     */
    public Matrix4x4 scaleForMenu(float dpWidth, float dpHeight, float customScaleX, float customScaleY) {

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

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getMesh()
     */
    @Override
    public Mesh getMesh() {
        return this.mObject;
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getMaterial()
     */
    @Override
    public Material getMaterial() {
        return this.material;
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getWorld()
     */
    @Override
    public Matrix4x4 getWorld() {
        return this.mWorld;
    }

}
