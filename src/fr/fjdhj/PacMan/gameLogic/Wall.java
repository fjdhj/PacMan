package fr.fjdhj.PacMan.gameLogic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Theo
 */
public class Wall {
	
	private double Xmin;
	private double Xmax;
	private double Ymin;
	private double Ymax;
	private Rectangle wall;
	
	
	/**
	 * Constructeur par défault
	 * Va calculer automatiquement les coordonées Maximumes et minimumes
	 * @param pane : L'AnchorPane utilisés pour faire le mur
	 */
	public Wall(Rectangle wall) {
		setWall(wall);
		setXmin(wall.getLayoutX());
		setYmin(wall.getLayoutY());
		setXmax(wall.getLayoutX() + wall.getWidth());
		setYmax(wall.getLayoutY() + wall.getHeight());
	}
	
	/**
	 * Constructeur : utilise 4 points
	 * 
	 * @param Xmin : Les coordonées minimales pour x
	 * @param Xmax : Les coordonées maximales pour x
	 * @param Ymin : Les coordonées minimales pour y
	 * @param Ymax : Les coordonées maximales pour y
	 */
	public Wall(int Xmin, int Xmax, int Ymin, int Ymax) {
		setXmin(Xmin);
		setYmin(Ymin);
		setXmax(Xmax);
		setYmax(Ymax);
		setWall(null);
	}
	
	
	public double getXmin() {return Xmin;}
	public void setXmin(double xmin) {Xmin = xmin;}

	public double getXmax() {return Xmax;}
	public void setXmax(double xmax) {Xmax = xmax;}

	public double getYmin() {return Ymin;}
	public void setYmin(double ymin) {Ymin = ymin;}

	public double getYmax() {return Ymax;}
	public void setYmax(double ymax) {Ymax = ymax;}

	public Rectangle getWall() {return wall;}
	public void setWall(Rectangle wall) {this.wall = wall;}

}
