package de.hdm.spe.lander.game;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.models.GameState;


public class GameStateManager {
	
	private Game game;
	private GameState currentState;
	
	public GameStateManager(Game game) {
		this.game = game;
	}
	
	public GameState getGameState() {
		return currentState;
	}

	public void setGameState(GameState newState) {
		if (currentState != null) {
			currentState.shutdown();
			currentState = null;
		}
		
		if (newState != null) {
			newState.initialize();
			if (game.isInitialized())
				newState.loadContent();
			
			currentState = newState;
		}
	}
	
	public void loadContent() {
		if (currentState != null) {
			currentState.loadContent();
		}
	}
	
	public void update(float deltaSeconds) {
		if (currentState != null) {
			currentState.update(deltaSeconds);
		}
	}
	
	public void draw(float deltaSeconds) {
		if (currentState != null) {
			currentState.draw(deltaSeconds, null);
		}
	}
	
	public void resize(int width, int height) {
		if (currentState != null) {
			currentState.resize(width, height);
		}
	}
	
	public void pause() {
		if (currentState != null) {
			currentState.pause();
		}
	}
	
	public void resume() {
		if (currentState != null) {
			currentState.resume();
		}
	}
}
