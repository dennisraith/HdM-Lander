
package de.hdm.spe.lander.models;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;

import java.io.IOException;
import java.io.InputStream;


public class Lander extends Square implements DrawableObject {

    enum LanderState {
        ACCELERATING(new Vector2(0, .5f)),
        GRAVITY(new Vector2());

        Vector2 mSpeed;

        LanderState(Vector2 speed) {
            this.mSpeed = speed;
        }

    }

    private final static String resName      = "landerv1.obj";
    public final static String  textureName  = "space.png";

    private Mesh                mesh;
    private final Material      material;
    private final Matrix4x4     world        = new Matrix4x4();
    private final Gravity       mGravity;
    private LanderState         mLanderState = LanderState.GRAVITY;

    public Lander(Gravity gravity) {
        this.material = new Material();
        this.mGravity = gravity;
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    public void setAccelerating(boolean accelerating) {
        if (accelerating) {
            this.mLanderState = LanderState.ACCELERATING;
        }
        else {
            this.mLanderState = LanderState.GRAVITY;
        }
    }

    public void updatePosition() {
        Vector2 speed = this.mGravity.getAbsoluteSpeed(this.mLanderState.mSpeed);
        this.world.translate(speed.getX(), speed.getY(), 0);
        Log.d(this.getClass().getName(), "Speed: X: " + speed.getX() + " Y: " + speed.getY());
        Vector4 v4 = this.world.multiply(new Vector4(speed, 0, 1));
        this.setPosition(new Vector2(v4.getX(), v4.getY()));
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
