package fr.fjdhj.PacMan.gameLogic;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import fr.fjdhj.PacMan.view.MainMenuMapping;
import fr.fjdhj.PacMan.view.PlayGameMapping;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameCore {
	/*
	 * Valeur par default du niveau
	 */
	private int startX;
	private int startY;
	private int startLife;
	private int startPoint;
	private Direction startDirection;
	private PacMan player;
	private PlayGameMapping controlleur;
	
	//Note : pour pouvoir y acc�der depuis notre Thread dans la fonction startGame()
	private GameCore gameCore = this;
	private GameLogic gameLogic;
	
	/*
	 * -----------------------------------------------------------------------
	 * Constructeur
	 * -----------------------------------------------------------------------
	 */
	
	/**
	 * Constructeur par d�fault
	 * @param startX
	 * @param startY
	 * @param startLife
	 * @param startPoint
	 * @param player
	 */
	public GameCore(int startX, int startY, int startLife, int startPoint, Direction startDirection, PacMan player) {
		setStartX(startX);
		setStartY(startY);
		setStartLife(startLife);
		setStartPoint(startPoint);
		setStartDirection(startDirection);
		setPlayer(player);
	}
	
	/**
	 * Par d�fault :- startX et startY son d�termin� en fonction du niveau
	 * 				- startLife vaut 3
	 * 				- startDirection vaut LEFT
	 * 				- l'objet PacMan (player) est cr�e directement avec ces informations
	 */
	public GameCore() {
		setStartX(0);
		setStartY(0);
		setStartLife(0);
		setStartPoint(0);
		setStartDirection(Direction.LEFT);		
		setPlayer(new PacMan(300,600,3,0, Direction.LEFT));
	}
	
	/*
	 * --------------------------------------------------------------------------
	 * FONCTION PRINCIPALE
	 * --------------------------------------------------------------------------
	 */
	
	/**
	 * D�marre la partie
	 */	
	public void startGame() {
		//On charge notre jeu et on r�cup�re le controlleur
		controlleur = (PlayGameMapping) MainClass.initScene(MainMenuMapping.class.getResource("playGame.fxml"));
		//On innitialise notre KeyListeneur
		initKeyListener();	
		//On fournie a notre controlleur nos objet
		controlleur.setEntity(player);
		//On ajoute nos listeneur pour l'interface graphique
		controlleur.addListener();
		
		//On appel notre GameCore
		GameLogic gameLogic = new GameLogic(gameCore);
		//On d�mare le Thread principale
		gameLogic.start();
		

	}
	
	/**
	 * Arrete la pertie
	 */
	public void stopGame() {
		gameLogic.interrupt();
	}
	
	/*
	 * --------------------------------------------------------------------------
	 * FONCTION PRIVEE
	 * --------------------------------------------------------------------------
	 */
	
	private void initKeyListener() {
		//On cr�er un listeneur qui va �couter notre clavier
		controlleur.pan.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings("incomplete-switch")
			public void handle(KeyEvent arg0) {
				switch (arg0.getCode()) {
				case LEFT:
					player.setDirection(Direction.LEFT);
					break;
				case RIGHT:
					player.setDirection(Direction.RIGHT);
					break;
				case UP:
					player.setDirection(Direction.UP);
					break;
				case DOWN:
					player.setDirection(Direction.DOWN);
				}
				
			}});
	}
	
	
	
	/*
	 * ------------------------------------------------------------
	 * Getteur/Setteur
	 * ------------------------------------------------------------
	 */
	public int getStartX() {return startX;}
	public void setStartX(int startX) {this.startX = startX;}

	public int getStartY() {return startY;}
	public void setStartY(int startY) {this.startY = startY;}

	public int getStartLife() {return startLife;}
	public void setStartLife(int startLife) {this.startLife = startLife;}

	public int getStartPoint() {return startPoint;}
	public void setStartPoint(int startPoint) {this.startPoint = startPoint;}
	
	public Direction getStartDirection() {return startDirection;}
	public void setStartDirection(Direction startDirection) {this.startDirection = startDirection;}

	public PacMan getPlayer() {return player;}
	public void setPlayer(PacMan player) {this.player = player;}
	

}
