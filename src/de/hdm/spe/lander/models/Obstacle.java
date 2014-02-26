
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;

import java.io.IOException;


public class Obstacle extends Square {

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
