
package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Obstacles;


public class Level1 extends Level {

    public Level1(Game game) {
        super(game);
        this.setUseObstacles(false);
        this.setObstacles(new Obstacles(2, 6, 6));
    }

    @Override
    public StateType getStateType() {
        return StateType.LEVEL1;
    }

}
