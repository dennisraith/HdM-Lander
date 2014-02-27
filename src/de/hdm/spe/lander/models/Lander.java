
package de.hdm.spe.lander.models;

import android.content.Context;
import android.graphics.RectF;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;
import java.io.InputStream;


public class Lander extends Square implements DrawableObject {

    enum LanderState {
        ACCELERATING(new Vector2(0, .8f)),
        GRAVITY(new Vector2());

        Vector2 mSpeed;

        LanderState(Vector2 speed) {
            this.mSpeed = speed;
        }

    }

    private Mesh            mesh;
    private final Material  material;
    private final Matrix4x4 world            = new Matrix4x4();
    private final Gravity   mGravity;
    private LanderState     mLanderState     = LanderState.GRAVITY;
    private float           acc_X            = 0;
    private float           accelerationTime = 0;
    private float           gravityAccTime   = 0;
    private final Vector2   gravity          = new Vector2(0, .4f);

    private boolean         isAccelerating;
    private Vector2         mCurrentSpeed    = new Vector2();

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
            this.accelerationTime = 0;
        }
        else {
            this.mLanderState = LanderState.GRAVITY;
            this.gravityAccTime = 0;
        }
    }

    public void setAccelerating(boolean accelerating) {
        if (this.isAccelerating != accelerating) {
            this.isAccelerating = accelerating;
            this.onAccelerationChanged();
        }
    }

    private Vector2 calculateSpeed(Vector2 shipSpeed, Vector2 gravity) {
        return shipSpeed.subtract(gravity);
    }

    public void updatePosition(float deltaTime) {
        this.accelerationTime += deltaTime;
        this.gravityAccTime += deltaTime;

        Vector2 shipspeed = this.accelerate(this.mLanderState.mSpeed, this.accelerationTime);
        Vector2 gravity = this.accelerate(this.gravity, this.gravityAccTime);
        Vector2 velocity = Vector2.add(this.calculateSpeed(shipspeed, gravity), new Vector2(this.acc_X, 0));
        this.moveShip(velocity);
        this.acc_X = 0;
        this.mCurrentSpeed = velocity;
    }

    private void moveShip(Vector2 velocity) {
        //        Vector2 translation = Vector2.add(velocity, this.mCurrentSpeed);

        this.world.translate(velocity.getX(), velocity.getY(), 0);
        Vector4 v4 = this.world.multiply(new Vector4(velocity, 0, 1));
        this.setPosition(new Vector2(v4.getX(), v4.getY()));
    }

    private Vector2 accelerate(Vector2 speed, float time) {
        float factor = time;
        if (factor > 1) {
            factor = 1;
        }
        return new Vector2(speed.getX(), speed.getY() * factor);
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
