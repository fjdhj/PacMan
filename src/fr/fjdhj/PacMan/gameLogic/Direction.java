package fr.fjdhj.PacMan.gameLogic;

public enum Direction{
    UP    ("up"), 
    DOWN  ("down"), 
    LEFT  ("left"), 
    RIGHT ("right");
	
	private final String direction;
	
	Direction(String direction){
		this.direction = direction;
	}
	
	public String toString() {
		return direction;
	}
}

