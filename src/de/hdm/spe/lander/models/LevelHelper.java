
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.states.Level;

import java.io.IOException;


/**
 * Helper class for showing a count in before the actual level starts
 * 
 * @author Dennis
 *
 */
public class LevelHelper {

    private static int   sCOUNT_IN   = 4;

    protected SpriteFont fontTitle;
    protected TextBuffer mText;
    protected Matrix4x4  mWorld;
    protected Level      mLevel;
    private boolean      ready       = false;
    private float        elapsedTime = 0;

    public LevelHelper(Level level) {
        this.mWorld = new Matrix4x4();
        this.mWorld.translate(-20, 0, 0).scale(.3f);
        this.mLevel = level;

    }

    public void prepare(Context context, GraphicsDevice device) throws IOException {
        SpriteFont font = device.createSpriteFont(null, 128);
        this.mText = device.createTextBuffer(font, 32);
    }

    public void draw(float deltaTime, Renderer renderer) {
        if (this.elapsedTime < LevelHelper.sCOUNT_IN) {
            renderer.drawText(this.mText, this.mWorld);
        }
    }

    public void onLoad() {
        this.ready = true;
    }

    public boolean update(float deltaTime) {

        if (!this.ready) {
            return false;
        }
        this.elapsedTime += deltaTime;
        if (this.elapsedTime < LevelHelper.sCOUNT_IN) {

            int time = Math.round(LevelHelper.sCOUNT_IN - this.elapsedTime);
            if (time > 0) {
                this.mText.setText(" " + time);
            }
            else {
                this.mText.setText("GO");
            }
            return false;
        }
        else {
            return true;
        }
    }

}
