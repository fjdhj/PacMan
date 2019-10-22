package fr.fjdhj.PacMan.gameLogic;


/*
 *  Classe qui va gérer le jeu : avancement, si il y a un mur, un phantom,
 *  des points, une victoire
 */

public class GameLogic extends Thread{

	private GameCore gameCore;
	private boolean run = true;

	public GameLogic(GameCore gameCore) {
		this.gameCore = gameCore;
	}
	
	 @Override
	public void run() {
		//Ici on teste si la partie est fini
		 while(run) {
			 //On avance de 10 -> soit une unité (meusure utilisé car les murs font 10 d'épaisseur et que PacMan fait 10 de rayon
			for(int i = 0; i<10; i++) {
				 try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gameCore.getPlayer().moove();
			}
		}
	}
	 
	 
	 
	 private boolean canMoove() {

		 
		 
		return false;
	 }
}
