package fr.fjdhj.PacMan.view;

import fr.fjdhj.PacMan.gameLogic.GameLogic;
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
		//On charge notre partie
		GameLogic game = new GameLogic();
		//Puis on la lance
		game.startGame();

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
