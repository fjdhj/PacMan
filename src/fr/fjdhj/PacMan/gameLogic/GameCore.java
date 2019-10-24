package fr.fjdhj.PacMan.gameLogic;

import java.util.List;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import fr.fjdhj.PacMan.view.MainMenuMapping;
import fr.fjdhj.PacMan.view.PlayGameMapping;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameCore {
	/*
	 * Valeur par default du niveau
	 */
	private int startLife;
	private Direction startDirection;
	private PacMan player;
	public PlayGameMapping controlleur;
	
	//Note : pour pouvoir y accéder depuis notre Thread dans la fonction startGame()
	private GameCore gameCore = this;
	private GameLogic gameLogic;
	
	/*
	 * -----------------------------------------------------------------------
	 * Constructeur
	 * -----------------------------------------------------------------------
	 */
	
	/**
	 * Constructeur par défault
	 * @param startLife
	 * @param Direction
	 * @param player
	 */
	public GameCore(int startLife, Direction startDirection, PacMan player) {
		setStartLife(startLife);
		setStartDirection(startDirection);
		setPlayer(player);
	}
	
	/**
	 * Par défault :- startLife vaut 3
	 * 				- startDirection vaut LEFT
	 */
	public GameCore() {
		setStartLife(0);
		setStartDirection(Direction.LEFT);		
		setPlayer(new PacMan(0,0,0,0, Direction.LEFT));
	}
	
	/*
	 * --------------------------------------------------------------------------
	 * FONCTION PRINCIPALE
	 * --------------------------------------------------------------------------
	 */
	
	/**
	 * Démarre la partie
	 */	
	public void startGame() {
		//On charge notre jeu et on récupère le controlleur
		controlleur = (PlayGameMapping) MainClass.initScene(MainMenuMapping.class.getResource("playGame.fxml"));
		//On innitialise notre KeyListeneur
		initKeyListener();	
		//On fournie a notre controlleur nos objet et on récupère les murs
		List<Wall> wall = controlleur.setEntityAndGetWall(player);
		//On met a jour nos coordonées en fonction de l'image view
		player.setXPos(controlleur.getImageViewPlayer().getLayoutX()+12);
		player.setYPos(controlleur.getImageViewPlayer().getLayoutY()+12);
		//On ajoute nos listeneur pour l'interface graphique
		controlleur.addListener();
		
		//On appel notre GameCore
		GameLogic gameLogic = new GameLogic(gameCore, wall);
		//On démare le Thread principale
		gameLogic.start();
		

	}
	
	/**
	 * Arrete la pertie
	 */
	public void stopGame() {
		//gameLogic.interrupt();
	}
	
	/*
	 * --------------------------------------------------------------------------
	 * FONCTION PRIVEE
	 * --------------------------------------------------------------------------
	 */
	
	private void initKeyListener() {
		//On créer un listeneur qui va écouter notre clavier
		controlleur.pan.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			public void handle(KeyEvent arg0) {
				switch (arg0.getCode()) {
				case LEFT:
					player.setNewDirection(Direction.LEFT);
					break;
				case RIGHT:
					player.setNewDirection(Direction.RIGHT);
					break;
				case UP:
					player.setNewDirection(Direction.UP);
					break;
				case DOWN:
					player.setNewDirection(Direction.DOWN);
					break;
				}
				
			}});
	}
	
	
	
	/*
	 * ------------------------------------------------------------
	 * Getteur/Setteur
	 * ------------------------------------------------------------
	 */

	public int getStartLife() {return startLife;}
	public void setStartLife(int startLife) {this.startLife = startLife;}
	
	public Direction getStartDirection() {return startDirection;}
	public void setStartDirection(Direction startDirection) {this.startDirection = startDirection;}

	public PacMan getPlayer() {return player;}
	public void setPlayer(PacMan player) {this.player = player;}
	

}
