
package de.hdm.spe.lander.models;

import android.content.Context;
import android.graphics.RectF;

import de.hdm.spe.lander.Static;
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
        ACCELERATING(new Vector2(0, .3f)),
        GRAVITY(new Vector2());

        Vector2 mSpeed;

        LanderState(Vector2 speed) {
            this.mSpeed = speed;
        }

    }

    private Mesh            mesh;
    private final Material  material;
    private final Matrix4x4 world              = new Matrix4x4();
    private final Gravity   mGravity;
    private LanderState     mLanderState       = LanderState.GRAVITY;
    private float           acc_X              = 0;
    private float           gravityTimeElapsed = 0;
    private boolean         isAccelerating;
    private Vector2         mCurrentSpeed      = new Vector2();

    public Lander(Gravity gravity) {
        this.material = new Material();
        this.mGravity = gravity;
        this.world.translate(0, 90, 0);
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    public Vector2 getCurrentSpeed() {
        return this.mCurrentSpeed;
    }

    private void onAccelerationChanged() {
        if (this.isAccelerating) {
            this.mLanderState = LanderState.ACCELERATING;
        }
        else {
            this.mLanderState = LanderState.GRAVITY;
        }
        this.gravityTimeElapsed = 0;
    }

    public void setAccelerating(boolean accelerating) {
        if (this.isAccelerating != accelerating) {
            this.isAccelerating = accelerating;
            this.onAccelerationChanged();
        }
    }

    public void updatePosition(float deltaTime) {
        if (!this.isAccelerating) {
            this.gravityTimeElapsed += deltaTime;
        }
        else {

        }
        Vector2 speed;
        speed = this.mGravity.getAbsoluteSpeed(this.mLanderState.mSpeed, this.gravityTimeElapsed);
        speed = Vector2.add(speed, new Vector2(this.acc_X, 0));
        this.world.translate(speed.getX(), speed.getY(), 0);
        Vector4 v4 = this.world.multiply(new Vector4(speed, 0, 1));
        this.setPosition(new Vector2(v4.getX(), v4.getY()));
        this.acc_X = 0;
        this.mCurrentSpeed = speed;
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.world;
    }

    public void onAccelerometerEvent(float[] values) {
        this.acc_X = values[0];
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream stream;
        stream = context.getAssets().open(Static.sLanderMesh);
        this.mesh = Mesh.loadFromOBJ(stream);
        RectF b = this.mesh.getBounds();
        RectF bounds = new RectF(b.left, b.top, b.right, b.bottom - 7);
        this.setBounds(bounds);
        stream = context.getAssets().open(Static.sLanderTex);
        this.material.setTexture(device.createTexture(stream));
    }
}
