
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.hdm.spe.lander.statics.Lang;
import de.hdm.spe.lander.statics.Static;

import java.util.Locale;


public class OptionManager {

    private static OptionManager sInstance = null;

    public static OptionManager initialize(Context context, LocaleChangeListener listener) {
        if (OptionManager.sInstance == null) {
            OptionManager.sInstance = new OptionManager(context);
            OptionManager.sInstance.mListener = listener;
        }
        return OptionManager.sInstance;
    }

    public static OptionManager getInstance() {
        return OptionManager.sInstance;
    }

    public String[]              options;
    private boolean              musicState    = true;
    // true  = DE
    private boolean              languageState = true;
    private final Context        mContext;

    private LocaleChangeListener mListener;

    private OptionManager(Context context) {
        this.mContext = context;
        this.loadOptions();
    }

    private void loadOptions() {

        SharedPreferences sharedPre = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0);
        this.musicState = sharedPre.getBoolean(Static.sSettingsMusic, true);
        this.languageState = sharedPre.getBoolean(Static.sSettingsLanguage, true);

        this.options = new String[5];

        String musicState = this.musicState ? Lang.STATE_ON : Lang.STATE_OFF;
        String langString = this.languageState ? Lang.LANG_DE : Lang.LANG_EN;

        this.options[0] = Lang.OPTIONS_MUSIC + " " + musicState;
        this.options[1] = Lang.OPTIONS_HIGHSCORE;
        this.options[2] = Lang.OPTIONS_DIFFICULTY;
        this.options[3] = Lang.OPTIONS_LANG + " " + langString;
        this.options[4] = Lang.BACK;

    }

    public boolean isSoundEnabled() {
        return this.musicState;
    }

    public boolean getLanguage() {
        return this.languageState;
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
                    this.options[0] = Lang.OPTIONS_MUSIC + " " + Lang.STATE_OFF;
                    this.musicState = false;
                } else {
                    this.options[0] = Lang.OPTIONS_MUSIC + " " + Lang.STATE_ON;
                    this.musicState = true;
                }
                break;
            case 3:
                Locale locale;
                if (this.languageState) {
                    this.options[3] = Lang.OPTIONS_LANG + " " + Lang.LANG_EN;
                    this.languageState = false;
                    locale = Locale.ENGLISH;
                } else {
                    this.options[3] = Lang.OPTIONS_LANG + " " + Lang.LANG_DE;
                    this.languageState = true;
                    locale = Locale.GERMANY;
                }
                this.mListener.onLocaleChanged(locale);
                break;
        }

    }

    public String getOption(int i) {
        return this.options[i];
    }

    public interface LocaleChangeListener {

        void onLocaleChanged(Locale locale);
    }

}
