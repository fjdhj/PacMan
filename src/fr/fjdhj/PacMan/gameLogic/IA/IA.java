package fr.fjdhj.PacMan.gameLogic.IA;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class IA {
	
	protected Ghost ghost;
	protected PacMan player;
	protected boolean[][] matrice;
	protected int level;
	protected IAmod storMod;
	protected ObjectProperty<IAmod> mod = new SimpleObjectProperty<>(IAmod.SCATTER);
	
	/*
	 * Apres avoir perdu une vie, ou apres avoir commencer un niveau, les IA sont en mode SCATTER
	 * Les temps sont en milliseconde
	 */
	protected int round =0;
	public final long[] TIME_WAVE_1 = {7000,20000,7000,20000,5000,20000,5000,-1};
	public final long[] FRIGHTNED_TIME = {6000,5000,4000,3000,2000,5000,2000,2000,1000,5000,2000,1000,1000,3000,1000,1000,-1,1000};
	
	public IA(){}

	
	/**
	 * Regarde si l'entité peut avancer
	 * @param matrice : La matrice du jeu
	 * @param x : coordonées x de l'entitée
	 * @param y : coordonées y de l'entitée
	 * @param direction : direction de l'entitée
	 * @return true si l'entité peut avancé; autrement renvoie false
	 */
	private boolean canGoForward(boolean[][] matrice, int x, int y, Direction direction) {
		if(direction.isHorizontal()) {
			if(!matrice[x+direction.getModifier()][y])
				return true;
		}else {
			if(!matrice[x][y+direction.getModifier()])
				return true;
		}
		
		return false;
	}
	
	
	/**
	 * Regarde si l'entitée se trouve a une intersection
	 * 
	 * @param matrice : La matrice du jeu (tuile)
	 * @param x : Les coordonée x de l'entité (tuile)
	 * @param y : Les coordonée y de l'entité (tuile)
	 * @param direction : La direction actuel de l'entité
	 * @return Une list, vide si aucun changement de direction est possible,
	 * 			autrement contient les nouvels direction que l'entité peut prendre
	 */
	private List<Direction> isAnIntersect(boolean[][] matrice, int x, int y, Direction direction) {
		List<Direction> result = new ArrayList<Direction>();
		
		//Si on se déplace horizontalement
		if(direction.isHorizontal()) {
			//Si il y a une tuile libre au dessu (=false)
			if(!matrice[x][y-1])
				result.add(Direction.UP);
			//Si il y a une tuile libre en dessou (=false)
			if(!matrice[x][y+1])
				result.add(Direction.DOWN);
			//Si c'est une intersection (changement de direction possible) et que l'IA peut garder la même direction 
			if(result.size()!=0 && !matrice[x+direction.getModifier()][y])
				result.add(direction);
		}else {
			//Si il y a une tuile libre a gauche (=false)
			if(!matrice[x-1][y])
				result.add(Direction.LEFT);
			//Si il y a une tuile libre a droite (=false)
			if(!matrice[x+1][y])
				result.add(Direction.RIGHT);
			//Si c'est une intersection (changement de direction possible) et que l'IA peut garder la même direction 
			if(result.size()!=0 && !matrice[x][y+direction.getModifier()])
				result.add(direction);
		}
		
		//On revoie la liste
		return result;
	}
	
	/*public boolean isOnPacMan() {
		System.out.println("IA X : " + simX + "; Y : " + simY);
		System.out.println("PacMan X : " + player.getXPos().get() + "; Y : " + player.getYPos().get());
		if(player.getXPos().get() == simX && player.getYPos().get() == simY) {
			return true;
		}
		
		
		return false;
	}*/
	
	/**
	 * 
	 * Permet de créer un matrice, nécessaires pour l'utilisation des algorythmes des fantômes
	 * 
	 * @param wall La liste des murs
	 * @param x la dimention x de la matrice (= largeur/14)
	 * @param y la dimention y de la matrice (= hauteur/14)
	 */
	public static boolean[][] createMatrice(List<Wall> listWall, int dimx, int dimy) {
		boolean[][] matrice = new boolean[dimx][dimy];
		
		//On simule 2 point, a chaque extrémité droite de la tuile, si un point est dans un mur, alors la tuile de gauche et de droite seras considérer
		//occupé par le mur et vaudra true, sinon celle de droite seras considérer comme vide et prendra comme valeur false.
		for(double indexY=0; indexY<dimy*14;indexY+=14) {
			for(double indexX=0; indexX<=dimx*14;indexX+=14) {
				int x = (int) (indexX/14);
				int y = (int) (indexY/14);
				boolean test = false;

				for(Wall wall:listWall) {
					if(wall.pointInWall(indexX, indexY+1)||wall.pointInWall(indexX, indexY+13)) {
						if(x<dimx) {
							matrice[x][y]=true;
						}
						
						if(x>0) {
							matrice[x-1][y]=true;
						}
						
						
						
						test = true;
						break;
					}
				}
				//Le point n'est dans aucun mur
				if(!test && x<dimx){
					matrice[x][y]=false;
				}else {
					test=false;
				}
				
			}
		}
		
		return matrice;
		
	}
	
	/**
	 * Donne la direction horizontal recommandé 
	 * @param x : coordonnée x (tuile)
	 * @param destx : coordonnée x de la cible (tuile)
	 * @return Direction, null si elles ont la même coordonée x
	 */
	private Direction horizontalDirectionNeed(int x, int destx) {
		
		if((destx-x)>0) {
			return Direction.RIGHT;
		}
		else if((destx-x)<0)
			return Direction.LEFT;
		return null;
		
	}
	
	/**
	 * Donne la direction vertical recommandé 
	 * @param y : coordonnée y (tuile)
	 * @param desty : coordonnée y de la cible (tuile)
	 * @return Direction, null si elles ont la même coordonée y
	 */
	private Direction verticalDirectionNeed(int y, int desty) {
		if((desty-y)>0)
			return Direction.DOWN;
		else if((desty-y)<0)
			return Direction.UP;
		return null;
	}
	
	/**
	 * Détermine la meilleur direction a prendre
	 * 
	 * @param matrice : Le jeu
	 * @param destx : Les coordonées x de la cible
	 * @param desty : Les coordonées y de la cible
	 * @param x : Les coordonées x de l'entité
	 * @param y : Les coordonées y de l'entité
	 * @param direction : La direction de la cible
	 * @return une direction; null si la cible ne se trouve pas à une intersection
	 */
	protected Direction regularPathFiding(boolean[][] matrice, int destx, int desty, int x, int y, Direction direction) {
		if(!canGoForward(matrice, x, y, direction))
			return null;
		
		//La case a simuler est une tuille devant
		if(direction.isHorizontal())
			x+=direction.getModifier();
		else
			y+=direction.getModifier();
		List<Direction> result = isAnIntersect(matrice, x, y, direction);
		if(result.size()!=0  && (destx != x || desty != y) && ghost.getNewDirection().get() == null) {
			if(result.size()==1)
				return result.get(0);
			double[] dist = new double[4];
			System.out.println("-----------------------------\n"+result.size());
			for(Direction dir : result) {
				//Distance = x a tester**2 + y a tester**2 
				if(dir.isHorizontal()) {
					//x a tester = abs(destx - (x+dir.getModifier()));
					//y a tester = abs(desty - y);
					System.out.println(Math.pow(destx - (x+dir.getModifier()), 2) + Math.pow(desty-y, 2));
					dist[dir.sortedTab()] = Math.pow(destx - (x+dir.getModifier()), 2) + Math.pow(desty-y, 2);
				}else {
					//x a tester = abs(destx - x);
					//y a tester = abs(desty - (y+dir.getModifier()));
					System.out.println(Math.pow(destx - x, 2) + Math.pow(desty-(y+dir.getModifier()), 2));
					dist[dir.sortedTab()] = Math.pow(destx - x, 2) + Math.pow(desty-(y+dir.getModifier()), 2);
					
				}
			}
			int value = 9999;
			double test = 9999;
			System.out.println(dist[0] + " " + dist[1] + " " +dist[2] + " " +dist[3]);
			for(int index=0; index<4; index++) {
				if(dist[index]<test && dist[index]!=0) {
					value = index;
					test = dist[index];
			}}
			
			System.out.println(test);
			
			if(value == 0)
				return Direction.UP;
			if(value == 1)
				return Direction.LEFT;
			if(value == 2)
				return Direction.DOWN;
			if(value == 3)
				return Direction.RIGHT;
		}
		
		
		return null;
		
		
	}
	
	public void mainIA() {
		
	}
	
	/**
	 * @return 
	 * 
	 */
	public Timer timer(long time) {
		if(time!=-1) {
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("timer good");
					if(!mod.get().equals(IAmod.FRIGHTNED))
						round++;
					
					switch(mod.get()) {
					case CHASE:
						mod.set(IAmod.SCATTER);
						break;
					case FRIGHTNED:
						mod.set(storMod);
						break;
					case SCATTER:
						mod.set(IAmod.CHASE);
						break;
					case ELROY1:
						break;
					case ELROY2:
						break;
					
					}
					switch(ghost.getDirection().get()) {
					case DOWN:
						ghost.setDirection(Direction.UP);
						break;
					case LEFT:
						ghost.setDirection(Direction.RIGHT);
						break;
					case RIGHT:
						ghost.setDirection(Direction.LEFT);
						break;
					case UP:
						ghost.setDirection(Direction.DOWN);
						break;
					default:
						break;
					
					}
					
				}
				
			}, time);
			return timer;
		}
		return null;
	}

	/* ------------------------------------------------
	 * GETTEUR/SETTEUR
	 * ------------------------------------------------
	 */
	public Ghost getGhost() {return ghost;}
	public void setGhost(Ghost ghost) {this.ghost = ghost;}

	public PacMan getPlayer() {return player;}
	public void setPlayer(PacMan player) {this.player = player;}
	
	public boolean[][] getMatrice() {return matrice;}
	public void setMatrice(boolean[][] matrice) {this.matrice = matrice;}
	
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level=level;} 
	
	public ObjectProperty<IAmod> getIAmod() {return mod;}
	public void setIAmod(IAmod mod) {this.mod.set(mod);}
	
	public int getRound() {return round;}

	
}
