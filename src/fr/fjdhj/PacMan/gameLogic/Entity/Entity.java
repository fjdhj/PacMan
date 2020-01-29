package fr.fjdhj.PacMan.gameLogic.Entity;

import java.util.List;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;

public class Entity {
	
	protected DoubleProperty xPos = new SimpleDoubleProperty();      //<----> double
	protected DoubleProperty yPos = new SimpleDoubleProperty();      //<----> double
	protected ObjectProperty<Direction> direction = new SimpleObjectProperty<>(); //<----> Direction
	protected ObjectProperty<Direction> newDirection = new SimpleObjectProperty<>(); //<----> Une nouvelle direction 
	
	public Entity() {
		// TODO Auto-generated constructor stub
	}
	
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
	
	public void updatePosition(ImageView image) {
		setXPos(image.getLayoutX()+12);
		setYPos(image.getLayoutY()+12);
	}
	
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
