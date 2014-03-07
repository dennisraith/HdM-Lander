
package de.hdm.spe.lander.states;

import android.content.Context;

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
import de.hdm.spe.lander.models.MediaManager.SoundEffect;
import de.hdm.spe.lander.models.MediaManager.Track;
import de.hdm.spe.lander.statics.Lang;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;


public class Menu extends GameState {

    private static final float sBGscaleX = 86;
    private static final float sBGscaleY = -75;

    protected SpriteFont       fontTitle;
    protected TextBuffer       textTitle;
    protected Matrix4x4        matTitle;

    protected SpriteFont       fontEntries;
    protected TextBuffer[]     textEntries;
    protected Matrix4x4[]      matEntries;
    protected Square[]         aabbEntries;

    public Menu(Game game) {
        super(game);
        this.mBackground = new Background(Static.sMenuBgImage);
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

    protected void prepareBackground(Context context, GraphicsDevice device) throws IOException {
        this.mBackground.prepare(context, device);
        this.mBackground.scaleForMenu(this.getGame().getScreenWidth(), this.getGame().getScreenHeight(), Menu.sBGscaleX, Menu.sBGscaleY);
    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        this.prepareBackground(context, device);

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

        this.matTitle = Matrix4x4.createTranslation(-300, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-200, 160, 0),
                Matrix4x4.createTranslation(-200, 40, 0),
                Matrix4x4.createTranslation(-200, -80, 0),
                Matrix4x4.createTranslation(-200, -200, 0),
                Matrix4x4.createTranslation(-200, -320, 0)

        };

        this.aabbEntries = new Square[] {
                new Square(-15, 180, 380, 80),
                new Square(-20, 60, 380, 80),
                new Square(-80, -60, 270, 80),
                new Square(-85, -180, 240, 80),
                new Square(-60, -300, 290, 80)
        };
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
        this.setPrepared(true);
    }

    @Override
    public void update(float deltaSeconds) {

    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
//                for (Square sq : this.aabbEntries) {
//                    renderer.draw(sq);
//                }
        renderer.draw(this.mBackground);
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
                    MediaManager.getInstance().playSound(SoundEffect.MenuClick);
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

    @Override
    public void onLoad() {
        MediaManager.getInstance().startTrack(Track.Menu);
    }

}
