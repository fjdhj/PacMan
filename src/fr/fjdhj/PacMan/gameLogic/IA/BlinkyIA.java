package fr.fjdhj.PacMan.gameLogic.IA;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;

public class BlinkyIA extends IA{

	private final int CASEX = 25;
	private final int CASEY = -3;
	
	public BlinkyIA(Ghost Blinky, PacMan player, boolean[][] matrice, int level) {
		setGhost(Blinky);
		setPlayer(player);
		setMatrice(matrice);
		setLevel(level);
		
	}

	@Override
	public void mainIA() {
		
		//On récupère coordonée de Blinky et on convertie en coordonée tuile
		int x = (int) (ghost.getXPos().get()/14);	
		int y = (int) (ghost.getYPos().get()/14);
		
		int destx;
		int desty;
		
		//Si on poursuit PacMan
		if(mod.get().equals(IAmod.CHASE) || mod.get().equals(IAmod.ELROY1) || mod.get().equals(IAmod.ELROY2)) {
			//On récupère coordonée de PacMan et on convertie en coordonée tuile
			destx = (int) (player.getXPos().get()/14);	
			desty = (int) (player.getYPos().get()/14);
			
		//On poursuit alors la case
		}else {
			destx = CASEX;
			desty = CASEY;
		}
		
		//On appel notre fonction afin de récupérer les coordonées
		Direction dir = regularPathFiding(matrice, destx, desty, x, y, ghost.getDirection().get());
		//Si on récupère pas rien, on le mets a jour dans la variable newDirection (et pas direction pour pas tout bug)
		if(dir != null) {
			ghost.setNewDirection(dir);
		}
	}
	


}
