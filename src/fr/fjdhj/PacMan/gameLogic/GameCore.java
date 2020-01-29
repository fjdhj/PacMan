package fr.fjdhj.PacMan.gameLogic;

import java.util.List;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;
import fr.fjdhj.PacMan.gameLogic.IA.BlinkyIA;
import fr.fjdhj.PacMan.gameLogic.IA.IA;
import fr.fjdhj.PacMan.view.PlayGameMapping;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameCore {
	/*
	 * Valeur par default du niveau
	 */
	private int startLife;
	private Direction startDirection;
	private PacMan player;
	private List<Wall> wall;
	public PlayGameMapping controlleur;
	
	//Note : créer avec la fonction addGhost
	private Ghost Blinky;
	
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
		Blinky = new Ghost(0,0,Direction.LEFT);
		
		//On charge notre jeu et on récupère le controlleur
		controlleur = (PlayGameMapping) MainClass.initScene(MainClass.class.getResource("view/playGame.fxml"));
		//On innitialise notre KeyListeneur
		initKeyListener();	
		
		//On fournie a notre controlleur nos objet et on récupère les murs
		wall = controlleur.setEntityAndGetWall(player, Blinky);
		
		//On créer nos Phantome et leur IA corespondante via la fonction
		//addGhost();
		
		//On met a jour nos coordonées en fonction de l'image view
		player.updatePosition(controlleur.getImageViewPlayer());
		Blinky.updatePosition(controlleur.getImageViewBlinky());
		
		//On ajoute nos listeneur pour l'interface graphique
		controlleur.addListener();
		
		//La carte se divise en tuile de 14x14, pour l'IA, il faut convertire les murs en tuils accéssible ou non
		boolean[][] matrice = IA.createMatrice(wall, 28, 31);
		
		//Blinky : le phantome rouge
		BlinkyIA blinkyIA = new BlinkyIA(Blinky, player, matrice);
		Blinky.setIA(blinkyIA);
		
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

	public Ghost getBlinky() {
		return Blinky;
	}
	
	public GameLogic getGameLogic() {return gameLogic;}

	

}
