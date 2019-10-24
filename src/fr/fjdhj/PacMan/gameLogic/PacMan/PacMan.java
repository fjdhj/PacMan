package fr.fjdhj.PacMan.gameLogic.PacMan;

import java.util.List;

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
	private ObjectProperty<Direction> newDirection = new SimpleObjectProperty<>(); //<----> Une nouvelle direction 
	
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
		life.set(life.get()+nbr);
	}
	
	/**
	 * Ajoute des points a PacMan
	 * NOTE: Si nbr<0 alors permet d'en retirer
	 * @param nbr : Nombre de point à ajouter
	 */
	public void addPoint(int nbr) {
		point.set(point.get() +nbr);
	}
	
	
	/* On dit que PacMan est placée dans un carrée (il fait 28 de cotée : 24 pour la taille de PacMan + 4 de vide)
	 * On utilise deux point A et B, placée au extrémité de la face qui avance
	 * Et on regarde si les deux points sont dans le mur 
	 * Si il se déplace latéralement : A(d,e) et B(d,f) et C(d,g)
	 * Si il se déplace verticalement : A(e,d) et B(f,d) et C(g,d)
	 * NOTE : dans une direction données (horizontale/verticale), quelque soit le sens, f et e et g sont identique mais d est different
	 */
	/**
	 * Regarde si PacMan va dans le mur en fonction de la direction
	 * @param wall : Le mur
	 * @return  true si il est dedans, autrement retourn false
	 */
	public Boolean goInWall(Wall wall) {
		int alpha = 0;
		boolean horizon = false;
		
			switch(direction.get()) {
			case LEFT:
				alpha=-14;
				horizon=true;
				break;
			case RIGHT:
				alpha=14;
				horizon=true;
				break;
			case UP:
				alpha=-14;
				horizon=false;
				break;
			case DOWN:
				alpha=14;
				horizon=false;
				break;
			}
			//Si on se déplace horizontalement
			if(horizon) {
				/*  d = x+alpha
				 *  e = y-12
				 *  f = y+12
				 *  g = y
				 */
				if(wall.getXmin()<=(xPos.get()+alpha) && (xPos.get()+alpha)<=wall.getXmax() && //On test pour d
				  ((wall.getYmin()<=(yPos.get()-13) && (yPos.get()-13)<=wall.getYmax()) || //On test pour e
				  (wall.getYmin()<=(yPos.get()+13) && (yPos.get()+13)<=wall.getYmax()) ||
				  (wall.getYmin()<=yPos.get() && yPos.get()<=wall.getYmax()))){ //On test pour f
						//Si toutes les condiotions son OK
						return true;
				}
				
			//Si on se déplace verticalment
			}else {
				/*  d = y+alpha
				 *  e = x-12
				 *  f = x+12
				 *  g = x
				 */
				if(wall.getYmin()<=(yPos.get()+alpha) && (yPos.get()+alpha)<=wall.getYmax() && //On test pour d
				  ((wall.getXmin()<=(xPos.get()-13) && (xPos.get()-13)<=wall.getXmax()) || //On test pour e
				  (wall.getXmin()<=(xPos.get()+13) && (xPos.get()+13)<=wall.getXmax()) ||
				  (wall.getXmin()<= xPos.get() && xPos.get()<=wall.getXmax()))){ //On test pour f
						//Si toutes les condiotions son OK
						return true;
				}
			}

		return false;
		
	}
	
	
	/* On dit que PacMan est placée dans un carrée (il fait 28 de cotée : 24 pour la taille de PacMan + 4 de vide)
	 * On utilise deux point A et B, placée au extrémité de la face qui avance
	 * Et on regarde si les deux points sont dans le mur 
	 * Si il se déplace latéralement : A(d,e) et B(d,f) et C(d,g)
	 * Si il se déplace verticalement : A(e,d) et B(f,d) et C(g,d)
	 * NOTE : dans une direction données (horizontale/verticale), quelque soit le sens, f et e et g sont identique mais d est different
	 */
	/**
	 * Regarde si PacMan va dans l'un des murs en fonction de la direction
	 * @param wall : une list de mur
	 * @return true si il est dedans, autrement retourn false
	 */
	public Boolean goInWall(List<Wall> ListWall) {
		int alpha = 0;
		boolean horizon = false;
			switch(direction.get()) {
			case LEFT:
				alpha=-14;
				horizon=true;
				break;
			case RIGHT:
				alpha=14;
				horizon=true;
				break;
			case UP:
				alpha=-14;
				break;
			case DOWN:
				alpha=14;
				break;
			}
			//Si on se déplace horizontalement
			if(horizon) {
				for(Wall wall : ListWall) {	
					/*  d = x+alpha
					 *  e = y-12
					 *  f = y+12
					 */
					if(wall.getXmin()<=(xPos.get()+alpha) && (xPos.get()+alpha)<=wall.getXmax() && //On test pour d
					  ((wall.getYmin()<=(yPos.get()-13) && (yPos.get()-13)<=wall.getYmax()) || //On test pour e
					  (wall.getYmin()<=(yPos.get()+13) && (yPos.get()+13)<=wall.getYmax()) ||
					  (wall.getYmin()<=yPos.get() && yPos.get()<=wall.getYmax()))){ //On test pour f
						//Si toutes les condiotions son OK
						return true;
					}
				}
				
			//Si on se déplace verticalment
			}else {
				for(Wall wall : ListWall) {	
					/*  d = y+alpha
					 *  e = x-12
					 *  f = x+12
					 */
					if(wall.getYmin()<=(yPos.get()+alpha) && (yPos.get()+alpha)<=wall.getYmax() && //On test pour d
					  ((wall.getXmin()<=(xPos.get()-13) && (xPos.get()-13)<=wall.getXmax()) || //On test pour e
					  (wall.getXmin()<=(xPos.get()+13) && (xPos.get()+13)<=wall.getXmax()) ||
					  (wall.getXmin()<= xPos.get() && xPos.get()<=wall.getXmax()))){ //On test pour f
						//Si toutes les condiotions son OK
						return true;
					}
				}
			}

		return false;
	}
	
	/**
	 * Reagrde si PacMan peut tourner
	 * Si oui, il change la direction de PacMan
	 * @param ListWall : La liste des murs
	 * @param newDirection : La nouvelle direction
	 * @return : True si possible, False si non
	 */
	public boolean canTurn(List<Wall> ListWall) {
		int alpha = 0;
		boolean horizon = false;
		switch(newDirection.get()) {
		case LEFT:
			alpha = -15;
			horizon=true;
			break;
		case RIGHT:
			alpha = 15;
			horizon=true;
			break;
		case UP:
			alpha = -15;
			break;
		case DOWN:
			alpha = 15;
			break;
		}
		
		if(horizon) {	
			for(Wall wall : ListWall) {	
				if(wall.getXmin()<=(xPos.get()+alpha) && (xPos.get()+alpha)<=wall.getXmax() && 
				  ((wall.getYmin()<=(yPos.get()-13) && (yPos.get()-13)<=wall.getYmax()) ||
				  (wall.getYmin()<=(yPos.get()+13) && (yPos.get()+13)<=wall.getYmax()) ||
				  (wall.getYmin()<=yPos.get() && yPos.get()<= wall.getYmax()))) {
					return false;
				}
			}
		}else {
			for(Wall wall : ListWall) {	
				if(wall.getYmin()<=(yPos.get()+alpha) && (yPos.get()+alpha)<=wall.getYmax() && 
				  ((wall.getXmin()<=(xPos.get()-13) && (xPos.get()-13)<=wall.getXmax()) ||
				  (wall.getXmin()<=(xPos.get()+13) && (xPos.get()+13)<=wall.getXmax()) ||
				  (wall.getXmin()<=xPos.get() && xPos.get()<=wall.getXmax()))) {
					return false;
				}
			}
		}
		setDirection(newDirection.get());
		this.newDirection.set(null);
		return true;
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
	 * @param direction La nouvelle direction (effet imediat)
	 */
	public void setDirection(ObjectProperty<Direction> direction) {
		this.direction = direction;
	}
	
	/**
	 * 
	 * @param direction La nouvelle direction (effet imediat)
	 */
	public void setDirection(Direction direction) {
		this.direction.set(direction);
	}

	/**
	 * 
	 * @return La nouvelle direction stocké
	 */
	public ObjectProperty<Direction> getNewDirection() {
		return newDirection;
	}

	/**
	 * 
	 * @param newDirection La nouvelle direction (effet aprés canTurn())
	 * @see canTurn(List<Wall> ListWall, Direction newDirection)
	 */
	public void setNewDirection(ObjectProperty<Direction> newDirection) {
		this.newDirection = newDirection;
	}
	
	/**
	 * 
	 * @param newDirection La nouvelle direction (effet aprés canTurn())
	 * @see canTurn(List<Wall> ListWall, Direction newDirection)
	 */
	public void setNewDirection(Direction newDirection) {
		this.newDirection.set(newDirection);
	}
	
	

}
