
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;

import java.io.IOException;


/**
 * displays and controls names and the space ship 
 * @author boris
 *
 */
public class CreditsLevel extends Level {

    protected SpriteFont   fontSprite;
    protected TextBuffer[] textBuffer;
    protected Matrix4x4[]  matEntries;

    private double         i = 0.0;

    /**
     * @param game
     */
    public CreditsLevel(Game game) {
        super(game);
        this.mLander.getWorld().translate(0, -90, 0);
        this.mLander.getFuel().setInfinite(true);
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Level#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        super.prepare(context, device);

        this.fontSprite = device.createSpriteFont(null, 70);
        this.textBuffer = new TextBuffer[] {
                device.createTextBuffer(this.fontSprite, 16),
                device.createTextBuffer(this.fontSprite, 16),
                device.createTextBuffer(this.fontSprite, 16)
        };
        this.textBuffer[0].setText("Dennis Raith");
        this.textBuffer[1].setText("Boris Boyarskiy");
        this.textBuffer[2].setText("Leslie Milde");

        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-50, 60, 0).scale(0.2f),
                Matrix4x4.createTranslation(-50, 30, 0).scale(0.2f),
                Matrix4x4.createTranslation(-50, 0, 0).scale(0.2f)
        };

    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Level#update(float)
     */
    @Override
    public void update(float deltaSeconds) {

        this.mLander.updatePosition(deltaSeconds);
        if (this.mLander.getPosition().getY() < -30) {
            this.mLander.setAccelerating(true);
        } else if (this.mLander.getPosition().getY() > 30) {
            this.mLander.setAccelerating(false);
        }

        Log.d("i :", " " + this.i);
        this.i += 0.06;
        if (this.i < 70) {

            this.matEntries[0].translate(0, -0.3f, 0);
            this.matEntries[1].translate(0, -0.3f, 0);
            this.matEntries[2].translate(0, -0.3f, 0);
        } else {

            this.matEntries[0].translate(0, 0.3f, 0);
            this.matEntries[1].translate(0, 0.3f, 0);
            this.matEntries[2].translate(0, 0.3f, 0);
            if (this.i > 140) {
                this.i = 0;
            }
        }

    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Level#draw(float, de.hdm.spe.lander.graphics.Renderer)
     */
    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        super.draw(deltaSeconds, renderer);

        for (int i = 0; i < this.matEntries.length; i++) {
            renderer.drawText(this.textBuffer[i], this.matEntries[i]);
        }
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Level#onAccelerometerEvent(float[])
     */
    @Override
    public void onAccelerometerEvent(float[] values) {
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.GameState#getStateType()
     */
    @Override
    public StateType getStateType() {
        return StateType.CREDITSLEVEL;
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Level#prepareObstacles()
     */
    @Override
    protected boolean prepareObstacles() {
        return false;
    }

}
