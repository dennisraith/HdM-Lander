
package de.hdm.spe.lander.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

import de.hdm.spe.lander.game.MGDGame;


public class MGDView extends GLSurfaceView {

    private final MGDGame mRenderer;

    public MGDView(Context context) {
        super(context);

        this.mRenderer = new MGDGame(context);
        this.setRenderer(this.mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
