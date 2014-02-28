
package de.hdm.spe.lander.states;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import de.hdm.spe.lander.R;
import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.game.LanderGame;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.MediaManager;

import java.io.IOException;


public class Menu extends GameState {

    private SpriteFont   fontTitle;
    private TextBuffer   textTitle;
    private Matrix4x4    matTitle;

    private SpriteFont   fontMenu;
    private TextBuffer[] textMenu;
    private Matrix4x4[]  matMenu;
    private Square[]     aabbMenu;


    private MediaPlayer  mediaPlayer;
    private SoundPool    soundPool;
    private int          clickSound;

    public Menu(Game game) {
        super(game);

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
    public void prepare(Context context, GraphicsDevice device) {


        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText("Moon Landing");

        this.fontMenu = device.createSpriteFont(null, 70);
        this.textMenu = new TextBuffer[] {
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16)
        };
        this.textMenu[0].setText("Start Game");
        this.textMenu[1].setText("High Score");
        this.textMenu[2].setText("Options");
        this.textMenu[3].setText("Credits");
        this.textMenu[4].setText("Quit");

        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matMenu = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, -1),
                Matrix4x4.createTranslation(-150, 40, -1),
                Matrix4x4.createTranslation(-150, -80, -1),
                Matrix4x4.createTranslation(-150, -200, -1),
                Matrix4x4.createTranslation(-150, -320, -1)

        };

        this.aabbMenu = new Square[] {
                new Square(25, 180, 380, 80),
                new Square(20, 60, 370, 80),
                new Square(-30, -60, 270, 80),
                new Square(-40, -180, 250, 80),
                new Square(-85, -300, 160, 80)
        };

        for (Square sq : this.aabbMenu) {
            try {
                sq.prepare(context, device);
                sq.getWorld().translate(0, 0, -2);
                sq.getMaterial().setTexture(device.createTexture(context.getAssets().open("space.png")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MediaManager.getInstance().loadTrack("space-menu.mp3");
        MediaManager.getInstance().loadSounds();

    }

    @Override
    public void update(float deltaSeconds) {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdown() {
        MediaManager.getInstance().reset();
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        for (Square sq : this.aabbMenu) {
            renderer.draw(sq);
        }

        renderer.drawText(this.textTitle, this.matTitle);
        for (int i = 0; i < this.matMenu.length; i++) {
            renderer.drawText(this.textMenu[i], this.matMenu[i]);
        }

    }

    private void onMenuItemClicked(int i) {

        switch (i) {
            case 0:
                this.setGameState(StateType.LEVEL1);
                break;
            case 1:
            	((LanderGame)getGame()).onHighscoreListRequested();
            	break;
            case 2:
            	this.setGameState(StateType.OPTIONS);
            	break;
            case 3:
                
                break;
            case 4:
            	this.getGame().finish();
            	break;
        }
    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {

        for (int i = 0; i < this.aabbMenu.length; ++i) {
            AABB aabb = this.aabbMenu[i];
            Log.d("for drin", "bla argagasd");
            if (point.intersects(aabb)) {
                if (action == InputAction.DOWN)
                	MediaManager.getInstance().playSound();

                this.onMenuItemClicked(i);
            }
        }
    }

    @Override
    public void onKeyboardKeyPressed(int event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAccelerometerEvent(float[] values) {
        // TODO Auto-generated method stub

    }

    @Override
    public StateType getStateType() {
        return StateType.MENU;
    }

}
