
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;

import java.io.IOException;


public class Obstacles {

    private final Obstacle[] obstacles;

    public Obstacles(int count, int width, int height) {
        this.obstacles = Obstacle.getRandomObstacles(count, width, height);

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

    }

    public boolean collide(Lander lander) {

        for (int i = 0; i < this.obstacles.length; i++) {
            if (lander.intersects(this.obstacles[i])) {
                return true;
            }
        }
        return false;

    }

    static class Obstacle extends Square {

        public Obstacle(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        @Override
        public void prepare(Context context, GraphicsDevice device) throws IOException {
            this.generateMesh();
        }

        public static Obstacle newRandom(int width, int height) {
            Obstacle ob;

            double x = Math.random() * 50;
            double y = Math.random() * 50;

            if (Math.random() > 0.5)
            {
                x = -x;
            }
            if (Math.random() > 0.5)
            {
                y = -y;
            }

            ob = new Obstacle((int) x, (int) y, width, height);

            return ob;
        }

        public static Obstacle[] getRandomObstacles(int count, int width, int height) {
            Obstacle[] obs = new Obstacle[count];
            for (int i = 0; i < count; i++) {
                obs[i] = Obstacle.newRandom(width, height);
            }
            return obs;
        }

    }
}