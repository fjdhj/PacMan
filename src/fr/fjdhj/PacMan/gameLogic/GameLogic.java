package fr.fjdhj.PacMan.gameLogic;

import java.util.List;

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
			 try {
				 Thread.sleep(4);
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
			 }else {
				 System.out.println("Go in wall");
			 }
			
		}
	}
	 
	 
}
