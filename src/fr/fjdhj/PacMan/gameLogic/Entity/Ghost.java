package fr.fjdhj.PacMan.gameLogic.Entity;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.IA.IA;

public class Ghost extends Entity{

	private IA ia;
	
	public Ghost(double xPos, double yPos, Direction direction) {
		this.xPos.set(xPos);
		this.yPos.set(yPos);
		this.direction.set(direction);
		this.newDirection.set(null);
	}
	
	public Ghost() {
		this.xPos.set(0);
		this.yPos.set(0);
		this.direction.set(Direction.LEFT);
		this.newDirection.set(null);
	}

	public IA getIA() {return ia;}
	public void setIA(IA ia) {this.ia = ia;}

	
}
