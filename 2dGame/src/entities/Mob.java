package entities;

import game.level.Level;
import game.level.tiles.Tile;

public abstract class Mob extends Entity {
	
	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		
		this.name = name;
		this.speed = speed;
		this.x = x;
		this.y = y;
		
	}
	
	public void move(float xa, float ya) {
		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if(!hasCollided(xa, ya)) {
			if (ya < 0) movingDir = 0;
			if (ya > 0) movingDir = 1;
			if (xa < 0) movingDir = 2;
			if (xa > 0) movingDir = 3;
			
			x+= xa*speed;
			y+= ya*speed;
		}
		
	}
	
	public abstract boolean hasCollided(float xa, float ya);
	
	protected boolean isSolidTile(float xa, float ya, int x, int y) {
		
		
		
		if (level == null) return false;
		
		Tile lastTile = level.getTile((this.x+x)>>3, (this.y + y)>>3);
		Tile newTile = level.getTile((this.x+x+(int)xa)>>3, (this.y+y+(int)ya)>>3);
		
		if (!lastTile.equals(newTile) && newTile.isSolid()) return true;
		return false;
		
	}
	
	public String getName() {
		return name;
	}

}
