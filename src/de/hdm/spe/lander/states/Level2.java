
package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Asteroid;
import de.hdm.spe.lander.gameobjects.Obstacles;


public class Level2 extends Level {

    public Level2(Game game) {
        super(game);
    }

    @Override
    public StateType getStateType() {
        return StateType.LEVEL2;
    }

    @Override
    protected boolean prepareObstacles() {
        Asteroid[] data = new Asteroid[2];
        data[0] = Asteroid.newInstance(6, -20, 5);
        data[1] = Asteroid.newInstance(5, 8, -40);
        this.mObstacles = new Obstacles(data);
        return true;
    }

}
