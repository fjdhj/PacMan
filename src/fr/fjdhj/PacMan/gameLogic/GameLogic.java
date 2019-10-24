package fr.fjdhj.PacMan.gameLogic;

import java.util.List;

import javafx.application.Platform;

/*
 *  Classe qui va gérer le jeu : avancement, si il y a un mur, un phantom,
 *  des points, une victoire
 */

public class GameLogic extends Thread{

	private GameCore gameCore;
	private List<Wall> wall;
	private boolean run = true;
	
	public GameLogic(GameCore gameCore, List<Wall> wall) {
		this.gameCore = gameCore;
		this.wall = wall;
	}
	
	@Override 
	public void run() {		
		
				//Ici on teste si la partie est fini
				 while(run) {
					 //Pause pour limiter la vitesse
					 try {
						 Thread.sleep(5);
					 } catch (InterruptedException e) {
						 e.printStackTrace();
					 }
					 
					 //On regarde si on a une nouvelle direction et si le joueur peut tourner
					 if(gameCore.getPlayer().getNewDirection().get()!=null)	{
						 gameCore.getPlayer().canTurn(wall);
						 
					 }		 
					 //Si PacMan ne va pas dans le mur
					 if(!gameCore.getPlayer().goInWall(wall)) {
						 gameCore.getPlayer().moove();
					 }
					
					 //On regarde si PacMan gagne des points
					 if((((gameCore.getPlayer().getXPos().get()-10)%14 == 0) || ((gameCore.getPlayer().getXPos().get()-10)%14 == 0)) && 
					 (((gameCore.getPlayer().getYPos().get()-10)%14 ==0) ||((gameCore.getPlayer().getYPos().get()-10)%14 ==0))) {
						 //On regarde s'il n'a pas deja été mangé
						 
						 for(int i = 0; i<gameCore.controlleur.getPointCoords().size(); i++) {
							 if(gameCore.getPlayer().getXPos().get() == gameCore.controlleur.getPointCoords().get(i).getLayoutX()
									 && gameCore.getPlayer().getYPos().get() == gameCore.controlleur.getPointCoords().get(i).getLayoutY()) {
									 //On vérifie si ce n'est pas un gome (gros points)
									 if(gameCore.controlleur.getPointCoords().get(i).getRadius()==4) {
											 //PacMan peut manger des phantomes
											 
											 
										 }else {
											 //On ajoute les points a PacMan
											 Platform.runLater(new Runnable() {
												@Override
												public void run() {
													gameCore.getPlayer().addPoint(100);;
													
												}
												 
											 });
											 
										 }
										 //On le supprime : plus besoin
										 gameCore.controlleur.getPointCoords().get(i).setVisible(false);
										 gameCore.controlleur.getPointCoords().remove(gameCore.controlleur.getPointCoords().get(i));
									}
						 }

					 }
				}
				
			
			
		

	}
	 
	 
}
