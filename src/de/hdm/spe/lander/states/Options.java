
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.R;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.statics.Lang;


public class Options extends Menu {

    private final OptionManager optionManager;

    public Options(Game game) {
        super(game);
        this.optionManager = OptionManager.getInstance();
    }

    private String clickedOption;

    @Override
    public void prepare(Context context, GraphicsDevice device) {

        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText(Lang.OPTIONS_NAME);

        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[] {
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32),
                device.createTextBuffer(this.fontEntries, 32)
        };

        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-220, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-220, 160, -1),
                Matrix4x4.createTranslation(-220, 40, -1),
                Matrix4x4.createTranslation(-220, -80, -1),
                Matrix4x4.createTranslation(-220, -200, -1),
                Matrix4x4.createTranslation(-220, -320, -1)
        };

        this.aabbEntries = new Square[] {
                new Square(-50, 180, 360, 80),
                new Square(30, 60, 520, 80),
                new Square(-10, -60, 440, 80),
                new Square(-35, -180, 390, 80),
                new Square(-105, -300, 250, 80),
        };

    }

    @Override
    public void update(float deltaSeconds) {
        this.textEntries[0].setText(this.optionManager.getOption(0));
        this.textEntries[1].setText(this.optionManager.getOption(1));
        this.textEntries[2].setText(this.optionManager.getOption(2));
        this.textEntries[3].setText(this.optionManager.getOption(3));
        this.textEntries[4].setText(this.optionManager.getOption(4));
    }

    @Override
    protected void onMenuItemClicked(int i) {
        this.optionManager.changeOptions(i);
        this.clickedOption = this.optionManager.getOption(i);
        switch (i) {
            case 0:
                this.textEntries[0].setText(this.clickedOption);
                break;
            case 1:
                HighscoreManager.getInstance().clearHighscore();
                this.getGame().postToast(R.string.highscore_reset);
                break;
            case 2:
                this.setGameState(StateType.DIFFICULTYOPTIONS);
                break;
            case 3:
                this.textEntries[3].setText(this.clickedOption);
                break;
            case 4:
                this.optionManager.saveOptions();
                this.setGameState(StateType.MENU);
                break;
        }
    }

    @Override
    public StateType getStateType() {
        return StateType.OPTIONS;
    }

}
