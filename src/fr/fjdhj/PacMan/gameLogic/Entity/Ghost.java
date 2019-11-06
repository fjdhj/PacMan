package fr.fjdhj.PacMan.gameLogic.Entity;

import java.util.List;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import fr.fjdhj.PacMan.gameLogic.IA.IA;

public class Ghost extends Entity{

	private IA ia;
	private double simulX, simulY;
	private List<Direction> listDirection;
	
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
	
	public boolean isAnIntersect(List<Wall> listWall) {
		double x = simulX,y = simulY;

		
		if(direction.get().equals(Direction.LEFT) || direction.get().equals(Direction.RIGHT)) {
			//Si on se déplace a l'horizontale
			for(Wall wall : listWall) {
				//On veut voir si on peut faire un changement a la verticale
				if(!(((wall.getYmin()<=(xPos.get()+15) && (xPos.get()+15)<=wall.getYmax())||
				  (wall.getYmin()<=(xPos.get()-15) && (xPos.get()-15 <=wall.getYmax()))) &&
				  ((wall.getXmin()<=(yPos.get()-13) && (yPos.get()-13)<=wall.getXmax()) ||
				  (wall.getXmin()<=(yPos.get()+13) && (yPos.get()+13)<=wall.getXmax()) ||
				  (wall.getXmin()<=yPos.get() && yPos.get()<=wall.getXmax())))){ 
						//Il n'y a pas de mur
						return true;
				}
			}
		}else {
			//On se déplace a la verticale
			for(Wall wall : listWall) {
				if(!(((wall.getXmin()<=(xPos.get()+15) && (xPos.get()+15)<=wall.getXmax())||
				  (wall.getXmin()<=(xPos.get()-15) && (xPos.get()-15 <=wall.getXmax()))) &&
				  ((wall.getYmin()<=(yPos.get()-13) && (yPos.get()-13)<=wall.getYmax()) ||
				  (wall.getYmin()<=(yPos.get()+13) && (yPos.get()+13)<=wall.getYmax()) ||
				  (wall.getYmin()<=yPos.get() && yPos.get()<=wall.getYmax())))){ 
						//Il n'y a pas de mur
						return true;
				}
				
			}
		}

		return false;
		
	}
	
	public IA getIA() {return ia;}
	public void setIA(IA ia) {this.ia = ia;}

	public double getSimulX() {return simulX;}
	public void setSimulX(double simulX) {this.simulX = simulX;}

	public double getSimulY() {return simulY;}
	public void setSimulY(double simulY) {this.simulY = simulY;}

	public List<Direction> getListDirection() {return listDirection;}
	public void setListDirection(List<Direction> listDirection) {this.listDirection = listDirection;}
	
	
}
