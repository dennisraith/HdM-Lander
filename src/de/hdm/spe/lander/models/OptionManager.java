
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.hdm.spe.lander.models.MediaManager.Track;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Lang;
import de.hdm.spe.lander.statics.Static;

import java.util.Locale;


public class OptionManager {

    public enum Language {
        DE(Locale.GERMAN),
        EN(Locale.ENGLISH);

        Locale locale;

        Language(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return this.locale;
        }
    }

    private static OptionManager sInstance = null;

    public static OptionManager initialize(Context context, LocaleChangeListener listener) {
        if (OptionManager.sInstance == null) {
            OptionManager.sInstance = new OptionManager(context, listener);

        }
        return OptionManager.sInstance;
    }

    public static OptionManager getInstance() {
        return OptionManager.sInstance;
    }

    public String[]                    options;
    private boolean                    musicState    = true;
    // true  = DE
    private Language                   mLanguage;
    private final Context              mContext;

    private final LocaleChangeListener mListener;

    private Difficulty                 difficulty    = Difficulty.EASY;
    private boolean                    allowsVibrate = false;

    private OptionManager(Context context, LocaleChangeListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.loadOptions();
    }

    private void loadOptions() {

        SharedPreferences sharedPre = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0);
        this.musicState = sharedPre.getBoolean(Static.sSettingsMusic, true);
        this.difficulty = Difficulty.valueOf(sharedPre.getString(Static.sSettingsDifficulty, Difficulty.EASY.toString()));
        this.mLanguage = Language.valueOf(sharedPre.getString(Static.sSettingsLanguage, Language.DE.toString()));
        this.allowsVibrate = sharedPre.getBoolean(Static.sSettingsVibration, true);
        this.mListener.setLocale(this.mLanguage);
        this.prepareStrings();
    }

    private void prepareStrings() {
        this.options = new String[5];
        String musicState = this.musicState ? Lang.STATE_ON : Lang.STATE_OFF;

        this.options[0] = Lang.OPTIONS_MUSIC + " " + musicState;
        this.options[1] = Lang.OPTIONS_HIGHSCORE;
        this.options[2] = Lang.OPTIONS_DIFFICULTY;
        this.options[3] = Lang.OPTIONS_LANG + " " + this.mLanguage.toString();
        this.options[4] = Lang.BACK;
    }

    public boolean isSoundEnabled() {
        return this.musicState;
    }

    public boolean isVibrationEnabled() {
        return this.allowsVibrate;
    }

    public void setDifficulty(Difficulty diff) {
        this.difficulty = diff;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Language getLanguage() {
        return this.mLanguage;
    }

    public void saveOptions() {
        Editor edit = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0).edit();
        edit.putBoolean(Static.sSettingsMusic, this.musicState);
        edit.putBoolean(Static.sSettingsVibration, this.allowsVibrate);
        edit.putString(Static.sSettingsDifficulty, this.difficulty.toString());
        edit.putString(Static.sSettingsLanguage, this.mLanguage.toString());
        edit.commit();
    }

    public void changeOptions(int i) {

        switch (i) {
            case 0:
                if (this.musicState) {
                    this.options[0] = Lang.OPTIONS_MUSIC + " " + Lang.STATE_OFF;
                    this.musicState = false;
                    MediaManager.getInstance().reset();
                } else {
                    this.options[0] = Lang.OPTIONS_MUSIC + " " + Lang.STATE_ON;
                    this.musicState = true;
                    MediaManager.getInstance().startTrack(Track.Menu);
                }
                break;
            case 3:

                Language locale = this.mLanguage.locale == Locale.ENGLISH ? Language.DE : Language.EN;
                this.mLanguage = locale;
                this.mListener.onLocaleChanged(locale);
                this.prepareStrings();
                break;
        }

    }

    public Difficulty toggleDifficulty() {
        int ordinal = this.difficulty.ordinal() + 1 > 2 ? 0 : this.difficulty.ordinal() + 1;
        this.difficulty = Difficulty.values()[ordinal];
        return this.difficulty;
    }

    public String getOption(int i) {
        return this.options[i];
    }

    public interface LocaleChangeListener {

        void onLocaleChanged(Language locale);

        void setLocale(Language locale);
    }

}
