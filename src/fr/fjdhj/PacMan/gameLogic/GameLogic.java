package fr.fjdhj.PacMan.gameLogic;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import fr.fjdhj.PacMan.view.MainMenuMapping;
import fr.fjdhj.PacMan.view.PlayGameMapping;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameLogic {
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
	
	
	/*
	 * -----------------------------------------------------------------------
	 * Constructeur
	 * -----------------------------------------------------------------------
	 */
	
	/**
	 * Constructeur par défault
	 * @param startX
	 * @param startY
	 * @param startLife
	 * @param startPoint
	 * @param player
	 */
	public GameLogic(int startX, int startY, int startLife, int startPoint, Direction startDirection, PacMan player) {
		setStartX(startX);
		setStartY(startY);
		setStartLife(startLife);
		setStartPoint(startPoint);
		setStartDirection(startDirection);
		setPlayer(player);
	}
	
	/**
	 * Par défault :- startX et startY son déterminé en fonction du niveau
	 * 				- startLife vaut 3
	 * 				- startDirection vaut LEFT
	 * 				- l'objet PacMan (player) est crée directement avec ces informations
	 */
	public GameLogic() {
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
	 * Démarre la partie
	 */	
	public void startGame() {
		//On charge notre jeu et on récupère le controlleur
		controlleur = (PlayGameMapping) MainClass.initScene(MainMenuMapping.class.getResource("playGame.fxml"));
		//On innitialise notre KeyListeneur
		initKeyListener();	
		//On fournie a notre controlleur nos objet
		controlleur.setEntity(player);
		//On créer un Thread pour ne pas bloquer l'affichage
		Thread t = new Thread(new Runnable() {
			
			//Ici la boucle est pour le teste du déplacement de PacMan, c'est OK
			@Override
			public void run() {
				for(int i = 0; i< 100; i++) {
					player.moove();
					try {  
						Thread.sleep(4);
					}catch (InterruptedException e){ 
						e.printStackTrace();
					}
				}
				
			}
			
		});
		t.start();

	}
	
	
	
	/*
	 * --------------------------------------------------------------------------
	 * FONCTION PRIVEE
	 * --------------------------------------------------------------------------
	 */
	
	private void initKeyListener() {
		//On créer un lsiteneur qui va écouter notre clavier
		controlleur.pan.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				System.out.println(arg0.getCode() + "Vien d'être pressé");
				
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
