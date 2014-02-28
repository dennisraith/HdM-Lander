
package de.hdm.spe.lander.gameobjects;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;

import java.io.IOException;


public class Platform extends Square {

    public Platform() {
        super(0, -99, 50, 2);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        this.generateMesh();
    }
}
