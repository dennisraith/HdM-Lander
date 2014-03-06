
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import de.hdm.spe.lander.R;

import java.io.IOException;


public class MediaManager {

    public enum LanderSound {
        MenuClick(R.raw.click),
        RocketBurst(R.raw.thruster_sound),
        Explosion(R.raw.explosion);

        int resId;
        int soundId;
        int streamID;

        LanderSound(int resId) {
            this.resId = resId;
        }

        void setSoundID(int id) {
            this.soundId = id;
        }

        void setStreamID(int id) {
            this.streamID = id;
        }
    }

    private final MediaPlayer   mediaPlayer;
    private final SoundPool     soundPool;

    private static MediaManager sInstance = null;
    private final Context       mContext;

    private MediaManager(Context context) {
        this.mContext = context;
        this.mediaPlayer = new MediaPlayer();
        this.soundPool = new SoundPool(LanderSound.values().length, AudioManager.STREAM_MUSIC, 0);
        this.loadSounds();

    }

    private void loadSounds() {

        for (LanderSound s : LanderSound.values()) {
            s.setSoundID(this.soundPool.load(this.mContext, s.resId, 1));
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
            e.printStackTrace();
        }
        this.startTrack();
    }

    public void startTrack() {
        if (OptionManager.getInstance().isSoundEnabled()) {
            this.mediaPlayer.start();
            this.mediaPlayer.setLooping(true);
        }
    }

    public void playSound(LanderSound sound) {
        if (OptionManager.getInstance().isSoundEnabled()) {
            sound.setStreamID(this.soundPool.play(sound.soundId, 1, 1, 0, 0, 1));
        }
    }

    public void stopSound(LanderSound sound) {
        this.soundPool.stop(sound.streamID);
    }

    public void reset() {
        this.mediaPlayer.stop();
        this.mediaPlayer.reset();
    }

    public static MediaManager getInstance() {
        return MediaManager.sInstance;
    }

    public static MediaManager initialize(Context context) {
        MediaManager.sInstance = new MediaManager(context);
        return MediaManager.sInstance;
    }
}
