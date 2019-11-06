package fr.fjdhj.PacMan.gameLogic.IA;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;

public class BlinkyIA extends IA{


	
	public BlinkyIA(Ghost Blinky, PacMan player) {
		setGhost(Blinky);
		setPlayer(player);
		//Pour que la derniérer direction de PacMan ne soit pas le néan
		updatePacManDirection();
	}

	public void folow() {
		//Si PacMan change de direction
		if(!PacManDirection.equals(player.getDirection().get())) {
			//On mets a jour la direction enregistré de PacMan
			updatePacManDirection();
			
			/*
			 * On détermine dans quelle partie se situe PacMan
			 */
			Direction horizontal, verticale;
			double horizontalAxe = ghost.getXPos().get()-player.getXPos().get();
			double verticalAxe = ghost.getYPos().get()-player.getYPos().get();
			
			System.out.println(ghost.getXPos().get() + " " +player.getXPos().get());
			
			//Verification axe horizontal
			if(horizontalAxe>0) { 
				//Alors PacMan se trouve a gauche du phantome
				horizontal = Direction.LEFT;
			}else if(horizontalAxe<0){
				//Alors PacMan se trouve a droite du phantome
				horizontal = Direction.RIGHT;
				//On veut que horizontalAxe soit positive
				horizontalAxe = Math.abs(horizontalAxe);
			}else {
				//Les deux entité sont aligné horizontalement
				horizontal = null;
			}
			
			//Verifiaction axe vertical
			if(verticalAxe>0) { 
				//Alors PacMan se trouve au dessu du phantome
				verticale = Direction.UP;
			}else if(verticalAxe<0){
				//Alors PacMan se trouve en dessous du phantome
				verticale = Direction.DOWN;
				//On veut que verticalAxe soit positive
				verticalAxe = Math.abs(verticalAxe);
			}else {
				//Les deux entité sont aligné verticalement
				verticale = null;
			}
			
			System.out.println(horizontalAxe + "   " + verticalAxe +"     " + verticale +" " +horizontal);
			
			/*
			 * Prise de decision de la part de l'IA
			 */
			/*if(ghost.isAnIntersect(listWall)) {
				
			}*/
		}
	}
	
	


}
