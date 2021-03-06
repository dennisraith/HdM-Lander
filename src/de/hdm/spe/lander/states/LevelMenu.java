
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Rectangle;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.statics.Lang;

import java.io.IOException;


/**
 * controls and displays the level selection
 * @author boris
 *
 */
public class LevelMenu extends Menu {

    /**
     * @param game
     */
    public LevelMenu(Game game) {
        super(game);
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#prepare(android.content.Context, de.hdm.spe.lander.graphics.GraphicsDevice)
     */
    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {

        this.prepareBackground(context, device);

        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText("Levels");

        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[] {
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16)
        };
        this.textEntries[0].setText(Lang.CURR_DIFF);
        this.textEntries[1].setText(OptionManager.getInstance().getDifficulty().getLocaleString());
        this.textEntries[2].setText("Level 1");
        this.textEntries[3].setText("Level 2");
        this.textEntries[4].setText("Level 3");
        this.textEntries[5].setText("Level 4");
        this.textEntries[6].setText(Lang.BACK);

        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-240, 310, 0),
                Matrix4x4.createTranslation(-150, 240, 0),
                //lv1
                Matrix4x4.createTranslation(-150, 120, 0),
                //lv2
                Matrix4x4.createTranslation(-150, 10, 0),
                //lv3
                Matrix4x4.createTranslation(-150, -100, 0),
                //lv4
                Matrix4x4.createTranslation(-150, -200, 0),
                //back
                Matrix4x4.createTranslation(-150, -300, 0)
        };
        this.aabbEntries = new Rectangle[] {
                new Rectangle(-10, 300, 300, 140),
                new Rectangle(-40, 140, 250, 80),
                new Rectangle(-40, 30, 250, 80),
                new Rectangle(-40, -80, 250, 80),
                new Rectangle(-40, -180, 250, 80),
                new Rectangle(-40, -280, 250, 80)
        };
        this.setPrepared(true);
    }


    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#update(float)
     */
    @Override
    public void update(float deltaSeconds) {
        this.textEntries[1].setText(OptionManager.getInstance().getDifficulty().getLocaleString());
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#onMenuItemClicked(int)
     */
    @Override
    protected void onMenuItemClicked(int i) {
        switch (i) {
            case 0:
                OptionManager.getInstance().toggleDifficulty();
                break;
            case 1:
                this.setGameState(StateType.LEVEL1);
                break;
            case 2:
                this.setGameState(StateType.LEVEL2);
                break;
            case 3:
                this.setGameState(StateType.LEVEL3);
                break;
            case 4:
                this.setGameState(StateType.LEVEL4);
                break;
            case 5:
                this.setGameState(StateType.MENU);
                break;
        }
    }

    /* (non-Javadoc)
     * @see de.hdm.spe.lander.states.Menu#getStateType()
     */
    @Override
    public StateType getStateType() {
        return StateType.LEVELMENU;
    }

}
