
package de.hdm.spe.lander.game;

import android.content.Context;
import android.opengl.GLSurfaceView;


public class MGDExerciseView extends GLSurfaceView {

    private final MGDExerciseGame game;

    public MGDExerciseView(Context context) {
        super(context);

        this.game = new MGDExerciseGame(this);
        this.setRenderer(this.game);

        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.game.onPause();
    }

    @Override
    public void onResume() {
        this.game.onResume();
        super.onResume();
    }
}
