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
	public final static long[] TIME_WAVE_1 = {7000,20000,7000,20000,5000,20000,5000,-1};
	public final static long[] FRIGHTNED_TIME = {6000,5000,4000,3000,2000,5000,2000,2000,1000,5000,2000,1000,1000,3000,1000,1000,-1,1000};
	
	public IA(){}

	
	/**
	 * Regarde si l'entit� peut avancer
	 * @param matrice : La matrice du jeu
	 * @param x : coordon�es x de l'entit�e
	 * @param y : coordon�es y de l'entit�e
	 * @param direction : direction de l'entit�e
	 * @return true si l'entit� peut avanc�; autrement renvoie false
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
	 * Regarde si l'entit�e se trouve a une intersection
	 * 
	 * @param matrice : La matrice du jeu (tuile)
	 * @param x : Les coordon�e x de l'entit� (tuile)
	 * @param y : Les coordon�e y de l'entit� (tuile)
	 * @param direction : La direction actuel de l'entit�
	 * @return Une list, vide si aucun changement de direction est possible,
	 * 			autrement contient les nouvels direction que l'entit� peut prendre
	 */
	private List<Direction> isAnIntersect(boolean[][] matrice, int x, int y, Direction direction) {
		List<Direction> result = new ArrayList<Direction>();
		
		//Si on se d�place horizontalement
		if(direction.isHorizontal()) {
			//Si il y a une tuile libre au dessu (=false)
			if(!matrice[x][y-1])
				result.add(Direction.UP);
			//Si il y a une tuile libre en dessou (=false)
			if(!matrice[x][y+1])
				result.add(Direction.DOWN);
			//Si c'est une intersection (changement de direction possible) et que l'IA peut garder la m�me direction 
			if(result.size()!=0 && !matrice[x+direction.getModifier()][y])
				result.add(direction);
		}else {
			//Si il y a une tuile libre a gauche (=false)
			if(!matrice[x-1][y])
				result.add(Direction.LEFT);
			//Si il y a une tuile libre a droite (=false)
			if(!matrice[x+1][y])
				result.add(Direction.RIGHT);
			//Si c'est une intersection (changement de direction possible) et que l'IA peut garder la m�me direction 
			if(result.size()!=0 && !matrice[x][y+direction.getModifier()])
				result.add(direction);
		}
		
		//On revoie la liste
		return result;
	}

	
	/**
	 * 
	 * Permet de cr�er un matrice, n�cessaires pour l'utilisation des algorythmes des fant�mes
	 * 
	 * @param wall La liste des murs
	 * @param x la dimention x de la matrice (= largeur/14)
	 * @param y la dimention y de la matrice (= hauteur/14)
	 */
	public static boolean[][] createMatrice(List<Wall> listWall, int dimx, int dimy) {
		boolean[][] matrice = new boolean[dimx][dimy];
		
		//On simule 2 point, a chaque extr�mit� droite de la tuile, si un point est dans un mur, alors la tuile de gauche et de droite seras consid�rer
		//occup� par le mur et vaudra true, sinon celle de droite seras consid�rer comme vide et prendra comme valeur false.
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
	 * Donne la direction horizontal recommand� 
	 * @param x : coordonn�e x (tuile)
	 * @param destx : coordonn�e x de la cible (tuile)
	 * @return Direction, null si elles ont la m�me coordon�e x
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
	 * Donne la direction vertical recommand� 
	 * @param y : coordonn�e y (tuile)
	 * @param desty : coordonn�e y de la cible (tuile)
	 * @return Direction, null si elles ont la m�me coordon�e y
	 */
	private Direction verticalDirectionNeed(int y, int desty) {
		if((desty-y)>0)
			return Direction.DOWN;
		else if((desty-y)<0)
			return Direction.UP;
		return null;
	}
	
	/**
	 * D�termine la meilleur direction a prendre
	 * 
	 * @param matrice : Le jeu
	 * @param destx : Les coordon�es x de la cible
	 * @param desty : Les coordon�es y de la cible
	 * @param x : Les coordon�es x de l'entit�
	 * @param y : Les coordon�es y de l'entit�
	 * @param direction : La direction de la cible
	 * @return une direction; null si la cible ne se trouve pas � une intersection
	 */
	protected Direction regularPathFiding(boolean[][] matrice, int destx, int desty, int x, int y, Direction direction) {
		if(!canGoForward(matrice, x, y, direction))
			return null;
		
		//La case a simuler est une tuille devant
		if(direction.isHorizontal())
			x+=direction.getModifier();
		else
			y+=direction.getModifier();
		//On r�cup�re les directions possible (demi tour exclue)
		List<Direction> result = isAnIntersect(matrice, x, y, direction);
		if(result.size()!=0  && (destx != x || desty != y) && ghost.getNewDirection().get() == null) {
			//Si on a qu'un seul direction on la prend
			if(result.size()==1)
				return result.get(0);
			double[] dist = new double[4];
			System.out.println("-----------------------------\n"+result.size());
			//On parcour notre r�sultat
			for(Direction dir : result) {
				//Si c'est horizontale
				//Distance = x a tester**2 + y a tester**2 
				if(dir.isHorizontal()) {
					//x a tester = abs(destx - (x+dir.getModifier()));
					//y a tester = abs(desty - y);
					System.out.println(Math.pow(destx - (x+dir.getModifier()), 2) + Math.pow(desty-y, 2));
					//On stock la distance entre pacman et le fantome
					dist[dir.sortedTab()] = Math.pow(destx - (x+dir.getModifier()), 2) + Math.pow(desty-y, 2);
				}else {
					//Si c'est verticale
					//x a tester = abs(destx - x);
					//y a tester = abs(desty - (y+dir.getModifier()));
					System.out.println(Math.pow(destx - x, 2) + Math.pow(desty-(y+dir.getModifier()), 2));
					//On stock la distance entre pacman et le fantome
					dist[dir.sortedTab()] = Math.pow(destx - x, 2) + Math.pow(desty-(y+dir.getModifier()), 2);
					
				}
			}
			int value = 9999;
			double test = 9999;
			System.out.println(dist[0] + " " + dist[1] + " " +dist[2] + " " +dist[3]);
			//On prends l'index pour dist[index] la plus petite
			for(int index=0; index<4; index++) {
				if(dist[index]<test && dist[index]!=0) {
					value = index;
					test = dist[index];
			}}
			
			System.out.println(test);
			//On retourn la valeur pour laquelle value correspond c'est a dire dist[index] la plus petite
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
	
	public void mainIA() {}
	
	/**
	 * Change de mode l'IA selon le mode actuelle
	 * @throws Exception si IAmod a pour valeur FRIGHTNED
	 * 
	 */
	@SuppressWarnings("incomplete-switch")
	public void changeMode() throws Exception {

		System.out.println("timer good");
		//si FRIGHTNED alors erreur
		if(mod.get().equals(IAmod.FRIGHTNED))
			throw new Exception("changeMode() can't be used when IAmod is FRIGHTNED");
		
		switch(mod.get()) {
		case CHASE:
			mod.set(IAmod.SCATTER);
			break;
		case SCATTER:
			mod.set(IAmod.CHASE);
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
		
		}
	}

	/**
	 * Regarde si le fantome se trouve sur PacMan
	 * @return <b>true</b> si c'est le cas, sinan renvoie <b>false</b>
	 */
	public boolean isOnPacMan() {
		//Attention, ici c'est bien la tuile et non pas la case sur la qu'elle il est !
		return ((int)(ghost.getXPos().get()/14) == (int) (player.getXPos().get()/14) && (int) (ghost.getYPos().get()/14) == (int) (player.getYPos().get()/14)); 
	}
	
	
	/* ------------------------------------------------
	 * GETTEUR/SETTEUR/TRANSFER
	 * ------------------------------------------------
	 */
	public synchronized Ghost getGhost() {return ghost;}
	public synchronized void setGhost(Ghost ghost) {this.ghost = ghost;}

	public synchronized PacMan getPlayer() {return player;}
	public synchronized void setPlayer(PacMan player) {this.player = player;}
	
	public synchronized boolean[][] getMatrice() {return matrice;}
	public synchronized void setMatrice(boolean[][] matrice) {this.matrice = matrice;}
	
	public synchronized int getLevel() {return level;}
	public synchronized void setLevel(int level) {this.level=level;} 
	
	public synchronized ObjectProperty<IAmod> getIAmod() {return mod;}
	public synchronized void setIAmod(IAmod mod) {this.mod.set(mod);}
	
	public synchronized IAmod getIAStoreMod() {return storMod;}
	public synchronized void setIAStoreMod(IAmod storMod) {this.storMod = storMod;}
	public synchronized void transferStoreModToCurent() {this.mod.set(storMod); this.storMod = null;}
	
	public synchronized int getRound() {return round;}

	
}
