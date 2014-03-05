
package de.hdm.spe.lander.statics;

import android.content.Context;

import de.hdm.spe.lander.R;


public class Lang {

    public static String GAME_NAME = "Moon Landing";

    public static String MENU;
    public static String MENU_NEWGAME;
    public static String MENU_QUIT;

    public static String OPTIONS_NAME;
    public static String OPTIONS_MUSIC;
    public static String OPTIONS_HIGHSCORE;
    public static String OPTIONS_DIFFICULTY;
    public static String OPTIONS_LANG;
    public static String BACK;
    public static String STATE_ON;
    public static String STATE_OFF;
    public static String LANG_DE;
    public static String LANG_EN;

    public static String GAME_SPEED;
    public static String GAME_TIME;
    public static String GAME_FUEL;
    public static String GAME_CRASH;
    public static String GAME_LANDING;

    public static void prepare(Context context) {
        Lang.MENU = context.getString(R.string.menu_name);
        Lang.MENU_NEWGAME = context.getString(R.string.menu_newgame);
        Lang.MENU_QUIT = context.getString(R.string.quit);
        Lang.OPTIONS_NAME = context.getString(R.string.options_name);
        Lang.OPTIONS_MUSIC = context.getString(R.string.options_music);
        Lang.OPTIONS_HIGHSCORE = context.getString(R.string.options_highscore);
        Lang.OPTIONS_DIFFICULTY = context.getString(R.string.options_difficulty);
        Lang.OPTIONS_LANG = context.getString(R.string.options_language);
        Lang.BACK = context.getString(R.string.back);
        Lang.STATE_ON = context.getString(R.string.state_on);
        Lang.STATE_OFF = context.getString(R.string.state_off);
        Lang.LANG_DE = context.getString(R.string.lang_de);
        Lang.LANG_EN = context.getString(R.string.lang_en);
        Lang.GAME_SPEED = context.getString(R.string.game_speed);
        Lang.GAME_TIME = context.getString(R.string.game_time);
        Lang.GAME_FUEL = context.getString(R.string.game_fuel);
        Lang.GAME_CRASH = context.getString(R.string.game_crash);
        Lang.GAME_LANDING = context.getString(R.string.game_landing);
    }

}
