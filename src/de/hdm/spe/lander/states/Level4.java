
package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;
import de.hdm.spe.lander.gameobjects.Asteroid;
import de.hdm.spe.lander.gameobjects.Obstacles;


public class Level4 extends Level {

    public Level4(Game game) {
        super(game);
        this.setMovePlatform(true);
    }

    @Override
    public StateType getStateType() {
        return StateType.LEVEL4;
    }

    @Override
    protected boolean prepareObstacles() {
        Asteroid[] data = new Asteroid[4];
        data[0] = Asteroid.newInstance(6, -18, -80);
        data[1] = Asteroid.newInstance(5, 6, -80);
        data[2] = Asteroid.newInstance(20, 50, -50);
        data[3] = Asteroid.newInstance(10, -10, -30);
        this.mObstacles = new Obstacles(data);
        return true;
    }

}
