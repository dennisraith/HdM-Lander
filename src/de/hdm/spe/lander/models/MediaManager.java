package de.hdm.spe.lander.models;

import java.io.IOException;

import de.hdm.spe.lander.R;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MediaManager {
	
	private MediaPlayer mediaPlayer;
	private SoundPool soundPool;
	private int click;
	
	
	private static MediaManager sInstance=null;
	private Context mContext;
	
	private MediaManager(Context context){
		this.mContext = context;
		mediaPlayer = new MediaPlayer();
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		
	}
	
	public static MediaManager initialize(Context context){
		sInstance = new MediaManager(context);
		return sInstance;
	}

	public void loadSounds(){
		click = soundPool.load(mContext, R.raw.click, 1);
	}
	
	public void playSound(){
		soundPool.play(click, 1, 1, 0, 0, 1);
	}
	
	public void loadTrack(String fName){
		if (mediaPlayer.isPlaying()){
			mediaPlayer.stop();
			mediaPlayer.reset();
		}
		try {
    		
    		AssetFileDescriptor afd = mContext.getAssets().openFd(fName);
    		
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.prepare();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();
	}
	
	
	
	public void reset(){
		mediaPlayer.stop();
		mediaPlayer.reset();
	}
	
	
	public static MediaManager getInstance(){
		return sInstance;
	}
	
}
