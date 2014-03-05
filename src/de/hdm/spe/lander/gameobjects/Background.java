
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

    private Mesh            mObject;
    private final Material  material;
    private final Matrix4x4 mWorld;

    private String fileName = "bg_earth.jpg";
    
    public Background() {
        this.material = new Material();
        this.mWorld = new Matrix4x4();
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open("bg_earth.obj");
        this.mObject = Mesh.loadFromOBJ(in);
        in = context.getAssets().open(fileName);
        this.material.setTexture(device.createTexture(in));
    }
    
    public void setBackground(String name){
    	fileName = name;
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
