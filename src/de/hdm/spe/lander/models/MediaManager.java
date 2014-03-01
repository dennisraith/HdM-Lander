
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import de.hdm.spe.lander.R;

import java.io.IOException;


public class MediaManager {

    private final MediaPlayer   mediaPlayer;
    private final SoundPool     soundPool;
    private int                 click;

    private static MediaManager sInstance = null;
    private final Context       mContext;

    private MediaManager(Context context) {
        this.mContext = context;
        this.mediaPlayer = new MediaPlayer();
        this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

    }

    public static MediaManager initialize(Context context) {
        MediaManager.sInstance = new MediaManager(context);
        return MediaManager.sInstance;
    }

    public void loadSounds() {
        this.click = this.soundPool.load(this.mContext, R.raw.click, 1);
    }

    public void playSound() {
        if (OptionManager.getInstance().isSoundEnabled()) {
            this.soundPool.play(this.click, 1, 1, 0, 0, 1);
        }
    }

    public void loadTrack(String fName) {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
        }
        try {

            AssetFileDescriptor afd = this.mContext.getAssets().openFd(fName);

            this.mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            this.mediaPlayer.prepare();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.mediaPlayer.start();
    }

    public void reset() {
        this.mediaPlayer.stop();
        this.mediaPlayer.reset();
    }

    public static MediaManager getInstance() {
        return MediaManager.sInstance;
    }

}
