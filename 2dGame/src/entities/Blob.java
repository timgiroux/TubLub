package entities;

import java.util.Random;

import game.Colors;
import game.gfx.Screen;
import game.level.Level;
import game.level.tiles.Tile;

public class Blob extends Mob {

	private int color = Colors.get(-1, 320, 121, 050);
	private int scale = 1;
	private float speed = 1f;
	protected boolean isSwimming = false;
	private int tickCount = 0;
	private int jump_state = 0; // 0:false 1:true, 2:jumping, // 3:cool down
	private int jump_modifier = 0;
	private int hit_state = 0; // 0:false 1:true, 2:hitting, // 3:cool down
	private Random random_direction = new Random();
	
	
	public Blob(Level level, int x, int y) {
		super(level, "Blob", x, y, 1);
		this.y += 16;
	}
	
	
	
	
	long hit_time = 0; 
	public void hit_data() {
		if(hit_state == 1) {
			// begin the hit
			
			hit_time = System.currentTimeMillis()+250;
			
			hit_state = 2;
			
			}
		if(hit_state == 2) {
			// while hitting
			
			if(System.currentTimeMillis() > hit_time) {
				// stop hit
				hit_state = 3;
			}
		}
		if (hit_state == 3) {
			// delay 250 ms and then set to 0
			if(System.currentTimeMillis() > hit_time+250) {
				hit_state = 0;
			}
		}
	}
	
	public void hit_destroy(Level level) {
		
		if (movingDir == 0)
		{
			level.alterTile((this.x>>3), (this.y>>3)-0, Tile.GRASS);
			level.alterTile((this.x>>3), (this.y>>3)-1, Tile.GRASS);
		}
		
		if (movingDir == 1)
		{
			level.alterTile((this.x>>3), (this.y>>3)+0, Tile.GRASS);
			level.alterTile((this.x>>3), (this.y>>3)+1, Tile.GRASS);
		}
		if (movingDir == 2)
		{
			level.alterTile((this.x>>3)-1, this.y>>3, Tile.GRASS);
			level.alterTile((this.x>>3)-2, this.y>>3, Tile.GRASS);
		}
		
		if (movingDir == 3)
		{
			level.alterTile((this.x>>3)+1, this.y>>3, Tile.GRASS);
			level.alterTile((this.x>>3)+2, this.y>>3, Tile.GRASS);
		}
	}
	
	long jump_time = 0;
	public void jump_data() {
		if(jump_state == 1) {
			// begin the jump
			jump_modifier = -4;
			
			jump_time = System.currentTimeMillis()+250;
			
			jump_state = 2;
			
			}
		if(jump_state == 2) {
			// jump animation
			// reset jump state
			if(System.currentTimeMillis() > jump_time) {

				jump_state = 3;
				jump_modifier = 0;
			}
		}
		if (jump_state == 3) {
			// delay 250 ms and then set to 0
			if(System.currentTimeMillis() > jump_time+250) {
				jump_state = 0;
			}
		}

	}
	
	public void render(Screen screen) {
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		yOffset += jump_modifier;
		
		int xTile = 0;
		int yTile = 28;
		int walkingSpeed = 4;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if(movingDir == 1) {
			xTile += 2;
		} else if (movingDir > 1) {
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1)%2;
			flipBottom = (movingDir - 1)%2;
			
		}
		
		
		jump_data();
		hit_data();
		
		
		// adjust for giants
//		if(scale == 2)
//		{
//			yOffset -= 16;
//			xOffset -= 50;
//		}
	
		
		if (isSwimming) {
			int waterColor = 0;
			yOffset += 4;
			if(tickCount % 60 < 15)
			{
				waterColor = Colors.get(-1, -1, 225, -1);
			} else if (15 <= tickCount%60 && tickCount %60 < 30)
			{
				yOffset--;
				waterColor = Colors.get(-1, 225, 115, -1);
			} else if (30 <= tickCount%60 && tickCount %60 < 45)
			{
				waterColor = Colors.get(-1, 115, -1, 225);
			} else {
				yOffset--;
				waterColor = Colors.get(-1, 225, 115, -1);

			}
			// splashes
			screen.render(xOffset,yOffset+3,0+27*32,waterColor,0x00,scale);
			screen.render(xOffset+8,yOffset+3,0+27*32,waterColor,0x01,scale);
			
		}
		
		// top
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile+yTile*32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, xTile+1+yTile*32, color, flipTop, scale);
		
		
		if(!isSwimming) {
			// bottom
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile+(yTile+1)*32, color, flipBottom, scale);
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, xTile+1+(yTile+1)*32, color, flipBottom, scale);
		}
		
		
		
		if(hit_state == 1 || hit_state == 2)
		{
			// render hit if hitting
			hit_destroy(level);
			
			// directional
			int hitColor = Colors.get(-1, -1, 555, 500);
			
			if(movingDir == 0) {
				screen.render(xOffset+4,yOffset-5,0+1*32,hitColor,0x00,1);
			}
			if(movingDir == 1) {
				screen.render(xOffset+4,yOffset+14,0+1*32,hitColor,0x10,1);
			}
			if(movingDir == 2) {
				screen.render(xOffset-4,yOffset+3,0+1*32,hitColor,0x00,1);
			}
			if(movingDir == 3) {
				screen.render(xOffset+12,yOffset+3,0+1*32,hitColor,0x01,1);
			}
			
		}
		
	}
	
	int dir = 0;
	public void tick() {
		
		if(isKill)
		{
			// move off of screen and freeze
			x = -100;
			y = -100;
			speed = 0;
			
		} else
		{
		
		if (tickCount % 10 == 0)
		{
			dir = random_direction.nextInt(7);
		}
		int xa = 0;
		int ya = 0;
		if (dir == 0) {
			ya-= speed;
		}
		if (dir == 1) {
			ya+= speed;
		}
		if (dir == 2) {
			xa-= speed;
		}
		if (dir == 3) {
			xa+= speed;
		}
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
			
		} else {
			isMoving = false;
		}
		if (level.getTile(this.x>>3, this.y>>3).getId() == 3) {
			isSwimming = true;
		}
		if(isSwimming && level.getTile(this.x>>3, this.y>>3).getId() != 3) {
			isSwimming = false;
		}
		}
		
		tickCount++;
	}
	
	public void setKill(boolean kill) {
		isKill = kill;
	}
	
	
	
	public boolean hasCollided(float xa, float ya) {
		
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		
//		to jump over objects
//		if(jump_state != 0) {
//			return false;
//		}
		
		for (int x = xMin; x < xMax ; x++ ) {
			if(isSolidTile(xa,ya,x,yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax ; x++ ) {
			if(isSolidTile(xa,ya,x,yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax ; y++ ) {
			if(isSolidTile(xa,ya,xMin,y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax ; y++ ) {
			if(isSolidTile(xa,ya,xMax,y)) {
				return true;
			}
		}
		
		return false;
	}
	

}

