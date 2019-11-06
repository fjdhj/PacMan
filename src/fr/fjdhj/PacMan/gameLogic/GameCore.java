package fr.fjdhj.PacMan.gameLogic;

import java.util.List;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;
import fr.fjdhj.PacMan.gameLogic.IA.BlinkyIA;
import fr.fjdhj.PacMan.view.MainMenuMapping;
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
	public PlayGameMapping controlleur;
	
	//Note : cr�er avec la fonction addGhost
	private Ghost Blinky;
	
	//Note : pour pouvoir y acc�der depuis notre Thread dans la fonction startGame()
	private GameCore gameCore = this;
	private GameLogic gameLogic;
	
	//Variable pour nos fonction g�rant les fantomes
	private boolean IaTurn = true;
	
	/*
	 * -----------------------------------------------------------------------
	 * Constructeur
	 * -----------------------------------------------------------------------
	 */
	
	/**
	 * Constructeur par d�fault
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
	 * Par d�fault :- startLife vaut 3
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
	 * D�marre la partie
	 */	
	public void startGame() {
		//On charge notre jeu et on r�cup�re le controlleur
		controlleur = (PlayGameMapping) MainClass.initScene(MainMenuMapping.class.getResource("playGame.fxml"));
		//On innitialise notre KeyListeneur
		initKeyListener();	
		
		//On cr�er nos Phantome et leur IA corespondante via la fonction
		addGhost();
		
		//On fournie a notre controlleur nos objet et on r�cup�re les murs
		List<Wall> wall = controlleur.setEntityAndGetWall(player, Blinky);
		
		//On met a jour nos coordon�es en fonction de l'image view
		player.updatePosition(controlleur.getImageViewPlayer());
		Blinky.updatePosition(controlleur.getImageViewBlinky());
		
		//On ajoute nos listeneur pour l'interface graphique
		controlleur.addListener();
		
		//On appel notre GameCore
		GameLogic gameLogic = new GameLogic(gameCore, wall);
		//On d�mare le Thread principale
		gameLogic.start();
		

	}
	/**
	 * Ajoute les Phantomes et leur IA
	 */
	private void addGhost() {
		//Blinky : le phantome rouge
		Blinky = new Ghost();
		BlinkyIA blinkyIA = new BlinkyIA(Blinky, player);
		
		IaTurn = true;
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(IaTurn) {
					blinkyIA.folow();
				}
				
				
			}
			
		});
		t.start();
		
	}
	
	private void stopGhostIA() {
		//Arrete le Thread dans la fonctoion addGhost
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
		//On cr�er un listeneur qui va �couter notre clavier
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
