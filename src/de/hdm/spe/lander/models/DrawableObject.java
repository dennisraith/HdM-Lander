
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.IOException;


/**
 * Interface which provides access to all required members for the {@link Renderer} to draw this object
 * @author Dennis
 *
 */
public interface DrawableObject {

    /**
     * Interface method for allowing to forward the prepare call of the GameState to this object
     * @param context
     * @param device
     * @throws IOException
     */
    public void prepare(Context context, GraphicsDevice device) throws IOException;

    /**
     * @return the mesh of this object
     */
    public Mesh getMesh();

    /**
     * @return the material of this object
     */
    public Material getMaterial();

    /**
     * @return the world matrix of this object
     */
    public Matrix4x4 getWorld();

}
