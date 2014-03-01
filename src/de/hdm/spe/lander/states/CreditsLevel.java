package de.hdm.spe.lander.states;

import de.hdm.spe.lander.game.Game;

public class CreditsLevel extends Level {

	public CreditsLevel(Game game) {
		super(game);
		mLander.getWorld().translate(0, -90, 0);
	}
	
	@Override
	public void update(float deltaSeconds){
		mLander.updatePosition(deltaSeconds);
		if(mLander.getPosition().getY() < -30){
			mLander.setAccelerating(true);
		}else
		if(mLander.getPosition().getY() > 30){
			mLander.setAccelerating(false);
		}
		
	}

	@Override
	public StateType getStateType() {
		return StateType.CREDITSLEVEL;
	}

}
