
package de.hdm.spe.lander.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import de.hdm.spe.lander.R;

import java.io.IOException;


public class MediaManager {

    public enum SoundEffect {
        MenuClick(R.raw.click),
        RocketBurst(R.raw.thruster_sound),
        Explosion(R.raw.explosion);

        int resId;
        int soundId;
        int streamID;

        SoundEffect(int resId) {
            this.resId = resId;
        }

        void setSoundID(int id) {
            this.soundId = id;
        }

        void setStreamID(int id) {
            this.streamID = id;
        }
    }

    public enum Track {
        Menu("space-menu.mp3"),
        Level("space-level.mp3");

        String              fileName;
        AssetFileDescriptor descriptor;

        Track(String fileName) {
            this.fileName = fileName;
        }

        void setDescriptor(AssetFileDescriptor descriptor) {
            this.descriptor = descriptor;
        }

    }

    private final MediaPlayer   mediaPlayer;
    private final SoundPool     soundPool;

    private static MediaManager sInstance = null;
    private final Context       mContext;
    private Track               mCurrentTrack;

    private MediaManager(Context context) {
        this.mContext = context;
        this.mediaPlayer = new MediaPlayer();
        this.soundPool = new SoundPool(SoundEffect.values().length, AudioManager.STREAM_MUSIC, 0);
        this.initialize();

    }

    private void initialize() {

        for (SoundEffect s : SoundEffect.values()) {
            s.setSoundID(this.soundPool.load(this.mContext, s.resId, 1));
        }

        for (Track t : Track.values()) {
            try {
                t.setDescriptor(this.mContext.getAssets().openFd(t.fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadTrack(Track track) {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
        }
        try {
            AssetFileDescriptor afd = track.descriptor;
            this.mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            this.mediaPlayer.prepare();
            this.mCurrentTrack = track;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTrack(Track track) {
        if (!OptionManager.getInstance().isSoundEnabled() || this.mCurrentTrack == track && this.mediaPlayer.isPlaying()) {
            return;
        }
        this.loadTrack(track);
        this.mediaPlayer.start();
        this.mediaPlayer.setLooping(true);
    }

    public void playSound(SoundEffect sound) {
        if (OptionManager.getInstance().isSoundEnabled()) {
            sound.setStreamID(this.soundPool.play(sound.soundId, 1, 1, 0, 0, 1));
        }
    }

    public void stopSound(SoundEffect sound) {
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
