package entities;

import game.Colors;
import game.Font;
import game.InputHandler;
import game.gfx.Screen;
import game.level.Level;
import game.level.tiles.Tile;

public class Player extends Mob{

	private InputHandler input;
	private int color = Colors.get(-1, 521, 210, 543);
	private int scale = 1;
	protected boolean isSwimming = false;
	private int tickCount = 0;
	private int jump_state = 0; // 0:false 1:true, 2:jumping, // 3:cool down
	private int jump_modifier = 0;
	private int hit_state = 0; // 0:false 1:true, 2:hitting, // 3:cool down
	private int throw_state = 0; // 0:false 1:true, 2:hitting, // 3:cool down
	private int inventory = 0;
	
	
	public Player(Level level, int x, int y, float speed, InputHandler input) {
		super(level, "Player", x, y, speed);
		// toot
		this.input = input;
		
		
	}
	
	
	
	long throw_time = 0; 
	public void throw_data() {
		int delay = 250;
		if(throw_state == 1) {
			// begin the hit
			
			throw_time = System.currentTimeMillis()+delay;
			
			
			throw_state = 2;
			
			}
		if(throw_state == 2) {
			// while hitting
			
			if(System.currentTimeMillis() > throw_time) {
				// stop hit
				throw_state = 3;
			}
		}
		if (throw_state == 3) {
			// delay 250 ms and then set to 0
			if(System.currentTimeMillis() > throw_time+delay) {
				throw_state = 0;
			}
		}
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
		
		int x = this.x>>3;
		int y = this.y>>3;
		
		if (movingDir == 0)
		{
			if (isWood(level, x, y, x, y-1)) add_inventory(4);
			level.alterTile(x, y-0, Tile.GRASS);
			level.alterTile(x, y-1, Tile.GRASS);
		}
		
		if (movingDir == 1)
		{
			if (isWood(level, x, y, x, y+1)) add_inventory(4);
			level.alterTile(x, y+0, Tile.GRASS);
			level.alterTile(x, y+1, Tile.GRASS);
		}
		if (movingDir == 2)
		{
			if (isWood(level, x-1, y, x-2, y)) add_inventory(4);
			level.alterTile(x-1, y, Tile.GRASS);
			level.alterTile(x-2, y, Tile.GRASS);
		}
		
		if (movingDir == 3)
		{
			// if a wood block is broken, add to inventory
			if (isWood(level, x+1, y, x+2, y)) add_inventory(4);
			level.alterTile(x+1, y, Tile.GRASS);
			level.alterTile(x+2, y, Tile.GRASS);
		}
		
	}
	
	public boolean isWood(Level level, int x, int y, int x2, int y2) {
		// just checks if the blocks at the coords are grass
		if (level.getTile(x, y) == Tile.TREE || level.getTile(x2, y2) == Tile.TREE)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void add_inventory(int amount) {
		if (amount > 0)
		{
			if (inventory < 4)
			{
				
				inventory = amount;
			}
		}
		else
		{
			if (inventory > 0)
			{
				inventory += amount;
			}
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
		int walkingSpeed = 2;
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
		throw_data();
	
		
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
			screen.render(xOffset,yOffset+3,0+27*32,waterColor,0x00,1);
			screen.render(xOffset+8,yOffset+3,0+27*32,waterColor,0x01,1);
			
		}
		
		// top
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile+yTile*32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, xTile+1+yTile*32, color, flipTop, scale);
		
		
		if(!isSwimming) {
			// bottom
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile+(yTile+1)*32, color, flipBottom, scale);
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, xTile+1+(yTile+1)*32, color, flipBottom, scale);
		}
		
		// render inventory
		int inventoryColor = Colors.get(-1, -1, -1, 111);
		int inventory_dist = -1;
		switch(inventory)
		{
			case 0:
				break;
			case 1:
				screen.render(xOffset + (modifier * 0x00) + inventory_dist, yOffset, 8+28*32, inventoryColor, 0x00, scale);
				break;
			case 2:
				screen.render(xOffset + (modifier * 0x00) + inventory_dist, yOffset, 8+29*32, inventoryColor, 0x00, scale);
				break;
			case 3:
				screen.render(xOffset + (modifier * 0x00) + inventory_dist, yOffset, 9+28*32, inventoryColor, 0x00, scale);
				break;
			case 4:
				screen.render(xOffset + (modifier * 0x00) + inventory_dist, yOffset, 9+29*32, inventoryColor, 0x00, scale);
				
		}
		
		
		
		// render hit
		if(hit_state == 1 || hit_state == 2)
		{
			// render hit if hitting
			hit_destroy(level);
			
			// directional
			int hitColor = Colors.get(-1, -1, 550, 550);
			
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
	
	public void tick() {
		float xa = 0;
		float ya = 0;
		if (input.up.isPressed()) {
			movingDir = 0;
			ya-= speed;
		}
		if (input.down.isPressed()) {
			movingDir = 1;
			ya+= speed;
		}
		if (input.left.isPressed()) {
			movingDir = 2;
			xa-= speed;
		}
		if (input.right.isPressed()) {
			movingDir = 3;
			xa+= speed;
		}
		if (input.space.isPressed()) {
			if(jump_state == 0 && !isSwimming) {
				jump_state = 1;
			}
		}
		if(input.a.isPressed()) {
			if(hit_state == 0 && !isSwimming) {
				hit_state = 1;
			}
		}
		if(input.b.isPressed()) {
			if(throw_state == 0 && !isSwimming) {
				throw_state = 1;
				add_inventory(-1);
			}
			
			
		}
		
		
		
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
			
		} else {
			isMoving = false;
		}
		if (level.getTile(this.x>>3, (this.y)>>3).getId() == 3) {
			isSwimming = true;
			speed = 1f;
		}
		if(isSwimming && level.getTile(this.x>>3, this.y>>3).getId() != 3) {
			isSwimming = false;
			speed = 2f;
		}
	
		tickCount++;
	}
	
	
	
	public boolean hasCollided(float xa, float ya) {
		
		int xMin = 0;
		int xMax = 7;
		int yMin = -1;
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
