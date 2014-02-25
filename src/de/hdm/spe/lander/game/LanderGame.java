
package de.hdm.spe.lander.game;

import android.view.View;

import de.hdm.spe.lander.states.Menu;


public class LanderGame extends Game {

    public LanderGame(View view) {
        super(view);

    }

    @Override
    public void initialize() {
        this.mCurrentState = new Menu(this);
    }

}
