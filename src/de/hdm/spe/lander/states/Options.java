
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.R;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.statics.Lang;

import java.io.IOException;


public class Options extends Menu {

    private final OptionManager optionManager;

    public Options(Game game) {
        super(game);
        this.optionManager = OptionManager.getInstance();
    }

    private String clickedOption;

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

        this.aabbEntries = new Square[] {
                new Square(-140, 180, 340, 80),
                new Square(-130, 60, 380, 80),
                new Square(10, -60, 650, 80),
                new Square(-90, -180, 450, 80),
                new Square(-90, -300, 430, 80),
                new Square(-190, -420, 250, 80)
        };
	  for (Square sq : this.aabbEntries) {
	      try {
	          sq.prepare(context, device);
	          sq.getWorld().translate(0, 0, 0);
	          sq.getMaterial().setTexture(device.createTexture(context.getAssets().open("space.png")));
	
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }
        this.setPrepared(true);
    }

    @Override
    public void update(float deltaSeconds) {
        this.textEntries[0].setText(this.optionManager.getOption(0));
        this.textEntries[1].setText(this.optionManager.getOption(1));
        this.textEntries[2].setText(this.optionManager.getOption(2));
        this.textEntries[3].setText(this.optionManager.getOption(3));
        this.textEntries[4].setText(this.optionManager.getOption(4));
        this.textEntries[5].setText(this.optionManager.getOption(5));
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
    	super.draw(deltaSeconds, renderer);
      for (Square sq : this.aabbEntries) {
          renderer.draw(sq);
      }
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
                this.textEntries[3].setText(this.clickedOption);
                break;
            case 5:
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
