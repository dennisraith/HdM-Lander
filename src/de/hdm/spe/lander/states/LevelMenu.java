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
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.states.GameState.StateType;

public class LevelMenu extends Menu{

	private final OptionManager optionManager;
	
	public LevelMenu(Game game) {
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
        this.textTitle.setText("Levels");
        
        this.fontEntries = device.createSpriteFont(null, 70);
        this.textEntries = new TextBuffer[]{
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16),
        		device.createTextBuffer(this.fontEntries, 16)
        };
        this.textEntries[0].setText("Level 1");
        this.textEntries[1].setText("Level 2");
        this.textEntries[2].setText("Level 3");
        this.textEntries[3].setText("Level 4");
        this.textEntries[4].setText("Zurück");
        
        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-180, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, 0),
                Matrix4x4.createTranslation(-150, 40, 0),
                Matrix4x4.createTranslation(-150, -80, 0),
                Matrix4x4.createTranslation(-150, -200, 0),
                Matrix4x4.createTranslation(-150, -320, 0)
        };
        this.aabbEntries = new Square[] {
                new Square(-40, 180, 250, 80),
                new Square(-40, 60, 250, 80),
                new Square(-40, -60, 250, 80),
                new Square(-40, -180, 250, 80),
                new Square(-40, -300, 250, 80)
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
        switch (i) {
            case 0:
                this.setGameState(StateType.LEVEL1);
                break;
            case 1:
            	this.setGameState(StateType.LEVEL2);
            	break;
            case 2:
            	this.setGameState(StateType.LEVEL3);
                break;
            case 3:
            	this.setGameState(StateType.LEVEL4);
                break;
            case 4:
                this.setGameState(StateType.MENU);
                break;
        }
    }
	
	@Override
    public StateType getStateType() {
        return StateType.LEVELMENU;
    }
	
	
}
