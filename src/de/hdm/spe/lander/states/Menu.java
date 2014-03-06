
package de.hdm.spe.lander.states;

import java.io.IOException;

import android.content.Context;

import de.hdm.spe.lander.Logger;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.game.LanderGame;
import de.hdm.spe.lander.gameobjects.Background;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.MediaManager.LanderSound;
import de.hdm.spe.lander.statics.Lang;


public class Menu extends GameState {

    protected SpriteFont   fontTitle;
    protected TextBuffer   textTitle;
    protected Matrix4x4    matTitle;

    protected SpriteFont   fontEntries;
    protected TextBuffer[] textEntries;
    protected Matrix4x4[]  matEntries;
    protected Square[]     aabbEntries;
    
    protected final Background mBG;

    public Menu(Game game) {
        super(game);
        this.mBG = new Background();
    }

    @Override
    public void prepareCamera(float width, float height) {
        if (width == 0 || height == 0) {
            width = 1000;
            height = 1000;
        }
        Matrix4x4 projection = new Matrix4x4();
        projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 5.0f);
        this.getCamera().setProjection(projection);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
    	
    	mBG.setBackground("moonLanding.jpg");
    	mBG.prepare(context, device);
    	this.mBG.getWorld().translate(0, 0, -1).scale(86, -75, 0);

        long time = System.currentTimeMillis();

        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText(Lang.GAME_NAME);

        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[] {
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16),
                device.createTextBuffer(this.fontEntries, 16)
        };
        this.textEntries[0].setText(Lang.MENU_NEWGAME);
        this.textEntries[1].setText("Highscores");
        this.textEntries[2].setText(Lang.OPTIONS_NAME);
        this.textEntries[3].setText("Credits");
        this.textEntries[4].setText(Lang.MENU_QUIT);

        Logger.log("Loading Time Text Buffers", System.currentTimeMillis() - time);
        time = System.currentTimeMillis();
        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, 0),
                Matrix4x4.createTranslation(-150, 40, 0),
                Matrix4x4.createTranslation(-150, -80, 0),
                Matrix4x4.createTranslation(-150, -200, 0),
                Matrix4x4.createTranslation(-150, -320, 0)

        };

        this.aabbEntries = new Square[] {
                new Square(25, 180, 380, 80),
                new Square(20, 60, 370, 80),
                new Square(-30, -60, 270, 80),
                new Square(-40, -180, 250, 80),
                new Square(-85, -300, 160, 80)
        };
        Logger.log("Loading Matrices", System.currentTimeMillis() - time);
        time = System.currentTimeMillis();
        //        for (Square sq : this.aabbMenu) {
        //            try {
        //                sq.prepare(context, device);
        //                sq.getWorld().translate(0, 0, -2);
        //                sq.getMaterial().setTexture(device.createTexture(context.getAssets().open("space.png")));
        //
        //            } catch (IOException e) {
        //                e.printStackTrace();
        //            }
        //        }
        Logger.log("Loading Time Preparation squares", System.currentTimeMillis() - time);
        time = System.currentTimeMillis();
        MediaManager.getInstance().loadTrack("space-menu.mp3");
        Logger.log("Loading Time Sounds", System.currentTimeMillis() - time);
    }

    @Override
    public void update(float deltaSeconds) {

    }


    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        //        for (Square sq : this.aabbMenu) {
        //            renderer.draw(sq);
        //        }
    	renderer.draw(this.mBG);
        renderer.drawText(this.textTitle, this.matTitle);
        for (int i = 0; i < this.matEntries.length; i++) {
            renderer.drawText(this.textEntries[i], this.matEntries[i]);
        }

    }

    protected void onMenuItemClicked(int i) {

        switch (i) {
            case 0:
                this.setGameState(StateType.LEVELMENU);
                break;
            case 1:
                ((LanderGame) this.getGame()).onHighscoreListRequested();
                break;
            case 2:
                this.setGameState(StateType.OPTIONS);
                break;
            case 3:
                this.setGameState(StateType.CREDITSLEVEL);
                break;
            case 4:
                this.getGame().finish();
                break;
        }
    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {

        for (int i = 0; i < this.aabbEntries.length; ++i) {
            if (point.intersects(this.aabbEntries[i])) {
                if (action == InputAction.DOWN) {
                    MediaManager.getInstance().playSound(LanderSound.MenuClick);
                    this.onMenuItemClicked(i);
                }
            }
        }
    }
    

    @Override
    public void onKeyboardKeyPressed(int event) {

    }

    @Override
    public void onAccelerometerEvent(float[] values) {

    }

    @Override
    public StateType getStateType() {
        return StateType.MENU;
    }

}
