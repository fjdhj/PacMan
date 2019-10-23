package fr.fjdhj.PacMan.gameLogic.PacMan;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PacMan {
	

	private DoubleProperty xPos = new SimpleDoubleProperty();      //<----> double
	private DoubleProperty yPos = new SimpleDoubleProperty();      //<----> double
	private IntegerProperty life = new SimpleIntegerProperty();    //<----> int
	private DoubleProperty point = new SimpleDoubleProperty();     //<----> double
	private ObjectProperty<Direction> direction = new SimpleObjectProperty<>(); //<----> Direction
	
	/*---------------------------------------------------------------------------
	 * Constructeur
	 *-----------------------------------------------------------------------------
	 */
	
	/**
	 * Constructeur par default
	 * 
	 * @param xPos Les coordonées x du PacMan
	 * @param yPos Les coordonées y du PacMan
	 * @param life Le nombre de vie restante
	 * @param point Le nombre de point obtenu
	 * @param direction : la direction
	 * 
	 */
	
	public PacMan(double xPos, double yPos, int life, double point, Direction direction) {
		this.xPos.set(xPos);
		this.yPos.set(yPos);
		this.life.set(life);
		this.point.set(point);
		this.direction.set(direction);
	}
	
	public PacMan() {
		this.xPos.set(0);
		this.yPos.set(0);
		this.life.set(0);
		this.point.set(0);
		this.direction.set(Direction.LEFT);
	}

	
	/*--------------------------------------------------------------------------------
	 * Fonction suplémentaire
	 *--------------------------------------------------------------------------------
	 */
	
	/**
	 * Modifie les coordonées de PacMan en fonction
	 * de la position realtive et de la direction
	 * 
	 */
	
	public void moove() {
		switch(direction.get()){
			case RIGHT:
				xPos.set(xPos.get() + 1);
				break;
			
			case LEFT:
				xPos.set(xPos.get() - 1);
				break;
				
			case UP:
				yPos.set(yPos.get() - 1);
				break;
				
			case DOWN:
				yPos.set(yPos.getValue() + 1);
				break;
		}	
	}
	
	/**
	 * Ajoute une/des vie(s) a PacMan si nbr>0
	 * Retire une/des vie(s) a PacMan si nbr<0
	 * @param nbr : quantité à ajouter/retirer
	 */
	public void changeLife(int nbr) {
		life.add(nbr);
	}
	
	/**
	 * Ajoute des points a PacMan
	 * NOTE: Si nbr<0 alors permet d'en retirer
	 * @param nbr : Nombre de point à ajouter
	 */
	public void addPoint(int nbr) {
		point.add(nbr);
	}
	
	
	/**
	 * Regarde si PacMan est dans le mur
	 * @param wall : Le mur
	 * @return  true si il est , autrement retourn false
	 */
	public Boolean isInWall(Wall wall) {
		if(xPos.get() > wall.getXmin() && xPos.get() < wall.getXmin() && yPos.get() > wall.getYmin() && yPos.get() < wall.getYmax()) { //Si il va dans le mur
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	/*
	 * -------------------------------------------------------------------------------------
	 * Ensemble getteur et setteur
	 * -------------------------------------------------------------------------------------
	 */
	
	
	/**
	 * @return La position x du PacMan
	 */
	public DoubleProperty getXPos() {
		return xPos;
	}

	/**
	 * @param xPos La nouvelle position x du PacMan
	 */
	public void setXPos(DoubleProperty xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * @param xPos La nouvelle position x du PacMan
	 */
	public void setXPos(double xPos) {
		this.xPos.set(xPos);
	}

	/**
	 * @return La position y du PacMan
	 */
	public DoubleProperty getYPos() {
		return yPos;
	}

	/**
	 * @param yPos La nouvelle position y du PacMan
	 */
	public void setYPos(DoubleProperty yPos) {
		this.yPos = yPos;
	}

	/**
	 * @param yPos La nouvelle position y du PacMan
	 */
	public void setYPos(double yPos) {
		this.yPos.set(yPos);
	}

	
	/**
	 * @return Recupere le nombre de point
	 */
	public DoubleProperty getPoint() {
		return point;
	}

	/**
	 * @param point le nouveau nombre de point point
	 */
	public void setPoint(DoubleProperty point) {
		this.point = point;
	}

	/**
	 * @param point le nouveau nombre de point point
	 */
	public void setPoint(double point) {
		this.point.set(point);
	}
	
	/**
	 * @return Le nombre de vie restante
	 */
	public IntegerProperty getLife() {
		return life;
	}

	/**
	 * @param life Le nouveau nombre de vie
	 */
	public void setLife(IntegerProperty life) {
		this.life = life;
	}
	
	/**
	 * @param life Le nouveau nombre de vie
	 */
	public void setLife(int life) {
		this.life.set(life);;
	}
	
	/**
	 * 
	 * @return La direction actuel
	 */
	public ObjectProperty<Direction> getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @param direction La nouvelle direction
	 */
	public void setDirection(ObjectProperty<Direction> direction) {
		this.direction = direction;
	}
	
	/**
	 * 
	 * @param direction La nouvelle direction
	 */
	public void setDirection(Direction direction) {
		this.direction.set(direction);
	}
	
	

}
