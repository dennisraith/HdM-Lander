
package de.hdm.spe.lander.gameobjects;

import android.content.Context;
import android.graphics.RectF;

import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;
import de.hdm.spe.lander.models.DrawableObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * Class representing the landing area need for winning condition
 * @author Dennis
 *
 */
public class Platform extends AABB implements DrawableObject {

    private static String  fileName   = "platform.obj";
    private static String  texName    = "platform_texture.png";
    private Mesh           mesh;
    private final Material material   = new Material();
    private Matrix4x4      world      = new Matrix4x4();
    private float          position   = 0;

    private boolean        flowsRight = true;

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream in;
        in = context.getAssets().open(Platform.fileName);
        this.mesh = Mesh.loadFromOBJ(in);
        in = context.getAssets().open(Platform.texName);
        this.material.setTexture(device.createTexture(in));
        this.world.translate(0, -95, -3).scale(3, 2, 1);
        this.setBounds(this.mesh.getBounds());
    }

    /**
     * update the bounds and position of the platform
     * @param bounds
     */
    private void setBounds(RectF bounds) {
        Vector4 BL = this.world.multiply(new Vector4(bounds.left, bounds.bottom, 0, 1));
        Vector4 TR = this.world.multiply(new Vector4(bounds.right, bounds.top, 0, 1));

        this.BOTTOM_LEFT = new Vector2(BL.getX(), BL.getY());
        this.TOP_RIGHT = new Vector2(TR.getX(), TR.getY());
    }

    /** Convenience method for updating the position of the Platform, only used if the platform should move
     * @param deltaSeconds
     */
    public void update(float deltaSeconds) {

        if (this.position > .2 || this.position < -.2) {
            this.flowsRight = !this.flowsRight;
        }
        if (this.flowsRight) {
            this.position += deltaSeconds / 5;
        }
        else {
            this.position -= deltaSeconds / 5;
        }

        this.world = Matrix4x4.createTranslation(this.position, 0, 0).multiply(this.world);
        this.setBounds(this.mesh.getBounds());

    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getMesh()
     */
    @Override
    public Mesh getMesh() {
        return this.mesh;
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
        return this.world;
    }
}
