
package de.hdm.spe.lander.game;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.KeyEvent;
import android.view.View;

import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector3;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.states.LevelA;


public class LanderGame extends Game {

    private SpriteFont      fontTitle, fontMenu;
    private TextBuffer      textTitle;
    private Matrix4x4       matTitle;
    private TextBuffer[]    textMenu;
    private Matrix4x4[]     matMenu;
    private AABB[]          aabbMenu;
    private boolean         showMenu = true;

    private MediaPlayer     mediaPlayer;
    private SoundPool       soundPool;
    private int             clickSound;
    private final GameState mCurrentState;

    public LanderGame(View view) {
        super(view);
        this.mCurrentState = new LevelA();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void loadContent() {
        this.mCurrentState.prepare(this.context, this.graphicsDevice);
        //            stream = this.context.getAssets().open("tree.png");
        //            this.texTree = this.graphicsDevice.createTexture(stream);
        //            this.matTree.setTexture(this.texTree);
        //
        //            stream = this.context.getAssets().open("road.obj");
        //            this.meshRoad = Mesh.loadFromOBJ(stream);
        //
        //            stream = this.context.getAssets().open("road.png");
        //            this.texRoad = this.graphicsDevice.createTexture(stream);
        //            this.matRoad.setTexture(this.texRoad);

        //        this.fontTitle = this.graphicsDevice.createSpriteFont(null, 64);
        //        this.fontMenu = this.graphicsDevice.createSpriteFont(null, 20);
        //
        //        this.textTitle = this.graphicsDevice.createTextBuffer(this.fontTitle, 16);
        //        this.textMenu = new TextBuffer[] {
        //                this.graphicsDevice.createTextBuffer(this.fontMenu, 16),
        //                this.graphicsDevice.createTextBuffer(this.fontMenu, 16),
        //                this.graphicsDevice.createTextBuffer(this.fontMenu, 16),
        //                this.graphicsDevice.createTextBuffer(this.fontMenu, 16)
        //        };
        //
        //        this.textTitle.setText("DrivingSim");
        //        this.textMenu[0].setText("Start Game");
        //        this.textMenu[1].setText("Options");
        //        this.textMenu[2].setText("Credits");
        //        this.textMenu[3].setText("Quit");
        //
        //        this.matTitle = Matrix4x4.createTranslation(-120, 100, 0);
        //        this.matMenu = new Matrix4x4[] {
        //                Matrix4x4.createTranslation(0, -50, 0),
        //                Matrix4x4.createTranslation(0, -80, 0),
        //                Matrix4x4.createTranslation(0, -110, 0),
        //                Matrix4x4.createTranslation(0, -140, 0)
        //        };
        //
        //        this.aabbMenu = new AABB[] {
        //                new AABB(0, -50, 120, 16),
        //                new AABB(0, -80, 120, 16),
        //                new AABB(0, -110, 120, 16),
        //                new AABB(0, -140, 120, 16)
        //        };

        //        while (this.mediaPlayer == null) {
        ////            this.mediaPlayer = MediaPlayer.create(this.context, R.raw.song);
        //        }
        //        this.mediaPlayer.start();

        //        this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //        this.clickSound = this.soundPool.load(this.context, R.raw.click, 1);
    }

    @Override
    public void update(float deltaSeconds) {
        this.mCurrentState.update(deltaSeconds);
        InputEvent inputEvent = this.inputSystem.peekEvent();
        while (inputEvent != null && false) {

            switch (inputEvent.getDevice()) {
                case KEYBOARD:
                    switch (inputEvent.getAction()) {
                        case DOWN:
                            switch (inputEvent.getKeycode()) {
                                case KeyEvent.KEYCODE_MENU:
                                    this.showMenu = !this.showMenu;
                                    break;
                            }
                            break;
                    }
                    break;

                case TOUCHSCREEN:
                    switch (inputEvent.getAction()) {
                        case DOWN:
                            Vector3 screenTouchPosition = new Vector3(
                                    (inputEvent.getValues()[0] / (this.screenWidth / 2) - 1),
                                    -(inputEvent.getValues()[1] / (this.screenHeight / 2) - 1),
                                    0);

                            //                            Vector3 worldTouchPosition = this.hudCamera.unproject(screenTouchPosition, 1);

                            //                            Point touchPoint = new Point(
                            //                                    worldTouchPosition.getX(),
                            //                                    worldTouchPosition.getY());

                            //                            for (int i = 0; i < this.aabbMenu.length; ++i) {
                            //                                AABB aabb = this.aabbMenu[i];
                            //                                if (touchPoint.intersects(aabb)) {
                            //                                    this.textMenu[i].setText("");
                            //
                            //                                    if (this.soundPool != null)
                            //                                        this.soundPool.play(this.clickSound, 1, 1, 0, 0, 1);
                            //                                }
                            //                            }
                    }
                    break;
            }

            this.inputSystem.popEvent();
            inputEvent = this.inputSystem.peekEvent();
        }
    }

    @Override
    public void draw(float deltaSeconds) {
        this.graphicsDevice.clear(0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
        this.mCurrentState.draw(deltaSeconds, this.renderer);
        this.graphicsDevice.setCamera(this.mCurrentState.getCamera());
    }

    @Override
    public void resize(int width, int height) {
        float aspect = (float) width / (float) height;
        Matrix4x4 projection;

        projection = new Matrix4x4();
        projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 100.0f);
        this.mCurrentState.getCamera().setProjection(projection);

        //        projection = new Matrix4x4();
        //        projection.setPerspectiveProjection(-0.1f * aspect, 0.1f * aspect, -0.1f, 0.1f, 0.1f, 100.0f);
        //        this.sceneCamera.setProjection(projection);
        //
        //        this.matTitle.setIdentity();
        //        this.matTitle.translate(-width / 2, height / 2 - 64, 0);
    }

    @Override
    public void pause() {
        //if (mediaPlayer != null)
        //	mediaPlayer.pause();		
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub		
    }

}
