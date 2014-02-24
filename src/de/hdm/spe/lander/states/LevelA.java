
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.Static;
import de.hdm.spe.lander.graphics.Background;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.models.Lander;

import java.io.IOException;


public class LevelA implements GameState {

    private final Lander     mLander;
    private final Camera     mCamera;
    TextBuffer               mText;
    private final Matrix4x4  mTextWorld;
    private final Background mBG;

    public LevelA() {
        this.mCamera = new Camera();

        this.mTextWorld = new Matrix4x4();
        this.mBG = new Background();
        this.mLander = new Lander();
        this.mTextWorld.translate(0, 90, 0).scale(.25f);
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mBG);
        renderer.drawText(this.mText, this.mTextWorld);
        renderer.draw(this.mLander);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        SpriteFont font = device.createSpriteFont(null, 32);
        this.mText = device.createTextBuffer(font, 32);
        this.mText.setText("TEXTBUFFER");

        this.mBG.prepare(context, device);
        this.mBG.getWorld().translate(0, 0, -20).scale(14, 14, 0);
        this.mLander.prepare(context, device);

    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.translate(new Vector2(0, -.1f));
        Vector3 v3 = this.getCamera().project(new Vector3(this.mLander.getPosition(), 0), 1);
        this.mText.setText("Y: " + v3.getY());
    }

    @Override
    public void resize(int width, int height) {

        float aspect = (float) width / (float) height;
        Log.d(this.getClass().getName(), "Width: " + width + " Height: " + height + " Aspect: " + aspect);
        this.setProjection(aspect);

    }

    private void setProjection(float aspect) {
        Matrix4x4 projection = new Matrix4x4();
        if (aspect > 1) {
            projection.setOrthogonalProjection(-Static.CAM_DIMEN, Static.CAM_DIMEN, -Static.CAM_DIMEN * aspect, Static.CAM_DIMEN * aspect, Static.CAM_NEAR,
                    Static.CAM_FAR);
        }
        else {
            projection.setOrthogonalProjection(-Static.CAM_DIMEN * aspect, Static.CAM_DIMEN * aspect, -Static.CAM_DIMEN, Static.CAM_DIMEN, Static.CAM_NEAR,
                    Static.CAM_FAR);
        }
        this.mCamera.setProjection(projection);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public Camera getCamera() {
        return this.mCamera;
    }

}
