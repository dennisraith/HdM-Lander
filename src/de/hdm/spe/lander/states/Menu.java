
package de.hdm.spe.lander.states;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent;
import de.hdm.spe.lander.input.InputSystem;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.models.Square;

import java.io.IOException;


public class Menu extends GameState {

    public Menu(Game game) {
        super(game);
    }

    private Camera       hudCamera;

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

    public void initialize(Game game) {

    }

    public void loadContent(Game game) {

    }

    public void update(Game game, float deltaSeconds) {
        InputSystem inputSystem = game.getInputSystem();
        int screenWidth = game.getScreenWidth();
        int screenHeight = game.getScreenHeight();

        InputEvent inputEvent = inputSystem.peekEvent();
        while (inputEvent != null) {
            switch (inputEvent.getDevice()) {
                case TOUCHSCREEN:
                    switch (inputEvent.getAction()) {
                        case DOWN:
                            Vector3 screenTouchPosition = new Vector3(
                                    (inputEvent.getValues()[0] / (screenWidth / 2) - 1),
                                    -(inputEvent.getValues()[1] / (screenHeight / 2) - 1),
                                    0);

                            Vector3 worldTouchPosition = this.hudCamera.unproject(screenTouchPosition, 1);

                            Point touchPoint = new Point(
                                    worldTouchPosition.getX(),
                                    worldTouchPosition.getY());

                            for (int i = 0; i < this.aabbMenu.length; ++i) {
                                AABB aabb = this.aabbMenu[i];
                                Log.d("for drin", "bla argagasd");
                                if (touchPoint.intersects(aabb)) {
                                    if (this.soundPool != null)
                                        this.soundPool.play(this.clickSound, 1, 1, 0, 0, 1);

                                    this.onMenuItemClicked(game, i);
                                }
                            }
                    }
                    break;
            }

            inputSystem.popEvent();
            inputEvent = inputSystem.peekEvent();
        }
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) {

        Matrix4x4 projection = new Matrix4x4();
        Matrix4x4 view = new Matrix4x4();

        this.hudCamera = new Camera();
        this.hudCamera.setProjection(projection);

        this.hudCamera.setView(view);

        this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        Log.d(this.getClass().getName(), "TextBounds: " + this.textTitle.getMesh().getBounds().width() + " : " + this.textTitle.getMesh().getBounds().height());
        this.textTitle.setText("Moon Landing");

        this.fontMenu = device.createSpriteFont(null, 70);
        this.textMenu = new TextBuffer[] {
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16),
                device.createTextBuffer(this.fontMenu, 16)
        };
        this.textMenu[0].setText("Start Game");
        this.textMenu[1].setText("Options");
        this.textMenu[2].setText("Credits");
        this.textMenu[3].setText("Quit");

        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matMenu = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, -1),
                Matrix4x4.createTranslation(-150, 40, -1),
                Matrix4x4.createTranslation(-150, -80, -1),
                Matrix4x4.createTranslation(-150, -200, -1)
        };

        this.aabbMenu = new Square[] {
                new Square(20, 180, 380, 80),
                new Square(-30, 60, 280, 80),
                new Square(-45, -60, 250, 80),
                new Square(-85, -180, 160, 80)
        };

        for (Square sq : this.aabbMenu) {
            try {
                sq.prepare(context, device);
                sq.getWorld().translate(0, 0, -10);
                sq.getMaterial().setTexture(device.createTexture(context.getAssets().open("space.png")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /*
         * while (mediaPlayer == null) { mediaPlayer = MediaPlayer.create(context, R.raw.song); } mediaPlayer.start();
         */

        this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //		clickSound = soundPool.load(context, R.raw.click, 1);
    }

    @Override
    public void resize(int width, int height) {
        Matrix4x4 projection = new Matrix4x4();
        projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 100.0f);
        this.hudCamera.setProjection(projection);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void update(float deltaSeconds) {
        // TODO Auto-generated method stub

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

    @Override
    public Camera getCamera() {
        return this.hudCamera;
    }

    private void onMenuItemClicked(Game game, int i) {
        switch (i) {
            case 0:
                Log.d("touch: 1", "Start");
                break;
            case 1:
                Log.d("touch: 2", "Options");
                break;
            case 2:
                Log.d("touch: 3", "Credits");
                break;
            case 3:
                game.finish();
                break;
        }
    }
}
