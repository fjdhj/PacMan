package fr.fjdhj.PacMan.gameLogic.PacMan;

import fr.fjdhj.PacMan.gameLogic.Direction;

public class PacMan {
	
	/**
	 * La posiotion x du PacMan. Elle peut être modifié
	 * @see PacMan#getXPos()
	 * @see PacMan#setXPos(int)
	 */
	private int xPos;
	private int yPos;
	private int life;
	private int point;
	private Direction direction;
	
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
	
	public PacMan(int xPos, int yPos, int life, int point, Direction direction) {
		setXPos(xPos);
		setYPos(yPos);
		setLife(life);
		setPoint(point);
		setDirection(direction);
	}
	
	public PacMan() {
		setXPos(0);
		setYPos(0);
		setLife(0);
		setPoint(0);
		setDirection(Direction.LEFT);
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
		switch(direction){
			case RIGHT:
				xPos++;
				break;
			
			case LEFT:
				xPos--;
				break;
				
			case UP:
				yPos--;
				break;
				
			case DOWN:
				yPos++;
				break;
		}	
	}
	
	/**
	 * Ajoute une/des vie(s) a PacMan si nbr>0
	 * Retire une/des vie(s) a PacMan si nbr<0
	 * @param nbr : quantité à ajouter/retirer
	 */
	public void changeLife(int nbr) {
		life = life + nbr;
	}
	
	/**
	 * Ajoute des points a PacMan
	 * NOTE: Si nbr<0 alors permet d'en retirer
	 * @param nbr : Nombre de point à ajouter
	 */
	public void addPoint(int nbr) {
		point=point+nbr;
	}
	
	
	
	
	/*
	 * -------------------------------------------------------------------------------------
	 * Ensemble getteur et setteur
	 * -------------------------------------------------------------------------------------
	 */
	
	
	/**
	 * @return La position x du PacMan
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * @param xPos La nouvelle position x du PacMan
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * @return La position y du PacMan
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * @param yPos La nouvelle position y du PacMan
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * @return Recupere le nombre de point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @param point le nouveau nombre de point point
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	/**
	 * @return Le nombre de vie restante
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life Le nouveau nombre de vie
	 */
	public void setLife(int life) {
		this.life = life;
	}
	
	/**
	 * 
	 * @return La direction actuel
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @param direction La nouvelle direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	

}
