package fr.fjdhj.PacMan.gameLogic.Entity;

import fr.fjdhj.PacMan.gameLogic.Direction;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PacMan extends Entity{
	


	private IntegerProperty life = new SimpleIntegerProperty();    //<----> int
	private IntegerProperty pointMult = new SimpleIntegerProperty();    //<----> int, permet de savoir ou on en est entre point et vie
	private DoubleProperty point = new SimpleDoubleProperty();     //<----> double

	
	/*---------------------------------------------------------------------------
	 * Constructeur
	 *-----------------------------------------------------------------------------
	 */
	
	/**
	 * Constructeur par default
	 * 
	 * @param xPos Les coordon�es x du PacMan
	 * @param yPos Les coordon�es y du PacMan
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
		this.newDirection.set(null);
	}
	
	public PacMan() {
		this.xPos.set(0);
		this.yPos.set(0);
		this.life.set(0);
		this.point.set(0);
		this.direction.set(Direction.LEFT);
		this.newDirection.set(null);
	}

	
	/*--------------------------------------------------------------------------------
	 * Fonction supl�mentaire
	 *--------------------------------------------------------------------------------
	 */
	
	/**
	 * Ajoute une/des vie(s) a PacMan si nbr>0
	 * Retire une/des vie(s) a PacMan si nbr<0
	 * @param nbr : quantit� � ajouter/retirer
	 */
	public void addLife(int nbr) {
		life.set(life.get()+nbr);
	}
	
	/**
	 * Ajoute des points a PacMan
	 * NOTE: Si nbr<0 alors permet d'en retirer
	 * @param nbr : Nombre de point � ajouter
	 */
	public void addPoint(int nbr) {
		point.set(point.get() +nbr);
	}
	
	

	
	
	/*
	 * -------------------------------------------------------------------------------------
	 * Ensemble getteur et setteur
	 * -------------------------------------------------------------------------------------
	 */
	
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

	public IntegerProperty getPointMult() {
		return pointMult;
	}

	public int getPointMultValue() {
		return pointMult.get();
	}
	
	public void setPointMult(IntegerProperty pointMult) {
		this.pointMult = pointMult;
	}	
	
	public void addPointMult(int n) {
		pointMult.set(pointMult.get()+n);
	}

}
