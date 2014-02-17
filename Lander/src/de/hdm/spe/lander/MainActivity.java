
package de.hdm.spe.lander;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import de.hdm.spe.lander.engine.MGDView;
import de.hdm.spe.lander.math.Vector3;


public class MainActivity extends Activity {

    private MGDView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.mSurfaceView = new MGDView(this);
        this.setContentView(this.mSurfaceView);
    }

    @SuppressWarnings("unused")
    private void testMath() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(6, 5, 4);

        Log.d(this.getClass().getSimpleName(), "-------init-----");
        float dot = Vector3.dot(v1, v2);
        Log.d(this.getClass().getSimpleName(), "-------dot-----" + dot + "");

        float v1leng = v1.getLength();
        float v2leng = v2.getLength();
        Log.d(this.getClass().getSimpleName(), "length v1: " + v1leng + " must be " + Math.sqrt(v1.getLengthSqr()));
        Log.d(this.getClass().getSimpleName(), "length v2: " + v2leng + " must be " + Math.sqrt(v2.getLengthSqr()));
        double cos = dot / (v1leng * v2leng);

        Log.d(this.getClass().getSimpleName(), "alpha:" + Math.toDegrees(Math.acos(cos)));
        Log.d(this.getClass().getSimpleName(), "--- cross ---");
        Vector3.cross(v1, v2).log();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        if (this.mSurfaceView != null) {
            this.mSurfaceView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (this.mSurfaceView != null) {
            this.mSurfaceView.onResume();
        }

        super.onResume();
    }

}
