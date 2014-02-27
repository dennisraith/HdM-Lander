
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
import de.hdm.spe.lander.models.Background;
import de.hdm.spe.lander.models.Lander;
import de.hdm.spe.lander.models.Obstacles;
import de.hdm.spe.lander.models.Platform;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;


public abstract class Level extends GameState {

    protected final Lander     mLander;
    TextBuffer                 mText;
    protected final Matrix4x4  mTextWorld;
    protected final Background mBG;
    protected final Platform   mPlatform;
    private Obstacles          mObstacles;
    private boolean            activeObstacles;

    public Level(Game game) {
        super(game);
        this.mTextWorld = new Matrix4x4();
        this.mBG = new Background();
        this.mPlatform = new Platform();
        this.mLander = new Lander(Difficulty.EASY);
        this.mTextWorld.translate(-40, 90, 0).scale(.25f);
        this.mBG.getWorld().translate(0, 0, -20).scale(14, 14, 0);
        Static.numberFormat.setMaximumFractionDigits(2);
        Static.numberFormat.setMinimumFractionDigits(2);
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mBG);
        renderer.drawText(this.mText, this.mTextWorld);
        renderer.draw(this.mPlatform);
        renderer.draw(this.mLander);

        if (this.activeObstacles) {
            this.mObstacles.draw(deltaSeconds, renderer);
        }
    }

    protected void setUseObstacles(boolean useObstacles) {
        this.activeObstacles = useObstacles;
    }

    protected void setObstacles(Obstacles obstacles) {
        this.mObstacles = obstacles;
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
        this.mBG.prepare(context, device);
        this.mPlatform.prepare(context, device);
        this.mLander.prepare(context, device);
        if (this.activeObstacles) {
            this.mObstacles.prepare(context, device);
        }
    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.updatePosition(deltaSeconds);
        //        Vector3 v3 = this.getCamera().project(new Vector3(this.mLander.getPosition(), 0), 1);

        if (this.activeObstacles) {
            this.mObstacles.update(deltaSeconds);
        }

        this.mText.setText("Speed: " + Static.numberFormat.format(this.mLander.getCurrentSpeed().getLength()) + "m/s");
        this.checkGameState();
    }

    public void checkGameState() {
        if (this.activeObstacles) {
            if (this.mObstacles.collide(this.mLander)) {
                this.onLoose();
                return;
            }
        }

        if (this.mLander.intersects(this.mPlatform)) {
            Logger.log("landerspeed", this.mLander.getCurrentSpeed().getY());
            if (this.mLander.getCurrentSpeed().getLength() > 0.3f) {
                this.onLoose();
            }
            else {
                this.onWin();
            }
        }
    }

    public void checkLoosingConditions() {
    }

    protected void onLoose() {
        this.pause();
        this.getGame().postToast("Crash!");
    };

    protected void onWin() {
        this.pause();
        this.getGame().postToast("Landed!");
    };

    @Override
    public void shutdown() {

    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {
        this.mLander.setAccelerating(action == InputAction.DOWN);

    }

    @Override
    public void onKeyboardKeyPressed(int event) {

    }

    @Override
    public void onAccelerometerEvent(float[] values) {
        this.mLander.onAccelerometerEvent(values);
    }

}
