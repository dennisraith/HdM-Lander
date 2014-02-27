
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.Static;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.Background;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.models.Gravity;
import de.hdm.spe.lander.models.Gravity.Difficulty;
import de.hdm.spe.lander.models.Lander;
import de.hdm.spe.lander.models.MediaManager;

import java.io.IOException;


public class LevelA extends GameState {

    private final Lander     mLander;
    TextBuffer               mText;
    private final Matrix4x4  mTextWorld;
    private final Background mBG;

    public LevelA(Game game) {
        super(game);
        this.mTextWorld = new Matrix4x4();
        this.mBG = new Background();
        this.mLander = new Lander(new Gravity(Difficulty.EASY));
        this.mTextWorld.translate(0, 90, 0).scale(.25f);
        this.mBG.getWorld().translate(0, 0, -20).scale(14, 14, 0);
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mBG);
        renderer.drawText(this.mText, this.mTextWorld);
        renderer.draw(this.mLander);
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

        this.mLander.prepare(context, device);
        
        MediaManager.getInstance().loadTrack("deep-space.mp3");
    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.updatePosition();
        Vector3 v3 = this.getCamera().project(new Vector3(this.mLander.getPosition(), 0), 1);
        this.mText.setText("Y: " + v3.getY());
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

}
