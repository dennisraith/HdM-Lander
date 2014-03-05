
package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;


public class Level1 extends Level {

    public Level1(Game game) {
        super(game);
    }

    @Override
    public StateType getStateType() {
        return StateType.LEVEL1;
    }

    @Override
    protected boolean prepareObstacles() {
        return false;
    }

}
