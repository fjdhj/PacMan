package fr.fjdhj.PacMan.gameLogic.IA;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;

public class BlinkyIA extends IA{


	
	public BlinkyIA(Ghost Blinky, PacMan player, boolean[][] matrice) {
		setGhost(Blinky);
		setPlayer(player);
		setMatrice(matrice);

	}

	@Override
	public void mainIA() {
		
		//On r�cup�re coordon�e de PacMan et on convertie en coordon�e tuile
		int destx = (int) (player.getXPos().get()/14);	
		int desty = (int) (player.getYPos().get()/14);
		
		//On r�cup�re coordon�e de Blinky et on convertie en coordon�e tuile
		int x = (int) (ghost.getXPos().get()/14);	
		int y = (int) (ghost.getYPos().get()/14);
		//On appel notre fonction afin de r�cup�rer les coordon�es
		Direction dir = regularPathFiding(matrice, destx, desty, x, y, ghost.getDirection().get());
		System.out.println(dir);
		//Si on r�cup�re pas rien, on le mets a jour dans la variable newDirection (et pas direction pour pas tout bug)
		if(dir != null) {
			ghost.setNewDirection(dir);
		}
	}
	
	


}
