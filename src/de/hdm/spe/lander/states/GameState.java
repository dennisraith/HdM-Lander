
package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Background;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.models.InputEventManager.InputReceiver;
import de.hdm.spe.lander.models.MediaManager;

import java.io.IOException;


public abstract class GameState implements InputReceiver {

    public enum StateType {
    	CREDITSLEVEL,
        LEVEL1,
        LEVEL2,
        LEVEL3,
        LEVEL4,
        MENU,
        OPTIONS,
        LEVELMENU,
        DIFFICULTYOPTIONS
    }

    private final Game   mGame;
    protected Camera     mCamera     = new Camera();
    private boolean      mIsPrepared = false;
    protected Background mBackground;

    public GameState(Game game) {
        this.mGame = game;
    }

    public Game getGame() {
        return this.mGame;
    }

    public abstract void prepare(Context context, GraphicsDevice device) throws IOException;

    public abstract void prepareCamera(float width, float height);

    public abstract void update(float deltaSeconds);

    public abstract void draw(float deltaSeconds, Renderer renderer);

    public final void shutdown(StateType type){
    	if (type == null) {
			onPause();
			return;
		}
    	if(this.getStateType().ordinal()>StateType.LEVEL4.ordinal() && type.ordinal()<=StateType.LEVEL4.ordinal()){
    		onPause();
    	}
    	else if(this.getStateType().ordinal()<=StateType.LEVEL4.ordinal() && type.ordinal()>StateType.LEVEL4.ordinal()){
    		onPause();
    	}
    }
    
    public void setPrepared(boolean initialized) {
        this.mIsPrepared = initialized;
    }

    public boolean isPrepared() {
        return this.mIsPrepared;
    }



    public void onPause() {
    	MediaManager.getInstance().reset();
    	
    };

    public abstract void onResume();

    public abstract StateType getStateType();

    public Camera getCamera() {
        return this.mCamera;
    }

    public void setGameState(StateType state) {
        this.mGame.setGameState(state);
    }

    @Override
    public void onBackPressed() {
        if (this.getStateType() != StateType.MENU) {
            this.getGame().setGameState(StateType.MENU);
        }
        else {
            this.mGame.finish();
        }
    }

}
