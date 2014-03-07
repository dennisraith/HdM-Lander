
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;

import java.io.IOException;


/**
 * @author Dennis
 *Convenience class for generating and handling the {@link Asteroid} obstacles within a level
 */
public class Obstacles {

    private final Asteroid[] obstacles;

    /**
     * generates random obstacles
     * @param size the scaling (1-20)
     * @param count the number of {@link Asteroid}s
     */
    public Obstacles(float size, int count) {
        this.obstacles = this.newRandom(size, count);
    }

    /** Assigns previously generated Obstacles to this instance
     * @param data
     */
    public Obstacles(Asteroid[] data) {
        this.obstacles = data;

    }

    /**
     * Convenience method for preparing all obstacles, called within the Level's prepare method
     * @param context
     * @param device
     * @throws IOException
     */
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        for (int i = 0; i < this.obstacles.length; i++) {
            this.obstacles[i].prepare(context, device);
        }
    }

    /** Convenience method for drawing all obstacles, called within the Level's draw method
     * Convenience
     * @param deltaTime
     * @param renderer
     */
    public void draw(float deltaTime, Renderer renderer) {
        for (int i = 0; i < this.obstacles.length; i++) {
            renderer.draw(this.obstacles[i]);
        }
    }

    /** Convenience method for updating all obstacles, called within the Level's update method
     * @param deltaSeconds
     */
    public void update(float deltaSeconds) {
        for (int i = 0; i < this.obstacles.length; i++) {
            this.obstacles[i].update(deltaSeconds);
        }
    }

    /** Convenience method for collision detection of all obstacles, called within the Level's checkGameState() method
     * @param lander
     * @return
     */
    public boolean collide(Lander lander) {

        for (int i = 0; i < this.obstacles.length; i++) {
            if (this.obstacles[i].intersects(lander)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Generates random Asteroid obstacles with random coordinates (+/-70)
     * @param scale
     * @param count
     * @return Array of generated Obstacles
     */
    public Asteroid[] newRandom(float scale, int count) {
        Asteroid[] asts = new Asteroid[count];
        for (int i = 0; i < count; i++) {
            float x = (float) (Math.random() * 70);
            float y = (float) (Math.random() * 70);

            if (Math.random() > 0.5)
            {
                x = -x;
            }
            if (Math.random() > 0.5)
            {
                y = -y;
            }
            asts[i] = Asteroid.newInstance(scale, x, y);
        }

        return asts;

    }

}
