
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.Logger;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.game.LanderGame;
import de.hdm.spe.lander.gameobjects.Asteroid;
import de.hdm.spe.lander.gameobjects.Background;
import de.hdm.spe.lander.gameobjects.Lander;
import de.hdm.spe.lander.gameobjects.Obstacles;
import de.hdm.spe.lander.gameobjects.Platform;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.input.InputEvent.InputAction;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.GameStatusBar;
import de.hdm.spe.lander.models.Highscore;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.LevelHelper;
import de.hdm.spe.lander.models.MediaManager;
import de.hdm.spe.lander.models.MediaManager.LanderSound;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Static;

import java.io.IOException;


public abstract class Level extends GameState {

    protected final Lander     mLander;

    protected final Background mBG;
    protected final Platform   mPlatform;
    private Obstacles          mObstacles;
    private boolean            activeObstacles;
    private final LevelHelper  mHelper;
    protected GameStatusBar    mStatusBar;

    private final Asteroid     mAst;

    public Level(Game game) {
        super(game);

        this.mBG = new Background();
        this.mPlatform = new Platform();
        this.mLander = new Lander(Difficulty.EASY);
        this.mHelper = new LevelHelper(this);
        this.mBG.getWorld().translate(0, 0, -20).scale(13.5f, -13f, 0);
        this.mStatusBar = new GameStatusBar(this);
        this.mAst = new Asteroid();

    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mBG);
        this.mHelper.draw(deltaSeconds, renderer);
        renderer.draw(this.mPlatform);
        this.mStatusBar.draw(deltaSeconds, renderer);
        this.mLander.draw(renderer);
        renderer.draw(this.mAst);
        if (this.activeObstacles) {
            this.mObstacles.draw(deltaSeconds, renderer);
        }
    }

    protected void setUseObstacles(boolean useObstacles) {
        this.activeObstacles = useObstacles;
    }

    protected void setObstacles(Obstacles obstacles) {
        this.mObstacles = obstacles;
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
        this.mAst.prepare(context, device);
        this.mHelper.prepare(context, device);
        this.mStatusBar.prepare(context, device);
        this.mBG.prepare(context, device);
        this.mPlatform.prepare(context, device);
        this.mLander.prepare(context, device);
        if (this.activeObstacles) {
            this.mObstacles.prepare(context, device);
        }
    }

    //        Vector3 v3 = this.getCamera().project(new Vector3(this.mLander.getPosition(), 0), 1);
    @Override
    public void update(float deltaSeconds) {

        if (this.mHelper.update(deltaSeconds)) {
            this.mLander.updatePosition(deltaSeconds);
            this.mStatusBar.update(deltaSeconds);
            if (this.activeObstacles) {
                this.mObstacles.update(deltaSeconds);
            }

        }
        this.checkGameState();
    }

    public void checkGameState() {
        if (this.activeObstacles) {
            if (this.mObstacles.collide(this.mLander)) {
                this.onLoose();
                return;
            }
        }
        if (this.mLander.intersects(this.mAst)) {
            this.getGame().postToast("Asteroid collision");
        }

        if (this.mLander.intersects(this.mPlatform)) {
            Logger.log("landerspeed", this.mLander.getCurrentSpeed().getY());
            if (this.mLander.getCurrentSpeed().getLength() > 0.3f) {
                this.onLoose();
            }
            else {
                this.onWin();
            }
        }
    }

    protected void onLoose() {
        this.pause();
        this.getGame().postToast("Crash!");
        MediaManager.getInstance().playSound(LanderSound.Explosion);
        this.setGameState(StateType.MENU);
    };

    protected void onWin() {
        this.pause();
        this.getGame().postToast("Landed!");
        float score = Highscore.calculateHighscore(this.mStatusBar.getElapsedTime(), this.mLander.getCurrentSpeed());
        if (HighscoreManager.getInstance().checkHighscore(score)) {
            Logger.log("HighscoreCheck", "Highscore! " + score);
            ((LanderGame) this.getGame()).onHighscoreDialogRequested(score);
        }
    };

    @Override
    public void pause() {
        this.mStatusBar.onPause();
        super.pause();
    }

    @Override
    public void resume() {
        this.mStatusBar.onResume();
        super.resume();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void onScreenTouched(Point point, InputAction action) {
        this.mLander.setAccelerating(action == InputAction.DOWN);

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

}
