
package de.hdm.spe.lander.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class LanderActivity extends Activity {

    private LanderView view;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.view = new LanderView(this);
        this.setContentView(this.view);
    }

    @Override
    protected void onPause() {
        this.view.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        this.view.onResume();
        super.onResume();
    }

}