
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.game.LanderGame;
import de.hdm.spe.lander.gameobjects.Background;
import de.hdm.spe.lander.gameobjects.Lander;
import de.hdm.spe.lander.gameobjects.Obstacles;
import de.hdm.spe.lander.gameobjects.Platform;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.models.GameStatusBar;
import de.hdm.spe.lander.models.Highscore;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.LevelHelper;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.MediaManager.SoundEffect;
import de.hdm.spe.lander.models.MediaManager.Track;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.statics.Lang;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;


public abstract class Level extends GameState {

    protected final Lander    mLander;

    protected final Platform  mPlatform;
    protected Obstacles       mObstacles;
    private final LevelHelper mHelper;
    protected GameStatusBar   mStatusBar;

    public Level(Game game) {
        super(game);
        this.mPlatform = new Platform();
        this.mBackground = new Background();

        this.mLander = new Lander(OptionManager.getInstance().getDifficulty());
        this.mHelper = new LevelHelper(this);

        this.mStatusBar = new GameStatusBar(this);
        this.mStatusBar.onPause();

    }

    protected void prepareBackground(Context context, GraphicsDevice device) throws IOException {
        this.mBackground.prepare(context, device);
        float width = this.getGame().getScreenWidth();

        float scaleX = 13.5f;

        float x = (width / 1080) + .5f;
        scaleX = scaleX + x;

        this.mBackground.getWorld().translate(0, 0, -20).scale(scaleX, -13f, 0);

    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        this.mHelper.onLoad();
        renderer.draw(this.mBackground);
        renderer.draw(this.mPlatform);
        this.mLander.draw(renderer);
        if (this.mObstacles != null) {
            this.mObstacles.draw(deltaSeconds, renderer);
        }
        this.mStatusBar.draw(deltaSeconds, renderer);
        this.mHelper.draw(deltaSeconds, renderer);
    }

    @Override
    public void prepareCamera(float width, float height) {
        Matrix4x4 projection = new Matrix4x4();
        float aspect = this.getGame().getAspect();
        if (aspect > 1) {
            projection.setOrthogonalProjection(-Static.CAM_DIMEN, Static.CAM_DIMEN, -Static.CAM_DIMEN * aspect, Static.CAM_DIMEN * aspect, Static.CAM_NEAR,
                    Static.CAM_FAR);
        }
        else {
            projection.setOrthogonalProjection(-Static.CAM_DIMEN * aspect, Static.CAM_DIMEN * aspect, -Static.CAM_DIMEN, Static.CAM_DIMEN, Static.CAM_NEAR,
                    Static.CAM_FAR);
        }
        this.getCamera().setProjection(projection);

    }

    @Override
    public void prepare(Context context, GraphicsDevice device) throws IOException {
        this.prepareBackground(context, device);
        this.mHelper.prepare(context, device);
        this.mStatusBar.prepare(context, device);
        this.mPlatform.prepare(context, device);
        this.mLander.prepare(context, device);
        if (this.prepareObstacles()) {
            this.mObstacles.prepare(context, device);
        }
        this.setPrepared(true);
    }

    @Override
    public void update(float deltaSeconds) {
        if (this.mHelper.update(deltaSeconds)) {
            this.mLander.updatePosition(deltaSeconds);
            this.mStatusBar.update(deltaSeconds);
            if (this.mObstacles != null) {
                this.mObstacles.update(deltaSeconds);
            }

        }
        this.checkGameState();
    }

    public void checkGameState() {
        if (this.mObstacles != null && this.getStateType() != StateType.CREDITSLEVEL) {
            if (this.mObstacles.collide(this.mLander)) {
                this.onLoose(true);
                return;
            }
        }

        if (this.mLander.intersects(this.mPlatform)) {
            if (this.mLander.getCurrentSpeed().getLength() > 0.3f) {
                this.onLoose(true);
            }
            else {
                this.onWin();
            }
        }
        if (this.checkOutOfBounds()) {
            this.onLoose(false);
        }
    }

    protected void onLoose(boolean crash) {
        String reason = crash ? Lang.GAME_CRASH : Lang.GAME_OOBOUNDS;
        this.getGame().postToast(reason);
        MediaManager.getInstance().playSound(SoundEffect.Explosion);
        this.setGameState(StateType.MENU);
    };

    protected void onWin() {
        this.getGame().postToast(Lang.GAME_LANDING);
        float score = Highscore.calculateHighscore(this.mStatusBar.getElapsedTime(), this.mLander.getCurrentSpeed(), this.mLander.getFuel());
        if (HighscoreManager.getInstance().checkHighscore(score)) {
            ((LanderGame) this.getGame()).onHighscoreDialogRequested(score);
        }
    };

    private boolean checkOutOfBounds() {
        Vector2 position = this.mLander.getPosition();
        if (position.getX() > 100 || position.getX() < -100) {
            return true;
        }
        if (position.getY() > 100 || position.getY() < -100) {
            return true;
        }
        return false;
    }

    @Override
    public void onLoad() {
        MediaManager.getInstance().startTrack(Track.Level);

    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {
        if (action == InputAction.UP) {
            this.mLander.setAccelerating(false);
        }
        else {
            this.mLander.setAccelerating(true);
        }

    }

    @Override
    public void onKeyboardKeyPressed(int event) {

    }

    @Override
    public void onAccelerometerEvent(float[] values) {
        this.mLander.onAccelerometerEvent(values);
    }

    public Lander getLander() {
        return this.mLander;
    }

    protected abstract boolean prepareObstacles();

}
