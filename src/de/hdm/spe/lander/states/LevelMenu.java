package de.hdm.spe.lander.states;

import android.content.Context;
import android.util.Log;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
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
	
	private String clickedOption;

	@Override
	public void prepare(Context context, GraphicsDevice device){
		
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
        this.matTitle = Matrix4x4.createTranslation(-220, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, -1),
                Matrix4x4.createTranslation(-150, 40, -1),
                Matrix4x4.createTranslation(-150, -80, -1),
                Matrix4x4.createTranslation(-150, -200, -1),
                Matrix4x4.createTranslation(-150, -320, -1)
        };
        this.aabbEntries = new Square[] {
                new Square(0, 180, 250, 80),
                new Square(0, 60, 250, 80),
                new Square(0, -60, 250, 80),
                new Square(0, -180, 250, 80),
                new Square(0, -300, 250, 80)
        };
	}
	
	@Override
    protected void onMenuItemClicked(int i) {
        this.optionManager.changeOptions(i);
        this.clickedOption = this.optionManager.getOption(i);
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
