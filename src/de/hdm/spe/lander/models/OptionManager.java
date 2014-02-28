package de.hdm.spe.lander.models;

public class OptionManager {
	
	public String[] options;
	private boolean mState = true;
	private boolean sState = true;
	
	public OptionManager(){
		
	}
	
	public void loadOptions(){
		options = new String[5];
		
			options[0] = "Music ON";
			options[1] = "Highscore";
			options[2] = "Schwierigkeit";
			options[3] = "Sprache DE";
			options[4] = "Zurück";
	
	}
	
	
	public void changeOptions(int i){

		if(i == 0 && mState == true){
			options[0] = "Music OFF";
			mState = false;
		}else{
			options[0] = "Music ON";
			mState = true;
		}
		
		if(i == 3 && sState == true){
			options[3] = "Sprache EN";
			sState = false;
		}else{
			options[3] = "Sprache DE";
			sState = true;
		}
				
	}
		

	public String getOption(int i){
		return options[i];
	}
		
	
}
