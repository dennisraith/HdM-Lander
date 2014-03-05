
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;

import java.io.IOException;


public class Obstacles {

    private final Asteroid[] obstacles;

    public Obstacles(float size, int count) {
        this.obstacles = this.newRandom(size, count);
    }

    public Obstacles(Asteroid[] data) {
        this.obstacles = data;

    }

    public void prepare(Context context, GraphicsDevice device) throws IOException {
        for (int i = 0; i < this.obstacles.length; i++) {
            this.obstacles[i].prepare(context, device);
        }
    }

    public void draw(float deltaTime, Renderer renderer) {
        for (int i = 0; i < this.obstacles.length; i++) {
            renderer.draw(this.obstacles[i]);
        }
    }

    public void update(float deltaSeconds) {
        for (int i = 0; i < this.obstacles.length; i++) {
            this.obstacles[i].update(deltaSeconds);
        }
    }

    public boolean collide(Lander lander) {

        for (int i = 0; i < this.obstacles.length; i++) {
            if (this.obstacles[i].intersects(lander)) {
                return true;
            }
        }
        return false;

    }

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
