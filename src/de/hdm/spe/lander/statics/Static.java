
package de.hdm.spe.lander.statics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import de.hdm.spe.lander.R;
import de.hdm.spe.lander.models.HighscoreAdapter;

import java.text.NumberFormat;


public class Static {

    public static int          CAM_DIMEN           = 100;
    public static int          CAM_FAR             = 100;
    public static int          CAM_NEAR            = 0;

    public final static String sLanderMesh         = "rocket.obj";
    public final static String sLanderTex          = "customRocketTex.png";
    public final static String sAsteroidMesh       = "asteroid.obj";
    public final static String sAsteroidTex        = "comet_texture.jpg";
    public final static String sMenuBgImage        = "moonLanding.jpg";

    public final static String sScorePrefsName     = "lander.scores";
    public final static String sSettingsPrefsName  = "lander.settings";
    public final static String sSettingsMusic      = "settings.music";
    public final static String sSettingsDifficulty = "settings.difficulty";
    public final static String sSettingsLanguage   = "settings.lang";
    public final static String sSettingsVibration  = "settings.vibrate";

    public static NumberFormat numberFormat        = NumberFormat.getNumberInstance();

    public static AlertDialog createScoreDialog(Context context, float score, OnClickListener listener) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMaximumFractionDigits(0);
        format.setMinimumFractionDigits(0);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.score_input, null);
        TextView tv = (TextView) view.findViewById(R.id.score);
        tv.setText(format.format(score));
        dialog.setView(view);
        dialog.setPositiveButton(android.R.string.ok, listener);
        dialog.setNegativeButton(android.R.string.cancel, listener);
        return dialog.create();
    }

    public static AlertDialog createHighscoreListDialog(Context context, HighscoreAdapter adapter, OnClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.score_overview, null);
        ListView lv = (ListView) view.findViewById(R.id.hs_list);
        lv.setAdapter(adapter);
        dialog.setView(view);
        dialog.setNeutralButton(android.R.string.ok, listener);
        return dialog.create();
    }
}
