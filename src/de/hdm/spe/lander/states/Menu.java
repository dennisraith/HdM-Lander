
package de.hdm.spe.lander.states;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import de.hdm.spe.lander.graphics.GraphicsDevice;
import de.hdm.spe.lander.graphics.Renderer;
import de.hdm.spe.lander.models.GameState;
import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.graphics.Camera;
import de.hdm.spe.lander.math.Matrix4x4;
import de.hdm.spe.lander.collision.AABB;
import de.hdm.spe.lander.graphics.SpriteFont;
import de.hdm.spe.lander.graphics.TextBuffer;
import de.hdm.spe.lander.collision.Point;
import de.hdm.spe.lander.input.InputEvent;
import de.hdm.spe.lander.input.InputSystem;
import de.hdm.spe.lander.math.Vector3;


public class Menu implements GameState {
	
	private Camera hudCamera;
	
	private SpriteFont fontTitle;
	private TextBuffer textTitle;
	private Matrix4x4 matTitle;
	
	private SpriteFont fontMenu;
	private TextBuffer[] textMenu;
	private Matrix4x4[] matMenu;
	private AABB[] aabbMenu;
	
	private MediaPlayer mediaPlayer;
	private SoundPool soundPool;
	private int clickSound;


	public void initialize(Game game) {
		float width = game.getScreenWidth();
		float height = game.getScreenHeight();
		
		Matrix4x4 projection = new Matrix4x4();
		projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 100.0f);
				
		Matrix4x4 view = new Matrix4x4();

		hudCamera = new Camera();
		hudCamera.setProjection(projection);
		hudCamera.setView(view);

		matTitle = Matrix4x4.createTranslation(-width / 2, height / 2 - 64, 0);
	}
	
	public void loadContent(Game game) {
			
		}
	
	public void update(Game game, float deltaSeconds) {
		InputSystem inputSystem = game.getInputSystem();
		int screenWidth = game.getScreenWidth();
		int screenHeight = game.getScreenHeight();
		
		InputEvent inputEvent = inputSystem.peekEvent();
		while (inputEvent != null) {	
			switch (inputEvent.getDevice()) {
			case TOUCHSCREEN:
				switch (inputEvent.getAction()) {
				case DOWN:
					Vector3 screenTouchPosition = new Vector3(
							 (inputEvent.getValues()[0] / (screenWidth / 2) - 1),
							-(inputEvent.getValues()[1] / (screenHeight / 2) - 1),
							0);
					
					Vector3 worldTouchPosition = hudCamera.unproject(screenTouchPosition, 1);
					
					Point touchPoint = new Point(
							worldTouchPosition.getX(),
							worldTouchPosition.getY());
					
					for (int i = 0; i < aabbMenu.length; ++i) { 
						AABB aabb = aabbMenu[i];
						if (touchPoint.intersects(aabb)) {
							if (soundPool != null)
								soundPool.play(clickSound, 1, 1, 0, 0, 1);
							
							onMenuItemClicked(game, i);
						}
					}
				}
				break;
			}
			
			inputSystem.popEvent();
			inputEvent = inputSystem.peekEvent();
		}
	}

    private void onMenuItemClicked(Game game, int i) {
		// TODO Auto-generated method stub
		
	}

//	public void draw(Game game, float deltaSeconds) {
//		GraphicsDevice graphicsDevice = game.getGraphicsDevice();
//		Renderer renderer = game.getRenderer();
//		
//		graphicsDevice.clear(0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
//		
//		graphicsDevice.setCamera(hudCamera);
//		renderer.drawText(textTitle, matTitle);
//		for (int i = 0; i < textMenu.length; ++i)
//			renderer.drawText(textMenu[i], matMenu[i]);
//	}

//	public void resize(Game game, int width, int height) {
//		Matrix4x4 projection = new Matrix4x4();
//		projection.setOrthogonalProjection(-width / 2, width / 2, -height / 2, height / 2, 0.0f, 100.0f);
//		hudCamera.setProjection(projection);
//		
//		matTitle.setIdentity();
//		matTitle.translate(-width / 2, height / 2 - 64, 0);
//	}
	
    @Override
    public void prepare(Context context, GraphicsDevice device) {
		
		fontTitle = device.createSpriteFont(null, 16);
		textTitle = device.createTextBuffer(fontTitle, 16);
		textTitle.setText("Moon Landing");
				
		fontMenu = device.createSpriteFont(null, 16);
		textMenu = new TextBuffer[] {
				device.createTextBuffer(fontMenu, 16),
				device.createTextBuffer(fontMenu, 16),
				device.createTextBuffer(fontMenu, 16),
				device.createTextBuffer(fontMenu, 16)
		};
		textMenu[0].setText("Start Game");
		textMenu[1].setText("Options");
		textMenu[2].setText("Credits");
		textMenu[3].setText("Quit");
		
		matTitle = new Matrix4x4();
		matMenu = new Matrix4x4[] {
				Matrix4x4.createTranslation(0, 0, 0),
				Matrix4x4.createTranslation(0, 0, 0),
				Matrix4x4.createTranslation(0, 0, 0),
				Matrix4x4.createTranslation(0, 0, 0)
		};
		
		aabbMenu = new AABB[] {
				new AABB(-150, 150, 210, 70),
				new AABB(-150, 50, 180, 70),
				new AABB(-150, -50, 180, 70),
				new AABB(-150, -150, 140, 70)
		};
		
		/*
		while (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(context, R.raw.song);
		}			
		mediaPlayer.start();
		*/
		
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//		clickSound = soundPool.load(context, R.raw.click, 1);
    }


    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void shutdown(){
    	
    }

	@Override
	public void update(float deltaSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(float deltaSeconds, Renderer renderer) {
			renderer.drawText(textTitle, matTitle);
		for (int i = 0; i < matMenu.length; i++) {	
			renderer.drawText(textMenu[i], matMenu[i]);
		}
		
		
	}
    

}
