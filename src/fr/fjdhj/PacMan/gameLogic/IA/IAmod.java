package fr.fjdhj.PacMan.gameLogic.IA;

public enum IAmod {
	
	CHASE     ("chase",75,85,95), //L'IA est en mode chase (normal)
	SCATTER   ("scatter",75,85,95), //L'IA est en mode scatter (va vers un point pr�d�finit, attention, blinky dans se mode poss�de une particulatit�)
	FRIGHTNED ("frightned",50,55,60), //L'IA est en mode frightned (PacMan peut la manger, l'IA tente de fuire)
	//Seulement pour Blinky, L'IA va juste plus vites, si il est dans ce mode, il poursuivra toujours PacMan
	ELROY1 ("scatterElroy1",80,90,100), 
	ELROY2 ("scatterElroy2",85,95,105);
	
	private final String mode;
	private final int speed1; //vitesse au niveau 1 (en %)
	private final int speed2; //vitesse du niveau 2 au 4 (en %)
	private final int speed5; //vitesse du niveau 5 a la fin (en %)
	
	IAmod(String mode, int speed1, int speed2, int speed5){
		this.mode = mode;
		this.speed1 = speed1;
		this.speed2 = speed2;
		this.speed5 = speed5;
		
	}

	@Override
	public String toString() {
		return mode;
	}
	
	/*
	 * NOTES : La vitesse des phantome dans le tunel est �gales � : (speed//2)+3
	 */
	
	public int getSpeed(int level) {
		if(level >= 5) {
			return speed5;
		}else if(level >= 2) {
			return speed2;
		}else {
			return speed1;
		}
	}
}
