package fr.fjdhj.PacMan.gameLogic;

import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author Theo
 */
public class Wall {
	
	private double Xmin;
	private double Xmax;
	private double Ymin;
	private double Ymax;
	private AnchorPane wall;
	
	
	/**
	 * Constructeur par d�fault
	 * Va calculer automatiquement les coordon�es Maximumes et minimumes
	 * @param pane : L'AnchorPane utilis�s pour faire le mur
	 */
	public Wall(AnchorPane wall) {
		setWall(wall);
		setXmin(wall.getLayoutX());
		setYmin(wall.getLayoutY());
		setXmax(wall.getLayoutX() + wall.getMaxWidth());
		setYmax(wall.getLayoutY() + wall.getMaxHeight());
	}
	
	/**
	 * Constructeur : utilise 4 points
	 * 
	 * @param Xmin : Les coordon�es minimales pour x
	 * @param Xmax : Les coordon�es maximales pour x
	 * @param Ymin : Les coordon�es minimales pour y
	 * @param Ymax : Les coordon�es maximales pour y
	 */
	public Wall(int Xmin, int Xmax, int Ymin, int Ymax) {
		setXmin(Xmin);
		setYmin(Ymin);
		setXmax(Xmax);
		setYmax(Ymax);
		setWall(null);
	}

	
	/**
	 * Regarde si les coordon�es sont dans le mur
	 * @param x : Les coordon�es x 
	 * @param y : Les coordon�es y
	 * @return true si il est dedans, autrement retourn false
	 */
	public Boolean isInTheWall(int x, int y) {
		if(x > Xmin && x < Xmin && y > Ymin && y < Ymax) { //Si il va dans le mur
			return true;
		}else {
			return false;
		}
	}
	
	
	public double getXmin() {return Xmin;}
	public void setXmin(double xmin) {Xmin = xmin;}

	public double getXmax() {return Xmax;}
	public void setXmax(double xmax) {Xmax = xmax;}

	public double getYmin() {return Ymin;}
	public void setYmin(double ymin) {Ymin = ymin;}

	public double getYmax() {return Ymax;}
	public void setYmax(double ymax) {Ymax = ymax;}

	public AnchorPane getWall() {return wall;}
	public void setWall(AnchorPane wall) {this.wall = wall;}

}
