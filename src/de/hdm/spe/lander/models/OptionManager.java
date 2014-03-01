package de.hdm.spe.lander.models;

import de.hdm.spe.lander.statics.Static;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class OptionManager {
	
	public String[] options;
	private boolean musicState = true;
	// true  = DE
	private boolean languageState = true;
	private Context mContext;
	
	public OptionManager(Context context){
		mContext = context;
	}
	
	public void loadOptions(){
		
		SharedPreferences sharedPre = mContext.getSharedPreferences(Static.sSettingsPrefsName, 0);
		musicState = sharedPre.getBoolean(Static.sSettingsMusic, true);
		languageState = sharedPre.getBoolean(Static.sSettingsLanguage, true);
		
		options = new String[5];
		
		String musicState = this.musicState?"ON":"OFF";
		String langString = this.languageState?"DE":"EN";
		
			options[0] = "Music "+musicState;
			options[1] = "Highscore";
			options[2] = "Schwierigkeit";
			options[3] = "Sprache "+langString;
			options[4] = "Zurück";
	
	}
	
	 public void saveOptions(){
		 Editor edit = mContext.getSharedPreferences(Static.sSettingsPrefsName, 0).edit();
		 edit.putBoolean(Static.sSettingsMusic, musicState);
		 edit.putBoolean(Static.sSettingsLanguage, languageState);
		 edit.commit();
	 }
	
	public void changeOptions(int i){

		switch (i){
		case 0:
			if(musicState){
				options[0] = "Music OFF";
				musicState = false;
			}else{
				options[0] = "Music ON";
				musicState = true;
			}
			break;
		case 3:
			if(languageState){
				options[3] = "Sprache EN";
				languageState = false;
			}else{
				options[3] = "Sprache DE";
				languageState = true;
			}
			break;
		}
				
	}
		

	public String getOption(int i){
		return options[i];
	}
		
	
}
