
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.gameobjects.Lander;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.states.Level;
import de.hdm.spe.lander.statics.Lang;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;


/**
 * Status bar within a Level, showing Fuel, Time and Speed
 * @author Dennis
 *
 */
public class GameStatusBar {

    protected final Matrix4x4 mTextWorld;
    private final GameTimer   mTimer;
    private final Level       mLevel;
    TextBuffer                mText;
    private final Lander      mLander;

    public GameStatusBar(Level level) {
        this.mTimer = new GameTimer();
        this.mTextWorld = new Matrix4x4();
        this.mTextWorld.translate(-55, 94, 0).scale(.1f);
        this.mLevel = level;
        Static.numberFormat.setMaximumFractionDigits(1);
        Static.numberFormat.setMinimumFractionDigits(1);
        this.mLander = this.mLevel.getLander();
    }

    /**
     * @param context
     * @param device
     * @throws IOException
     */
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        SpriteFont font = device.createSpriteFont(null, 64);
        this.mText = device.createTextBuffer(font, 48);
    }

    /**
     * @param deltaTime
     * @param renderer
     */
    public void draw(float deltaTime, Renderer renderer) {
        renderer.drawText(this.mText, this.mTextWorld);
    }

    /**
     * @param speed
     * @param time
     * @param fuel
     */
    public void updateText(String speed, String time, String fuel) {
        this.mText.setText(Lang.GAME_SPEED + "" + speed + "m/s " + Lang.GAME_TIME + " " + time + "s " + Lang.GAME_FUEL + "" + fuel + " %");
    }

    /**
     * @param deltaTime
     */
    public void update(float deltaTime) {
        this.mTimer.update(deltaTime);

        String speed = Static.numberFormat.format(this.mLander.getCurrentSpeed().getLength());
        String time = Static.numberFormat.format(this.mTimer.getTime());
        String fuel = Static.numberFormat.format((this.mLander.getFuel().getPercentage()));
        this.updateText(speed, time, fuel);
    }

    /**
     * 
     */
    public void onPause() {
        this.mTimer.pause();
    }

    /**
     * 
     */
    public void onResume() {
        this.mTimer.resume();
    }

    /**
     * @return
     */
    public float getElapsedTime() {
        return this.mTimer.getTime();
    }

}
