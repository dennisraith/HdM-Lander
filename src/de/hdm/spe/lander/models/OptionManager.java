
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Static;


public class OptionManager {

    private static OptionManager sInstance = null;

    public static OptionManager initialize(Context context) {
        if (OptionManager.sInstance == null) {
            OptionManager.sInstance = new OptionManager(context);
        }
        return OptionManager.sInstance;
    }

    public static OptionManager getInstance() {
        return OptionManager.sInstance;
    }

    public String[]       options;
    private boolean       musicState    = true;
    // true  = DE
    private boolean       languageState = true;
    private final Context mContext;
    
    private Difficulty difficulty = Difficulty.EASY;

    private OptionManager(Context context) {
        this.mContext = context;
        this.loadOptions();
    }

    private void loadOptions() {

        SharedPreferences sharedPre = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0);
        this.musicState = sharedPre.getBoolean(Static.sSettingsMusic, true);
        this.languageState = sharedPre.getBoolean(Static.sSettingsLanguage, true);

        this.options = new String[5];

        String musicState = this.musicState ? "ON" : "OFF";
        String langString = this.languageState ? "DE" : "EN";

        this.options[0] = "Music " + musicState;
        this.options[1] = "Highscore";
        this.options[2] = "Schwierigkeit";
        this.options[3] = "Sprache " + langString;
        this.options[4] = "Zurück";

    }

    public boolean isSoundEnabled() {
        return this.musicState;
    }

    public boolean getLanguage() {
        return this.languageState;
    }
    
    public void setDifficulty(Difficulty diff){
    	this.difficulty = diff;
    }

    public void saveOptions() {
        Editor edit = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0).edit();
        edit.putBoolean(Static.sSettingsMusic, this.musicState);
        edit.putBoolean(Static.sSettingsLanguage, this.languageState);
        edit.commit();
    }

    public void changeOptions(int i) {

        switch (i) {
            case 0:
                if (this.musicState) {
                    this.options[0] = "Music OFF";
                    this.musicState = false;
                } else {
                    this.options[0] = "Music ON";
                    this.musicState = true;
                }
                break;
            case 3:
                if (this.languageState) {
                    this.options[3] = "Sprache EN";
                    this.languageState = false;
                } else {
                    this.options[3] = "Sprache DE";
                    this.languageState = true;
                }
                break;
        }

    }

    public String getOption(int i) {
        return this.options[i];
    }

}
