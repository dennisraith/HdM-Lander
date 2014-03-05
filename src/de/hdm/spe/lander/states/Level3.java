
package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Asteroid;
import de.hdm.spe.lander.gameobjects.Obstacles;


public class Level3 extends Level {

    public Level3(Game game) {
        super(game);
    }

    @Override
    public StateType getStateType() {
        return StateType.LEVEL3;
    }

    @Override
    protected boolean prepareObstacles() {
        Asteroid[] data = new Asteroid[3];
        data[0] = Asteroid.newInstance(6, -18, -80);
        data[1] = Asteroid.newInstance(5, 6, -80);
        data[2] = Asteroid.newInstance(20, 50, -50);
        this.mObstacles = new Obstacles(data);
        return true;
    }

}
