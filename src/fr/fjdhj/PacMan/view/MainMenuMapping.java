package fr.fjdhj.PacMan.view;

import java.io.File;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.GameCore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainMenuMapping {
	
	@FXML
	private Button playButton;
	@FXML
	private Button settingButton;
	@FXML
	private Button exitButton;
	@FXML
	private MenuItem credit;
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private GameCore game;
	
	public MainMenuMapping() {}
	
	@FXML
	private void initialize() {
		media = new Media(new File("sound/mainMenu/PacMan_Theme_Remix_By_arsenic1987.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	
	
	/*
	 * Lance la partie
	 */
	@FXML
	public void playGame() {
		//On charge notre partie
		game = new GameCore();
		//Puis on la lance
		game.startGame();

	}
	
	/*
	 * Ouvre les parametre
	 */
	@FXML
	public void openSetting() {
		
	}
	
	@FXML
	public void openCredit() {
		MainClass.creditDialog();
	}
	
	
	
	/*
	 * Ferme l'application
	 */
	@FXML
	public void exit(){
		game.stopGame();
		System.exit(0);
	}
}
