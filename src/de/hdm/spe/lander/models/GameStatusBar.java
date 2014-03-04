
package de.hdm.spe.lander.models;

import android.content.Context;

import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.states.Level;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;


public class GameStatusBar {

    protected final Matrix4x4 mTextWorld;
    private final GameTimer   mTimer;
    private final Level       mLevel;
    TextBuffer                mText;

    public GameStatusBar(Level level) {
        this.mTimer = new GameTimer();
        this.mTextWorld = new Matrix4x4();
        this.mTextWorld.translate(-60, 94, 0).scale(.1f);
        this.mLevel = level;
        Static.numberFormat.setMaximumFractionDigits(1);
        Static.numberFormat.setMinimumFractionDigits(1);
    }

    public void prepare(Context context, GraphicsDevice device) throws IOException {
        SpriteFont font = device.createSpriteFont(null, 64);
        this.mText = device.createTextBuffer(font, 32);
    }

    public void draw(float deltaTime, Renderer renderer) {
        renderer.drawText(this.mText, this.mTextWorld);
    }

    public void updateText(String speed, String time) {
        this.mText.setText("Speed: " + speed + "m/s" + " Time: " + time + " sec");
    }

    public void update(float deltaTime) {
        this.mTimer.update(deltaTime);

        String speed = Static.numberFormat.format(this.mLevel.getLander().getCurrentSpeed().getLength());
        String time = Static.numberFormat.format(this.mTimer.getTime());
        this.updateText(speed, time);
    }

    public void onPause() {
        this.mTimer.pause();
    }

    public void onResume() {
        this.mTimer.resume();
    }

    public float getElapsedTime() {
        return this.mTimer.getTime();
    }

}
