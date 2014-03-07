
package de.hdm.spe.lander.gameobjects;

import android.content.Context;
import android.graphics.RectF;

import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Mesh;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector4;
import de.hdm.spe.lander.models.DrawableObject;
import de.hdm.spe.lander.models.Fuel;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.MediaManager.SoundEffect;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;
import java.io.InputStream;


/**
 * Class representing the ship which has to be landed in a level
 * @author Dennis
 *
 */
public class Lander extends AABB implements DrawableObject {

    /**
     * the maximum vertical speed the ship can reach
     */
    private static float vehicleSpeedY = .7f;

    /**
     * Enum for controlling the state in which the ship is
     * @author Dennis
     *
     */
    enum VehicleState {
        ACCELERATING(new Vector2(0, Lander.vehicleSpeedY)),
        GRAVITY(new Vector2());

        Vector2 velocity;

        VehicleState(Vector2 speed) {
            this.velocity = speed;
        }

    }

    private Mesh             mesh;
    private final Material   material;
    private final Matrix4x4  world           = new Matrix4x4();
    private VehicleState     state           = VehicleState.GRAVITY;

    private float            horizontalSpeed = 0;
    private float            vehAccTime      = 0;
    private float            gravAccTime     = 0;

    private Vector2          gravityVector   = new Vector2();
    private Vector2          mCurrentSpeed   = new Vector2();

    private boolean          isAccelerating;

    private final Fire       mFire;
    private final Fuel       mFuel;
    private final Difficulty difficulty;

    /**
     * Create a new ship with the given {@link Difficulty} (gravity, fuel)
     * @param difficulty
     */
    public Lander(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.material = new Material();
        this.gravityVector = difficulty.getGravityVector();
        this.world.translate(0, 40, 0);
        this.mFire = new Fire();
        this.mFuel = new Fuel(difficulty);
        this.mFire.setWorld(this.world);

    }

    /**
     * Called when the vehicle state has changed
     */
    private void onAccelerationChanged() {
        if (this.isAccelerating) {
            this.state = VehicleState.ACCELERATING;
            this.vehAccTime = 0;
            MediaManager.getInstance().playSound(SoundEffect.RocketBurst);

        }
        else {
            this.state = VehicleState.GRAVITY;
            this.gravAccTime = 0;
            if (this.mFuel.isEmpty()) {
                this.state.velocity = new Vector2(0, this.getCurrentSpeed().getY());
            }
            else {
                this.state.velocity = new Vector2();
            }
            MediaManager.getInstance().stopSound(SoundEffect.RocketBurst);
        }

    }

    /**
     * Set whether the ship is accelerating or only exposed to gravity
     * @param accelerating
     */
    public void setAccelerating(boolean accelerating) {
        if (this.isAccelerating != accelerating && !this.mFuel.isEmpty()) {
            this.isAccelerating = accelerating;
            this.onAccelerationChanged();
        }
    }

    /**
     * Calculate the resulting velocity vector of the ship
     * @param shipSpeed
     * @param gravity
     * @return resulting velocity
     */
    private Vector2 calculateSpeed(Vector2 shipSpeed, Vector2 gravity) {
        return shipSpeed.subtract(gravity);
    }

    /**
     * update the current position of the ship and calculate its velocity
     * @param deltaTime
     */
    public void updatePosition(float deltaTime) {
        this.vehAccTime += deltaTime;
        this.gravAccTime += deltaTime;
        if (this.isAccelerating) {
            this.mFuel.onAccelerating(deltaTime);
        }

        Vector2 shipspeed = this.accelerate(this.state.velocity, this.vehAccTime);
        Vector2 gravity = this.accelerate(this.gravityVector, this.gravAccTime);
        Vector2 velocity = Vector2.add(this.calculateSpeed(shipspeed, gravity), new Vector2(this.horizontalSpeed, 0));

        this.moveShip(velocity);
        this.mCurrentSpeed = velocity;
        this.horizontalSpeed = 0;
        this.mFire.setWorld(this.world);

        if (this.mFuel.isEmpty()) {
            this.isAccelerating = false;
            this.onAccelerationChanged();
        }
    }

    /**
     * move the ship by the velocity vector
     * @param velocity
     */
    private void moveShip(Vector2 velocity) {
        this.world.translate(velocity.getX(), velocity.getY(), 0);
        Vector4 v4 = this.world.multiply(new Vector4(velocity, 0, 1));
        this.setPosition(new Vector2(v4.getX(), v4.getY()));
    }

    /**
     * accelerate the given vector (ship/gravity) over time (tracked by vehAccTime and gravAccTime)
     * @param speed
     * @param time
     * @return
     */
    private Vector2 accelerate(Vector2 speed, float time) {
        if (time > 1) {
            time = 1;
        }
        return new Vector2(speed.getX() * time, speed.getY() * time);
    }

    /**
     * draw the ship and its components
     * @param renderer
     */
    public void draw(Renderer renderer) {
        renderer.draw(this);
        if (this.isAccelerating) {
            renderer.draw(this.mFire);
        }
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#getWorld()
     */
    @Override
    public Matrix4x4 getWorld() {
        return this.world;
    }

    /**
     * react to the rotation of the device
     * @param values
     */
    public void onDeviceRotationEvent(float[] values) {
        this.horizontalSpeed = values[0] * 1.6f;
    }

    /**
     * @return whether the Ship is currently accelerating
     */
    public boolean isAccelerating() {
        return this.isAccelerating;
    }

    /*
     * (non-Javadoc)
     * @see de.hdm.spe.lander.models.DrawableObject#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        InputStream stream;
        stream = context.getAssets().open(Static.sLanderMesh);
        this.mesh = Mesh.loadFromOBJ(stream);
        RectF b = this.mesh.getBounds();
        RectF bounds = new RectF(b.left, b.top, b.right, b.bottom - 5);
        this.setBounds(bounds);
        this.getWorld().translate(0, 0, -2).scale(1.4f);
        stream = context.getAssets().open(Static.sLanderTex);
        this.material.setTexture(device.createTexture(stream));
        this.mFire.prepare(context, device);
    }

    /**
     * set the bounds of the mesh and update the ships position
     * @param bounds
     */
    private void setBounds(RectF bounds) {
        this.BOTTOM_LEFT = new Vector2(bounds.left, bounds.bottom);
        this.TOP_RIGHT = new Vector2(bounds.right, bounds.top);
        this.moveShip(new Vector2());
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

    /**
     * @return the current speed of the ship
     */
    public Vector2 getCurrentSpeed() {
        return this.mCurrentSpeed;
    }

    /**
     * @return the used difficulty
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * @return the {@link Fuel} instance of this ship
     */
    public Fuel getFuel() {
        return this.mFuel;
    }

}
