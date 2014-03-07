
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.hdm.spe.lander.math.Vector2;
import de.hdm.spe.lander.statics.Static;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


/**
 * Manager responsible for saving, loading and managing old and new Highscores
 * @author Dennis
 *
 */
public class HighscoreManager {

    private static HighscoreManager     sInstance  = null;
    private final Context               mContext;
    private ArrayList<Highscore>        mScores    = new ArrayList<Highscore>();
    private final Comparator<Highscore> comparator = new Comparator<Highscore>() {

                                                       @Override
                                                       public int compare(Highscore lhs, Highscore rhs) {
                                                           if (lhs.getScore() == rhs.getScore()) {
                                                               return 0;
                                                           }

                                                           if (lhs.getScore() < rhs.getScore()) {
                                                               return 1;
                                                           }
                                                           else {
                                                               return -1;
                                                           }

                                                       }
                                                   };

    private HighscoreManager(Context context) {
        this.mContext = context;
        Static.numberFormat.setMaximumFractionDigits(2);
        Static.numberFormat.setMinimumFractionDigits(0);
        this.loadScores();

    }

    public static HighscoreManager initialize(Context context) {
        HighscoreManager.sInstance = new HighscoreManager(context);
        return HighscoreManager.sInstance;
    }

    public static HighscoreManager getInstance() {
        return HighscoreManager.sInstance;
    }

    private void loadScores() {
        SharedPreferences prefs = this.mContext.getSharedPreferences(Static.sScorePrefsName, 0);
        Map<String, Float> scores = (Map<String, Float>) prefs.getAll();

        for (String key : scores.keySet()) {
            float score = scores.get(key);
            this.mScores.add(new Highscore(key, score));
        }

        Collections.sort(this.mScores, this.comparator);
        while (this.mScores.size() > 10) {
            this.mScores.remove(this.mScores.size() - 1);
        }
    }

    public void saveScores() {

        Editor edit = this.mContext.getSharedPreferences(Static.sScorePrefsName, 0).edit().clear();
        for (Highscore score : this.mScores) {

            edit.putFloat(score.getName(), score.getScore());
        }
        edit.apply();
    }

    public boolean checkHighscore(float time, Vector2 speed) {
        float score = time * speed.getLength();
        return this.checkHighscore(score);
    }

    /**
     * Check if the given score is good enough to become a Highscore
     * @param score
     * @return whether this is a Highscore
     */
    public boolean checkHighscore(float score) {
        if (this.mScores.size() < 10) {
            return true;
        }
        for (Highscore hs : this.mScores) {
            if (hs.getScore() < score) {
                this.mScores.remove(this.mScores.size() - 1);
                return true;
            }
        }
        return false;
    }

    public void clearHighscore() {
        this.mScores = new ArrayList<Highscore>();
        this.mContext.getSharedPreferences(Static.sScorePrefsName, 0).edit().clear().commit();
    }

    public boolean addHighscore(Highscore score) {
        if (!this.checkHighscore(score.getScore())) {
            return false;
        }
        else {
            this.mScores.add(score);
            return true;
        }
    }

    public HighscoreAdapter getAdapter() {
        Collections.sort(this.mScores, this.comparator);
        return new HighscoreAdapter(this.mContext, 0, this.mScores);
    }

}
