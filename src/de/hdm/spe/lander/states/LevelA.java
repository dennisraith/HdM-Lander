
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.Logger;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.models.Background;
import de.hdm.spe.lander.models.Gravity;
import de.hdm.spe.lander.models.Gravity.Difficulty;
import de.hdm.spe.lander.models.Lander;
import de.hdm.spe.lander.models.Obstacle;
import de.hdm.spe.lander.models.Platform;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;
import java.text.NumberFormat;


public class LevelA extends GameState {

    private final Lander     mLander;
    TextBuffer               mText;
    private final Matrix4x4  mTextWorld;
    private final Background mBG;
    private final Platform   mPlatform;
    private final Obstacle[] mObstacles;
    NumberFormat             format = NumberFormat.getNumberInstance();

    public LevelA(Game game) {
        super(game);
        this.mTextWorld = new Matrix4x4();
        this.mBG = new Background();
        this.mPlatform = new Platform();
        this.mLander = new Lander(new Gravity(Difficulty.EASY));
        this.mTextWorld.translate(-40, 90, 0).scale(.25f);
        this.mBG.getWorld().translate(0, 0, -20).scale(14, 14, 0);
        this.mObstacles = Obstacle.getRandomObstacles(3, 5, 5);
        this.format.setMaximumFractionDigits(2);
        this.format.setMinimumFractionDigits(2);
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mBG);
        renderer.drawText(this.mText, this.mTextWorld);
        renderer.draw(this.mPlatform);
        renderer.draw(this.mLander);
        //        for (int i = 0; i < this.mObstacles.length; i++) {
        //            renderer.draw(this.mObstacles[i]);
        //        }
    }

    @Override
    public void prepareCamera(int width, int height) {
        Matrix4x4 projection = new Matrix4x4();
        float aspect = this.getGame().getAspect();
        if (aspect > 1) {
            projection.setOrthogonalProjection(-Static.CAM_DIMEN, Static.CAM_DIMEN, -Static.CAM_DIMEN * aspect, Static.CAM_DIMEN * aspect, Static.CAM_NEAR,
                    Static.CAM_FAR);
        }
        else {
            projection.setOrthogonalProjection(-Static.CAM_DIMEN * aspect, Static.CAM_DIMEN * aspect, -Static.CAM_DIMEN, Static.CAM_DIMEN, Static.CAM_NEAR,
                    Static.CAM_FAR);
        }
        this.getCamera().setProjection(projection);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        SpriteFont font = device.createSpriteFont(null, 32);
        this.mText = device.createTextBuffer(font, 32);
        this.mText.setText("TEXTBUFFER");
        for (int i = 0; i < this.mObstacles.length; i++) {
            this.mObstacles[i].prepare(context, device);
        }
        this.mBG.prepare(context, device);
        this.mPlatform.prepare(context, device);

        this.mLander.prepare(context, device);
    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.updatePosition(deltaSeconds);
        Vector3 v3 = this.getCamera().project(new Vector3(this.mLander.getPosition(), 0), 1);

        //        this.mText.setText("X: " + this.format.format(v3.getX()) + " Y: " + this.format.format(v3.getY()));
        this.mText.setText("Speed: " + this.format.format(this.mLander.getCurrentSpeed().getLength()) + "m/s");

        //        for (int i = 0; i < this.mObstacles.length; i++) {
        //            if (this.mLander.intersects(this.mObstacles[i])) {
        //                this.getGame().postToast("Crashed!");
        //                this.pause();
        //            }
        //        }

        if (this.mLander.intersects(this.mPlatform)) {
            Logger.log("landerspeed", this.mLander.getCurrentSpeed().getY());
            if (this.mLander.getCurrentSpeed().getLength() > 0.3f) {
                this.getGame().postToast("Crash!");
            }
            else {
                this.getGame().postToast("Landed!");
            }
            this.pause();
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {
        this.mLander.setAccelerating(action == InputAction.DOWN);

    }

    @Override
    public void onKeyboardKeyPressed(int event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAccelerometerEvent(float[] values) {
        //        Log.d(this.getClass().getName(), "Rotation!: x: " + values[0] + " y: " + values[1] + " z: " + values[2]);
        this.mLander.onAccelerometerEvent(values);
    }

}
