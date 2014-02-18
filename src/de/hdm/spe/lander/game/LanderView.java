
package de.hdm.spe.lander.game;

import android.content.Context;
import android.opengl.GLSurfaceView;


public class LanderView extends GLSurfaceView {

    private final LanderGame game;

    public LanderView(Context context) {
        super(context);

        this.game = new LanderGame(this);
        this.setRenderer(this.game);

        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.game.pause();
    }

    @Override
    public void onResume() {
        this.game.resume();
        super.onResume();
    }
}
