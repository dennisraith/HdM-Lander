
package de.hdm.spe.lander.gameobjects;

import android.content.Context;
import android.graphics.RectF;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;
import de.hdm.spe.lander.models.DrawableObject;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.MediaManager.LanderSound;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;
import java.io.InputStream;


public class Lander extends Square implements DrawableObject {

    private static float vehicleSpeedY = .6f;

    enum VehicleState {
        ACCELERATING(new Vector2(0, Lander.vehicleSpeedY)),
        GRAVITY(new Vector2());

        Vector2 velocity;

        VehicleState(Vector2 speed) {
            this.velocity = speed;
        }

    }

    private Mesh            mesh;
    private final Material  material;
    private final Matrix4x4 world           = new Matrix4x4();
    private VehicleState    state           = VehicleState.GRAVITY;

    private float           horizontalSpeed = 0;
    private float           vehAccTime      = 0;
    private float           gravAccTime     = 0;

    private Vector2         gravityVector   = new Vector2();
    private Vector2         mCurrentSpeed   = new Vector2();

    private boolean         isAccelerating;

    private final Fire      mFire;

    public Lander(Difficulty gravity) {
        this.material = new Material();
        this.gravityVector = gravity.getGravityVector();
        this.world.translate(0, 90, 0);
        this.mFire = new Fire();
    }

    private void onAccelerationChanged() {
        if (this.isAccelerating) {
            this.state = VehicleState.ACCELERATING;
            this.vehAccTime = 0;
            MediaManager.getInstance().playSound(LanderSound.RocketBurst);
        }
        else {
            this.state = VehicleState.GRAVITY;
            this.gravAccTime = 0;
            this.state.velocity = this.getCurrentSpeed();
            MediaManager.getInstance().stopSound(LanderSound.RocketBurst);
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
        this.vehAccTime += deltaTime;
        this.gravAccTime += deltaTime;

        Vector2 shipspeed = this.accelerate(this.state.velocity, this.vehAccTime);
        Vector2 gravity = this.accelerate(this.gravityVector, this.gravAccTime);
        Vector2 velocity = Vector2.add(this.calculateSpeed(shipspeed, gravity), new Vector2(this.horizontalSpeed, 0));

        this.moveShip(velocity);
        this.mCurrentSpeed = velocity;
        VehicleState.GRAVITY.velocity = new Vector2();
        this.horizontalSpeed = 0;
        this.mFire.setWorld(this.getWorld());
    }

    private void moveShip(Vector2 velocity) {
        this.world.translate(velocity.getX(), velocity.getY(), 0);
        Vector4 v4 = this.world.multiply(new Vector4(velocity, 0, 1));
        this.setPosition(new Vector2(v4.getX(), v4.getY()));
    }

    private Vector2 accelerate(Vector2 speed, float time) {
        if (time > 1) {
            time = 1;
        }
        return new Vector2(speed.getX(), speed.getY() * time);
    }

    public void draw(Renderer renderer) {
        renderer.draw(this);
        if (this.isAccelerating) {
            renderer.draw(this.mFire);
        }
    }

    @Override
    public Matrix4x4 getWorld() {
        return this.world;
    }

    public void onAccelerometerEvent(float[] values) {
        this.horizontalSpeed = values[0];
    }

    public boolean isAccelerating() {
        return this.isAccelerating;
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream stream;
        stream = context.getAssets().open(Static.sLanderMesh);
        this.mesh = Mesh.loadFromOBJ(stream);
        RectF b = this.mesh.getBounds();
        RectF bounds = new RectF(b.left, b.top, b.right, b.bottom - 12);
        this.setBounds(bounds);
        this.getWorld().translate(0, 0, -2).scale(1.4f);
        stream = context.getAssets().open(Static.sLanderTex);
        this.material.setTexture(device.createTexture(stream));
        this.mFire.prepare(context, device);
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

}
