
package de.hdm.spe.lander.game;

import android.content.Context;
import android.graphics.BitmapFactory;

import de.hdm.spe.lander.engine.Mesh;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Material;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.Texture;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MGDGame extends Game {

    private Mesh            mesh;
    private Mesh            mesh2;
    private float           distance = 0;
    private float           angle    = 0;
    private final Renderer  mRenderer;
    private final Matrix4x4 mWorld;
    Texture                 background;

    public MGDGame(Context context) {
        super(context);
        this.mRenderer = new Renderer(new GraphicsDevice());
        this.mWorld = new Matrix4x4();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.resize(width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        this.mRenderer.initialize(gl);
        //        SpriteFont font = new SpriteFont(this.mRenderer.getGraphicsDevice(), Typeface.DEFAULT, 10f);
        try {
            this.background = this.mRenderer.getGraphicsDevice().createTexture(BitmapFactory.decodeStream(this.mContext.getAssets().open("space.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update(float deltaSeconds) {

        this.distance += 0.5;
        this.distance = (this.distance > 0) ? this.distance * 0.01f : this.distance * .01f;
        this.angle += 15;
        if (this.angle >= 360) {
            this.angle = 0;
        }
        this.mWorld.rotateX(this.angle);
        this.mWorld.translate(-this.distance, this.distance, 0);
    }

    @Override
    public void draw(float deltaSeconds) {
        Material defaultMat = new Material();
        try {

            Texture t = this.mRenderer.getGraphicsDevice().createTexture(this.mContext.getAssets().open("texture2.bmp"));
            defaultMat.setTexture(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mRenderer.clearBuffer();
        this.mRenderer.getGraphicsDevice().bindTexture(this.background);

        this.mRenderer.drawMesh(defaultMat, this.mesh, this.mWorld);

        //        this.mRenderer.drawMesh(defaultMat, this.mesh2, this.mWorld);

    }

    @Override
    public void resize(int width, int height) {
        this.mRenderer.resize(width, height);
    }

    @Override
    public void loadContent() {

        try {

            this.mesh = Mesh.loadFromObj(this.mContext.getAssets().open("monkey.obj"));
            this.mesh2 = Mesh.loadFromObj(this.mContext.getAssets().open("monkey-translate.obj"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
