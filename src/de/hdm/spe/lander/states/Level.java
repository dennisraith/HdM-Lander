
package de.hdm.spe.lander.states;

import android.content.Context;

import de.hdm.spe.lander.Logger;
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
import de.hdm.spe.lander.models.MediaManager.LanderSound;
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
        this.mBackground.getWorld().translate(0, 0, -20).scale(13.5f, -13f, 0);

        this.mLander = new Lander(OptionManager.getInstance().getDifficulty());
        this.mHelper = new LevelHelper(this);

        this.mStatusBar = new GameStatusBar(this);

    }

    protected void prepareBackground(Context context, GraphicsDevice device) throws IOException {
        this.mBackground.prepare(context, device);
        //        this.mBackground.autoScale(this.getGame().getScreenWidth(), this.getGame().getScreenHeight(), 13, -13).translate(0, 0, -18);
        //        this.mBackground.getWorld().scale(13.5f, -13f, 0);
    }

    @Override
    public void draw(float deltaSeconds, Renderer renderer) {
        renderer.draw(this.mBackground);
        this.mHelper.draw(deltaSeconds, renderer);
        renderer.draw(this.mPlatform);
        this.mStatusBar.draw(deltaSeconds, renderer);
        this.mLander.draw(renderer);
        if (this.mObstacles != null) {
            this.mObstacles.draw(deltaSeconds, renderer);
        }
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

    //        Vector3 v3 = this.getCamera().project(new Vector3(this.mLander.getPosition(), 0), 1);
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
            Logger.log("landerspeed", this.mLander.getCurrentSpeed().getY());
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
        MediaManager.getInstance().playSound(LanderSound.Explosion);
        this.setGameState(StateType.MENU);
    };

    protected void onWin() {
        this.getGame().postToast("Landed!");
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
    public void onPause() {
        this.mStatusBar.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        this.mStatusBar.onResume();
        MediaManager.getInstance().loadTrack("space-level.mp3");
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
