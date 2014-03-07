
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.hdm.spe.lander.models.MediaManager.Track;
import de.hdm.spe.lander.statics.Difficulty;
import de.hdm.spe.lander.statics.Lang;
import de.hdm.spe.lander.statics.Static;

import java.util.Locale;


/**
 * 
 * manages options in options menu
 * Singleton.
 * @author boris
 *
 */
public class OptionManager {

    /**
     * 
     * manages languages
     * @author boris
     *
     */
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

    /**
     * 
     * return and if needed initializes the option manager
     * @param context
     * @param listener
     * @return OptionManager object
     */
    public static OptionManager initialize(Context context, LocaleChangeListener listener) {
        if (OptionManager.sInstance == null) {
            OptionManager.sInstance = new OptionManager(context, listener);

        }
        return OptionManager.sInstance;
    }

    /**
     * @return OptionManager object
     */
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

    /**
     * @param context
     * @param listener
     */
    private OptionManager(Context context, LocaleChangeListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.loadOptions();
    }

    /**
     * loads the option states from shared preferences
     */
    private void loadOptions() {

        SharedPreferences sharedPre = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0);
        this.musicState = sharedPre.getBoolean(Static.sSettingsMusic, true);
        this.difficulty = Difficulty.valueOf(sharedPre.getString(Static.sSettingsDifficulty, Difficulty.EASY.toString()));
        this.mLanguage = Language.valueOf(sharedPre.getString(Static.sSettingsLanguage, Language.DE.toString()));
        this.allowsVibrate = sharedPre.getBoolean(Static.sSettingsVibration, true);
        this.mListener.setLocale(this.mLanguage);
        this.prepareStrings();
    }

    /**
     * prepares option strings
     * sets the option names
     */
    private void prepareStrings() {
        this.options = new String[6];
        String musicState = this.musicState ? Lang.STATE_ON : Lang.STATE_OFF;
        String vibrateState = this.allowsVibrate ? Lang.STATE_ON : Lang.STATE_OFF;

        this.options[0] = Lang.OPTIONS_MUSIC + " " + musicState;
        this.options[1] = Lang.OPTIONS_VIBRO + " " + vibrateState;
        this.options[2] = Lang.OPTIONS_HIGHSCORE;
        this.options[3] = Lang.OPTIONS_DIFFICULTY;
        this.options[4] = Lang.OPTIONS_LANG + " " + this.mLanguage.toString();
        this.options[5] = Lang.BACK;
    }

    /**
     * @return if sound is enabled
     */
    public boolean isSoundEnabled() {
        return this.musicState;
    }

    /**
     * @return if vibration is enabled
     */
    public boolean isVibrationEnabled() {
        return this.allowsVibrate;
    }

    /**
     * @param diff
     */
    public void setDifficulty(Difficulty diff) {
        this.difficulty = diff;
    }

    /**
     * @return the chosen difficulty
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * @return current language
     */
    public Language getLanguage() {
        return this.mLanguage;
    }

    /**
     * saves the options
     */
    public void saveOptions() {
        Editor edit = this.mContext.getSharedPreferences(Static.sSettingsPrefsName, 0).edit();
        edit.putBoolean(Static.sSettingsMusic, this.musicState);
        edit.putBoolean(Static.sSettingsVibration, this.allowsVibrate);
        edit.putString(Static.sSettingsDifficulty, this.difficulty.toString());
        edit.putString(Static.sSettingsLanguage, this.mLanguage.toString());
        edit.commit();
    }

    /**
     * changes option if clicked
     * @param i
     */
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
            case 1:
                if (this.allowsVibrate) {
                    this.options[1] = Lang.OPTIONS_VIBRO + " " + Lang.STATE_OFF;
                    this.allowsVibrate = false;
                } else {
                    this.options[1] = Lang.OPTIONS_VIBRO + " " + Lang.STATE_ON;
                    this.allowsVibrate = true;
                }
                break;
            case 4:

                Language locale = this.mLanguage.locale == Locale.ENGLISH ? Language.DE : Language.EN;
                this.mLanguage = locale;
                this.mListener.onLocaleChanged(locale);
                this.prepareStrings();
                break;
        }

    }

    /**
     * changes the difficulty
     * @return current difficulty
     */
    public Difficulty toggleDifficulty() {
        int ordinal = this.difficulty.ordinal() + 1 > 2 ? 0 : this.difficulty.ordinal() + 1;
        this.difficulty = Difficulty.values()[ordinal];
        return this.difficulty;
    }

    /**
     * @param i
     * @return
     */
    public String getOption(int i) {
        return this.options[i];
    }

    /**
     * @author boris
     *
     */
    public interface LocaleChangeListener {

        void onLocaleChanged(Language locale);

        void setLocale(Language locale);
    }

}
