package fr.fjdhj.PacMan.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuMapping {
	
	@FXML
	private Button playButton;
	@FXML
	private Button settingButton;
	@FXML
	private Button exitButton;
	
	
	

	public MainMenuMapping() {}
	
	@FXML
	private void initialize() {}
	
	
	/*
	 * Lance la partie
	 */
	@FXML
	public void playGame() {
		
	}
	
	/*
	 * Ouvre les parametre
	 */
	@FXML
	public void openSetting() {
		
	}
	
	/*
	 * Ferme l'application
	 */
	@FXML
	public void exit(){
		System.exit(0);
	}
}
