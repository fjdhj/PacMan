package fr.fjdhj.PacMan.gameLogic.IA;

import java.util.List;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;

public class IA {
	
	protected Ghost ghost;
	protected PacMan player;
	protected Direction PacManDirection;
	protected List<Wall> listWall;
	
	public IA(){}
	
	public void updatePacManDirection() {
		setPacManDirection(player.getDirection().get());
	}

	public Ghost getGhost() {return ghost;}
	public void setGhost(Ghost ghost) {this.ghost = ghost;}

	public Direction getPacManDirection() {return PacManDirection;}
	public void setPacManDirection(Direction pacManDirection) {PacManDirection = pacManDirection;}

	public PacMan getPlayer() {return player;}
	public void setPlayer(PacMan player) {this.player = player;}

	public List<Wall> getListWall() {return listWall;}
	public void setListWall(List<Wall> listWall) {this.listWall = listWall;}
}
