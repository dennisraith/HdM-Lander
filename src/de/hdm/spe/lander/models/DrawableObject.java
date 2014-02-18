
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.IOException;


public interface DrawableObject {

    public void prepare(Context context) throws IOException;

    public Mesh getMesh();

    public Material getMaterial();

    public Matrix4x4 getWorld();

}
