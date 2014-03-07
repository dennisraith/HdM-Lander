
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Lang;

import java.io.IOException;


public class DifficultyOptions extends Menu {

    public DifficultyOptions(Game game) {
        super(game);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {

        this.prepareBackground(context, device);

        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText(Lang.OPTIONS_DIFFICULTY);

        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[] {
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16)
        };
        this.textEntries[0].setText(Lang.DIFF_EASY);
        this.textEntries[1].setText(Lang.DIFF_MEDIUM);
        this.textEntries[2].setText(Lang.DIFF_HARD);
        this.textEntries[3].setText(Lang.BACK);

        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, 0),
                Matrix4x4.createTranslation(-150, 40, 0),
                Matrix4x4.createTranslation(-150, -80, 0),
                Matrix4x4.createTranslation(-150, -200, 0)
        };
        this.aabbEntries = new Square[] {
                new Square(-40, 180, 250, 80),
                new Square(-40, 60, 250, 80),
                new Square(-40, -60, 250, 80),
                new Square(-40, -180, 250, 80)
        };
        this.setPrepared(true);

        //                for (Square sq : this.aabbEntries) {
        //                    try {
        //                        sq.prepare(context, device);
        //                        sq.getWorld().translate(0, 0, -2);
        //                        sq.getMaterial().setTexture(device.createTexture(context.getAssets().open("space.png")));
        //        
        //                    } catch (IOException e) {
        //                        e.printStackTrace();
        //                    }
        //                }

    }

    //	@Override
    //	public void draw(float deltaSeconds, Renderer renderer){
    //		super.draw(deltaSeconds, renderer);
    //                for (Square sq : this.aabbEntries) {
    //                    renderer.draw(sq);
    //                }
    //	}

    @Override
    protected void onMenuItemClicked(int i) {
        Log.d("diff :", "" + i);
        Difficulty diff = null;
        String name = null;
        switch (i) {
            case 0:
                diff = Difficulty.EASY;
                name = Lang.DIFF_EASY;
                break;
            case 1:
                diff = Difficulty.MEDIUM;
                name = Lang.DIFF_MEDIUM;
                break;
            case 2:
                diff = Difficulty.HARD;
                name = Lang.DIFF_HARD;
                break;
            case 3:
                break;
        }
        if (diff != null) {
            OptionManager.getInstance().setDifficulty(diff);
            this.getGame().postToast(Lang.DIFF_SET + " " + name);
        }
        this.setGameState(StateType.OPTIONS);
    }

    @Override
    public StateType getStateType() {
        return StateType.DIFFICULTYOPTIONS;
    }

}
