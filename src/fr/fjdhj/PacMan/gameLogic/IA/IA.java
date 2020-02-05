package fr.fjdhj.PacMan.gameLogic.IA;

import java.util.ArrayList;
import java.util.List;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;

public class IA {
	
	protected Ghost ghost;
	protected PacMan player;
	protected boolean[][] matrice;
	
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
		int poid[] = new int[4];
		int profondeur = 50;
		List<Direction> result = isAnIntersect(matrice, x, y, direction);
		if(result.size()!=0 && (destx != x || desty != y)) {
			Direction directionH = horizontalDirectionNeed(x, destx);
			Direction directionV = verticalDirectionNeed(y, desty);
			
			//Si la direction horizontal recommandé est dans la list result
			if(result.contains(directionH))
				poid[directionH.sortedTab()] = minPathFinding(matrice, destx, desty, x+directionH.getModifier(), y, directionH, profondeur);
			//Si la direction vertical recommandé est dans la list result
			if(result.contains(directionV))
				poid[directionV.sortedTab()] = minPathFinding(matrice, destx, desty, x, y+directionV.getModifier(), directionV, profondeur);
			
			//Si le tableau ne contient rien, on prend change de direction pour x ou y = celle de la destination
			//x alignée
			if(!result.contains(directionV) || !result.contains(directionH)) {
				if(directionH == null) {
					for(Direction dir:result) {
						if(dir.isHorizontal()) {
							System.out.println(".......0......");
							poid[dir.sortedTab()]=minPathFinding(matrice, destx, desty, x+dir.getModifier(), y, dir, profondeur);
						}
					}
				//y allignée
				}else if(directionV == null){
					for(Direction dir:result) {
						if(!dir.isHorizontal())
							poid[dir.sortedTab()]=minPathFinding(matrice, destx, desty, x, y+dir.getModifier(), dir, profondeur);
					}
				}
			}
			//Si aucne des deux direction recommandé ne peut être emprunté
			if(!result.contains(directionV) || !result.contains(directionH)) {
				for(Direction dir:result) {
					if(dir.isHorizontal())
						poid[dir.sortedTab()] = minPathFinding(matrice, destx, desty, x+dir.getModifier(), y, dir, profondeur);
					else
						poid[dir.sortedTab()] = minPathFinding(matrice, destx, desty, x, y+dir.getModifier(), dir, profondeur);
				}}
			
			//On regarde maintenant la direction qui correspond à la valeur la plus petite
			int index = 999;
			int test = 999;
			for(int parcour=0; parcour<poid.length; parcour++) {
				if(poid[parcour]<test && poid[parcour]!=0) {
					test = poid[parcour];
					index = parcour;
				}
			}
			//UP
			if(index==0)
				return Direction.UP;
			//DOWN
			else if(index==1)
				return Direction.DOWN;
			//LEFT
			else if(index==2)
				return Direction.LEFT;
			//RIGHT
			else if(index==3)
				return Direction.RIGHT;
			//On peut obtenir -1 si il y a un bug, par défault null seras renvoyé
		}
		
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
	 * @return un poid; null si la est atteinte
	 */
	private int minPathFinding(boolean[][] matrice, int destx, int desty, int x, int y, Direction direction, int profondeur) {
		int poid = 0;
		List<Direction> result;
		profondeur--;
		
		//Si on est arrivé a destination
		if(destx == x && desty == y)
			return poid;
		if(profondeur==0)
			return 999999;
		
		while((result=isAnIntersect(matrice, x, y, direction)).size()<=1) {
			//Si on est arrivé a destination
			if(destx == x && desty == y) {
				System.out.println("good");
				return poid;
			}
			//Sinon si on a uniquement une nouvel direction disponible, on est alors dans un angle, on change donc de direction
			else if(result.size()==1)
				direction=result.get(0);
			
			//Si l'entité peut avancer
			if(canGoForward(matrice, x, y, direction)) {
				//Comme on avance, on augment le poid
				poid++;
				
				//On déplace ensuite l'entité
				if(direction.isHorizontal())
					x+=direction.getModifier();
				else
					y+=direction.getModifier();
			
			//Si on est bloqué, on renvoie une valeur trés imporante
			}else {
				return 999999;
			}
		}
		
		if(destx == x && desty == y)
			return poid;
		
		//On récupère les direction recomandé
		Direction directionH = horizontalDirectionNeed(x, destx);
		Direction directionV = verticalDirectionNeed(y, desty);
		System.out.println(directionH + " "+ directionV+ " "+x+ " "+y);
		List<Integer> tabPoid = new ArrayList<Integer>();
		System.out.println(result);
		//Si notre list contiens la direction recommandé :
		//HORIZONTALE
		if(result.contains(directionH))
			tabPoid.add(minPathFinding(matrice, destx, desty, x+directionH.getModifier(), y, directionH, profondeur));
		//VERTICALE
		if(result.contains(directionV))
			tabPoid.add(minPathFinding(matrice, destx, desty, x, y+directionV.getModifier(), directionV, profondeur));
		
		//Si le tableau ne contient rien, on prend change de direction pour x ou y = celle de la destination
		//x alignée
		if(tabPoid.size()==0 ) {
			if(directionH == null) {
				for(Direction dir:result) {
					if(dir.isHorizontal())
						tabPoid.add(minPathFinding(matrice, destx, desty, x+dir.getModifier(), y, dir, profondeur));
				}
				//y allignée
			}else if(directionV == null) {
				for(Direction dir:result) {
					if(!dir.isHorizontal())
						tabPoid.add(minPathFinding(matrice, destx, desty, x, y+dir.getModifier(), dir, profondeur));
				}
			}
		}
		
		if(tabPoid.size()==0 ) {
			for(Direction dir:result) {
				if(dir.isHorizontal() && direction.isHorizontal())
					tabPoid.add(minPathFinding(matrice, destx, desty, x+dir.getModifier(), y, dir, profondeur));
				else if(!dir.isHorizontal() && !direction.isHorizontal())
					tabPoid.add(minPathFinding(matrice, destx, desty, x, y+dir.getModifier(), dir, profondeur));
			}
		}
		
		//On récupère la valeur minimal
		java.util.Collections.sort(tabPoid);
		return tabPoid.get(0)+poid;
	}
	
	public void mainIA() {
		
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
	
	//A modifier dans chaque classe qui étant
	
}
