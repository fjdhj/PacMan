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
		
		//On r�cup�re coordon�e de Blinky et on convertie en coordon�e tuile
		int x = (int) (ghost.getXPos().get()/14);	
		int y = (int) (ghost.getYPos().get()/14);
		
		int[] dest = getCoordDestination();
		
		//On appel notre fonction afin de r�cup�rer une direction
		Direction dir = regularPathFiding(matrice, dest[0], dest[1], x, y, ghost.getDirection().get());
		//Si on r�cup�re pas rien, on le mets a jour dans la variable newDirection (et pas direction pour pas tout bug)
		if(dir != null) {
			ghost.setNewDirection(dir);
		}
	}
	
	public int[] getCoordDestination() {
		int[] dest = new int[2];
				
		//Si on poursuit PacMan
		if(mod.get().equals(IAmod.CHASE) || mod.get().equals(IAmod.ELROY1) || mod.get().equals(IAmod.ELROY2)) {
			//On r�cup�re coordon�e de PacMan et on convertie en coordon�e tuile
			dest[0] = (int) (player.getXPos().get()/14);	
			dest[1] = (int) (player.getYPos().get()/14);
			
		//On poursuit alors la case
		}else {
			dest[0] = CASEX;
			dest[1] = CASEY;
		}
		return(dest);
	}

}
