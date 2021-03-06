
package de.hdm.spe.lander.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.EditText;

import de.hdm.spe.lander.R;
import de.hdm.spe.lander.models.Highscore;
import de.hdm.spe.lander.models.HighscoreManager;
import de.hdm.spe.lander.states.GameState.StateType;
import de.hdm.spe.lander.statics.Static;


public class LanderGame extends Game {

    private AlertDialog mScoreDialog;

    public LanderGame(View view) {
        super(view);

    }

    @Override
    public void initialize() {
        this.setGameState(StateType.MENU);
    }

    public void onHighscoreListRequested() {
        this.view.post(new Runnable() {

            @Override
            public void run() {
                Static.createHighscoreListDialog(LanderGame.this.getContext(), HighscoreManager.getInstance().getAdapter(), new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });
    }

    public void onHighscoreDialogRequested(final float score) {
        this.setPaused(true);
        this.view.post(new Runnable() {

            @Override
            public void run() {
                LanderGame.this.mScoreDialog = Static.createScoreDialog(LanderGame.this.getContext(), score, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                String name = ((EditText) LanderGame.this.mScoreDialog.findViewById(R.id.nameInput)).getText().toString();
                                Highscore hs = new Highscore(name, score);
                                HighscoreManager.getInstance().addHighscore(hs);
                                HighscoreManager.getInstance().saveScores();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                        LanderGame.this.setGameState(StateType.MENU);
                    }

                });
                LanderGame.this.mScoreDialog.show();
            }
        });
    }

}
