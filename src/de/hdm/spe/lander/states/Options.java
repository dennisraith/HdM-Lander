
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.R;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Rectangle;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.statics.Lang;

import java.io.IOException;


/**
 * displays and controls the options
 * @author boris
 *
 */
public class Options extends Menu {
    private final OptionManager optionManager;

    /**
     * @param game
     */
    public Options(Game game) {
        super(game);
        this.optionManager = OptionManager.getInstance();
    }

    /**
     * 
     */
    private String clickedOption;

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {

        this.prepareBackground(context, device);

        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText(Lang.OPTIONS_NAME);

        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[] {
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32)
        };

        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-300, 160, 0),
                Matrix4x4.createTranslation(-300, 40, 0),
                Matrix4x4.createTranslation(-300, -80, 0),
                Matrix4x4.createTranslation(-300, -200, 0),
                Matrix4x4.createTranslation(-300, -320, 0),
                Matrix4x4.createTranslation(-300, -440, 0)
        };

        this.aabbEntries = new Rectangle[] {
                new Rectangle(-140, 180, 340, 80),
                new Rectangle(-90, 60, 460, 80),
                new Rectangle(10, -60, 650, 80),
                new Rectangle(-90, -180, 450, 80),
                new Rectangle(-90, -300, 430, 80),
                new Rectangle(-190, -420, 250, 80)
        };
        this.setPrepared(true);
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#update(float)
     */
    @Override
    public void update(float deltaSeconds) {
        this.textEntries[0].setText(this.optionManager.getOption(0));
        this.textEntries[1].setText(this.optionManager.getOption(1));
        this.textEntries[2].setText(this.optionManager.getOption(2));
        this.textEntries[3].setText(this.optionManager.getOption(3));
        this.textEntries[4].setText(this.optionManager.getOption(4));
        this.textEntries[5].setText(this.optionManager.getOption(5));
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#onMenuItemClicked(int)
     */
    @Override
    protected void onMenuItemClicked(int i) {
        this.optionManager.changeOptions(i);
        this.clickedOption = this.optionManager.getOption(i);
        switch (i) {
            case 0:
                this.textEntries[0].setText(this.clickedOption);
                break;
            case 1:
                this.textEntries[1].setText(this.clickedOption);
                break;
            case 2:
                HighscoreManager.getInstance().clearHighscore();
                this.getGame().postToast(R.string.highscore_reset);
                break;
            case 3:
                this.setGameState(StateType.DIFFICULTYOPTIONS);
                break;
            case 4:
                this.textEntries[4].setText(this.clickedOption);
                break;
            case 5:
                this.optionManager.saveOptions();
                this.setGameState(StateType.MENU);
                break;
        }
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#getStateType()
     */
    @Override
    public StateType getStateType() {
        return StateType.OPTIONS;
    }

}
