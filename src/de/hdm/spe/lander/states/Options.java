
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.OptionManager;


public class Options extends GameState {

    private final OptionManager optionManager;

    public Options(Game game) {
        super(game);
        this.optionManager = new OptionManager(game.getContext());
    }

    private SpriteFont   fontTitleOpt;
    private TextBuffer   textTitleOpt;
    private Matrix4x4    matTitleOpt;

    private SpriteFont   fontOptions;
    private TextBuffer[] textOptions;
    private Matrix4x4[]  matOptions;
    private Square[]     aabbOptions;

    private String       clickedOption;

    @Override
    public void prepareCamera(float width, float height) {
        if (width == 0 || height == 0) {
            width = 1000;
            height = 1000;
        }
        Matrix4x4 projection = new Matrix4x4();
        projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 100.0f);
        this.getCamera().setProjection(projection);
    }

    @Override
    public void onKeyboardKeyPressed(int keyEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAccelerometerEvent(float[] values) {
        // TODO Auto-generated method stub

    }

    @Override
    public void prepare(Context context, GraphicsDevice device) {

        this.fontTitleOpt = device.createSpriteFont(null, 96);
        this.textTitleOpt = device.createTextBuffer(this.fontTitleOpt, 16);
        this.textTitleOpt.setText("Options");

        this.optionManager.loadOptions();

        this.fontOptions = device.createSpriteFont(null, 70);
        this.textOptions = new TextBuffer[] {
                device.createTextBuffer(this.fontOptions, 16),
                device.createTextBuffer(this.fontOptions, 16),
                device.createTextBuffer(this.fontOptions, 16),
                device.createTextBuffer(this.fontOptions, 16),
                device.createTextBuffer(this.fontOptions, 16)
        };
        this.textOptions[0].setText(this.optionManager.getOption(0));
        this.textOptions[1].setText(this.optionManager.getOption(1));
        this.textOptions[2].setText(this.optionManager.getOption(2));
        this.textOptions[3].setText(this.optionManager.getOption(3));
        this.textOptions[4].setText(this.optionManager.getOption(4));

        new Matrix4x4();
        this.matTitleOpt = Matrix4x4.createTranslation(-300, 400, 0);
        this.matOptions = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, -1),
                Matrix4x4.createTranslation(-150, 40, -1),
                Matrix4x4.createTranslation(-150, -80, -1),
                Matrix4x4.createTranslation(-150, -200, -1),
                Matrix4x4.createTranslation(-150, -320, -1)
        };

        this.aabbOptions = new Square[] {
                new Square(20, 180, 360, 80),
                new Square(15, 60, 350, 80),
                new Square(60, -60, 440, 80),
                new Square(30, -180, 390, 80),
                new Square(-40, -300, 250, 80),
        };

        //        MediaManager.getInstance().loadTrack("space-menu.mp3");
        MediaManager.getInstance().loadSounds();
    }

    @Override
    public void update(float deltaSeconds) {

    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {

        renderer.drawText(this.textTitleOpt, this.matTitleOpt);
        for (int i = 0; i < this.matOptions.length; i++) {
            renderer.drawText(this.textOptions[i], this.matOptions[i]);
        }

    }

    private void onMenuItemClicked(int i) {
        this.optionManager.changeOptions(i);
        this.clickedOption = this.optionManager.getOption(i);
        switch (i) {
            case 0:
                this.textOptions[0].setText(this.clickedOption);
                Log.d("case 0", "msg");
                break;
            case 1:
                HighscoreManager.getInstance().clearHighscore();
                this.getGame().postToast("Highscore cleared");
                break;
            case 2:

                break;
            case 3:
                this.textOptions[3].setText(this.clickedOption);
                break;
            case 4:
                this.optionManager.saveOptions();
                this.setGameState(StateType.MENU);
                break;
        }
    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {

        for (int i = 0; i < this.aabbOptions.length; ++i) {
            AABB aabb = this.aabbOptions[i];
            if (point.intersects(aabb)) {
                if (action == InputAction.DOWN) {
                    MediaManager.getInstance().playSound();
                    this.onMenuItemClicked(i);
                }
            }
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public StateType getStateType() {
        return StateType.OPTIONS;
    }

}
