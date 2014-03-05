package de.hdm.spe.lander.states;

import java.io.IOException;

import android.content.Context;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Square;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.models.OptionManager;
import de.hdm.spe.lander.states.GameState.StateType;

public class DifficultyOptions extends Menu {
	
	private final OptionManager optionManager;

	public DifficultyOptions(Game game) {
		super(game);
		this.optionManager = OptionManager.getInstance();
	}

	private String clickedOption;
	
	@Override
	public void prepare(Context context, GraphicsDevice device){
		
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
        this.textEntries[1].setText("Normal");
        this.textEntries[2].setText("Hard");
        this.textEntries[3].setText("Zur�ck");
        
        new Matrix4x4();
        this.matTitle = Matrix4x4.createTranslation(-220, 400, 0);
        this.matEntries = new Matrix4x4[] {
                Matrix4x4.createTranslation(-150, 160, -1),
                Matrix4x4.createTranslation(-150, 40, -1),
                Matrix4x4.createTranslation(-150, -80, -1),
                Matrix4x4.createTranslation(-150, -200, -1)
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
        this.optionManager.changeOptions(i);
        this.clickedOption = this.optionManager.getOption(i);
        switch (i) {
            case 0:
                break;
            case 1:
            	break;
            case 2:
                break;
            case 3:
                this.setGameState(StateType.MENU);
                break;
        }
    }
	
	@Override
    public StateType getStateType() {
        return StateType.DIFFICULTYOPTIONS;
    }
	
	
	
}
