package de.hdm.spe.lander.states;

import java.io.IOException;

import android.content.Context;
import android.util.Log;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.states.GameState.StateType;
import de.hdm.spe.lander.statics.Difficulty;

public class DifficultyOptions extends Menu {
	
	private final OptionManager optionManager;

	public DifficultyOptions(Game game) {
		super(game);
		this.optionManager = OptionManager.getInstance();
	}

	
	@Override
	public void prepare(Context context, GraphicsDevice device) throws IOException{
		
    	mBG.setBackground("moonLanding.jpg");
    	mBG.prepare(context, device);
    	this.mBG.getWorld().translate(0, 0, -1).scale(86, -75, 0);
		
		this.fontTitle = device.createSpriteFont(null, 96);
        this.textTitle = device.createTextBuffer(this.fontTitle, 16);
        this.textTitle.setText("Difficulty");
        
        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[]{
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16)
        };
        this.textEntries[0].setText("Easy");
        this.textEntries[1].setText("Medium");
        this.textEntries[2].setText("Hard");
        this.textEntries[3].setText("Zurück");
        
        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-220, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, 0),
                Matrix4x4.createTranslation(-150, 40, 0),
                Matrix4x4.createTranslation(-150, -80, 0),
                Matrix4x4.createTranslation(-150, -200, 0)
        };
        this.aabbEntries = new Square[] {
                new Square(-40, 180, 250, 80),
                new Square(-40, 60, 250, 80),
                new Square(-40, -60, 250, 80),
                new Square(-40, -180, 250, 80)
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
        
	}
	
//	@Override
//	public void draw(float deltaSeconds, Renderer renderer){
//		super.draw(deltaSeconds, renderer);
//                for (Square sq : this.aabbEntries) {
//                    renderer.draw(sq);
//                }
//	}
	
	
	@Override
    protected void onMenuItemClicked(int i) {
        Log.d("diff :",""+i);
        Difficulty diff = null;
        switch (i) {
            case 0:
            	diff= Difficulty.EASY;
                break;
            case 1:
            	diff= Difficulty.MEDIUM;
            	break;
            case 2:
            	diff= Difficulty.HARD;
                break;
            case 3:
                break;
        }
        if(diff!=null ){
        	OptionManager.getInstance().setDifficulty(diff);
        	getGame().postToast("Difficulty set to "+diff.name());
        }
        this.setGameState(StateType.OPTIONS);
    }
	
	@Override
    public StateType getStateType() {
        return StateType.DIFFICULTYOPTIONS;
    }
	
	
	
}
