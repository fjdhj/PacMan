package fr.fjdhj.PacMan.gameLogic;

public enum Direction{
    UP    ("up", 270, -1, false), 
    DOWN  ("down", 90, +1, false), 
    LEFT  ("left", 180, -1, true), 
    RIGHT ("right", 0, +1, true);
	
	private final String direction;
	private final int rotate;
	private final int modifier;
	private final boolean horizontal;
	
	Direction(String direction, int rotate, int modifier, boolean horizontal){
		this.direction = direction;
		this.rotate = rotate;
		this.modifier = modifier;
		this.horizontal=horizontal;
	}
	
	public String toString() {
		return direction;
	}
	
	public int getRotate() {
		return rotate;
	}

	public int getModifier() {
		return modifier;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * 0 : UP
	 * 1 : DOWN
	 * 2 : LEFT
	 * 3 : RIGHT
	 * @return -1 si BUGGG
	 * 	
	 */
	public int sortedTab() {
		switch(direction) {
		case "up":
			return 0;
		case "down":
			return 1;
		case "left":
			return 2;
		case "right":
			return 3;
		}
		return -1;
			
	}
}

