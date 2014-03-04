
package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;


public class CreditsLevel extends Level {

    public CreditsLevel(Game game) {
        super(game);
        this.mLander.getWorld().translate(0, -90, 0);
    }

    @Override
    public void update(float deltaSeconds) {
        this.mLander.updatePosition(deltaSeconds);
        if (this.mLander.getPosition().getY() < -30) {
            this.mLander.setAccelerating(true);
        } else if (this.mLander.getPosition().getY() > 30) {
            this.mLander.setAccelerating(false);
        }

    }

    @Override
    public void onAccelerometerEvent(float[] values) {
    }

    @Override
    public StateType getStateType() {
        return StateType.CREDITSLEVEL;
    }

}
