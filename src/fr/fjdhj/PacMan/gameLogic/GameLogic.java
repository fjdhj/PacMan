package fr.fjdhj.PacMan.gameLogic;

import java.util.List;
import java.util.Timer;

import fr.fjdhj.PacMan.gameLogic.IA.IAmod;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/*
 *  Classe qui va gérer le jeu : avancement, si il y a un mur, un phantom,
 *  des points, une victoire
 */

public class GameLogic extends Thread{

	private GameCore gameCore;
	private List<Wall> wall;
	private boolean run = true;
	
	public boolean blinkyBoolTimer = true;
	public long blinkyTimerStart = 0;
	public long blinkyTimerEnd = 0;
	public Timer blinkyTimer;

	
	public GameLogic(GameCore gameCore, List<Wall> wall) {
		this.gameCore = gameCore;
		this.wall = wall;
	}
	
	@Override 
	public void run() {		
		setTimer();
		
		//On déplace de manière parallèle les fantome car il ne vont aps a la même vitesse
		ghostGameLogic();
		
				//Ici on teste si la partie est fini
				 while(run) {
					 //Pause pour limiter la vitesse
					 try {
						 Thread.sleep(7);
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
					 if((((gameCore.getPlayer().getXPos().get()-7)%14 == 0) || ((gameCore.getPlayer().getXPos().get()-7)%14 == 0)) && 
					 (((gameCore.getPlayer().getYPos().get()-7)%14 ==0) ||((gameCore.getPlayer().getYPos().get()-7)%14 ==0))) {
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
	
	
	public void ghostGameLogic() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				blinkyTimer = gameCore.getBlinky().getIA().timer(gameCore.getBlinky().getIA().TIME_WAVE_1[gameCore.getBlinky().getIA().getRound()]);
				while(run) {
						
					/*if(!gameCore.getBlinky().getIA().getIAmod().equals(IAmod.FRIGHTNED)) {
					//On démare le timer pour les IA
						if(blinkyTimer) {
							start = System.currentTimeMillis();
							timer = gameCore.getBlinky().getIA().timer();
							blinkyTimer = false;
						}
					}else {
						
					}*/
					//Vitesse du jeu
					try {
						Thread.sleep(7);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(gameCore.getBlinky().getNewDirection().get()!=null) {
						gameCore.getBlinky().canTurn(wall);
					}
					
					 //Si blinky ne va dans le mur
					 if(!gameCore.getBlinky().goInWall(wall)) {
						 gameCore.getBlinky().moove();
						 if((int)(gameCore.getBlinky().getXPos().get()-gameCore.getBlinky().getDirection().get().getModifier())/14 != (int)gameCore.getBlinky().getXPos().get()/14
							|| (int)(gameCore.getBlinky().getYPos().get()-gameCore.getBlinky().getDirection().get().getModifier())/14 != (int)gameCore.getBlinky().getYPos().get()/14)
						 
							 gameCore.getBlinky().getIA().mainIA();
					 }
				}
				
			}
			
		});
	t.start();
	
	}
	 
	
	public void setTimer() {
		//BLINKY JE TE CHOISIE
		gameCore.getBlinky().getIA().getIAmod().addListener(new ChangeListener<IAmod>() {
			@Override
			public void changed(ObservableValue<? extends IAmod> observable, IAmod oldValue, IAmod newValue) {
				//Si on change de mod, soit le timer est fini, soit on passe en mode fuite
				//Si on passe en mod fuite
				if(newValue.equals(IAmod.FRIGHTNED)) {
					blinkyTimerEnd = System.currentTimeMillis();
					blinkyTimer.cancel();
					blinkyTimer.purge();
					//Stock le temps du timer
					long time = gameCore.getBlinky().getIA().FRIGHTNED_TIME[gameCore.level];
					//Si il != -1 car il existe une niveau qui vaut -1
					if(time!=-1) {
						blinkyTimer = gameCore.getBlinky().getIA().timer(time);
					
					//Si c'est -1, on ne fait faire donc sue demi tour au fantome
					}else{
						switch(gameCore.getBlinky().getDirection().get()) {
						case DOWN:
							gameCore.getBlinky().getDirection().set(Direction.UP);
							break;
						case LEFT:
							gameCore.getBlinky().getDirection().set(Direction.RIGHT);
							break;
						case RIGHT:
							gameCore.getBlinky().getDirection().set(Direction.LEFT);
							break;
						case UP:
							gameCore.getBlinky().getDirection().set(Direction.DOWN);
							break;							
						}}
							
				//Si on ne passe pas en mode fuite, dans le cas de Blinky, on peut passer en mode Elroy, 
				//au quel cas, on ne fait pas demi tour, on ne fait rien
				//Mais si ce n'est pas le cas :
				}else {
					if(!newValue.equals(IAmod.ELROY1) || !newValue.equals(IAmod.ELROY2)) {
						//Pour changer de mod, soit on redémare une partie, soit un timer est fini
						if(!gameCore.startLevel) {
							blinkyTimerStart = System.currentTimeMillis();
							//Si on est au niveau 1
							if(gameCore.level==1) {
								long time = gameCore.getBlinky().getIA().TIME_WAVE_1[gameCore.getBlinky().getIA().getRound()];
								blinkyTimer = gameCore.getBlinky().getIA().timer(time);
							}
						}
					}
				}
				
			}});
	}
}
