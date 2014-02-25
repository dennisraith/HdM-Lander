
package de.hdm.spe.lander.models;

import de.hdm.spe.lander.states.GameState;

public interface GameStateChangedListener {

    public void onGameStateChanged(GameState newState);
}