package de.hdm.spe.lander.game;

import android.content.Context;
import android.opengl.GLSurfaceView;

import de.hdm.spe.lander.game.MGDExerciseGame;

public class MGDExerciseView extends GLSurfaceView {

	private MGDExerciseGame game;
	
	public MGDExerciseView(Context context) {
		super(context);
		
		game = new MGDExerciseGame(this);
		setRenderer(game);
		
		setRenderMode(RENDERMODE_CONTINUOUSLY);
	}

	@Override
	public void onPause() {
		super.onPause();
		game.pause();
	}
	
	@Override
	public void onResume() {
		game.resume();
		super.onResume();
	}
}
