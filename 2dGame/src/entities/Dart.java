package entities;

import game.Colors;
import game.gfx.Screen;
import game.level.Level;

public class Dart extends Entity{
	
	private int speed = 1;
	private boolean isHit = false;
	private int direction;
	private int dartColor = Colors.get(-1, -1, 521, 210);
	private byte flipDart = 0x00;
	private int dartTile = 1+27*32;

	public Dart(Level level, int direction, int start_x, int start_y) {
		super(level);
		this.direction = direction;
		this.x = start_x;
		this.y = start_y-3;
	}

	public void tick() {
		// direction ::: 0: up, 1:down, 2:left, 3:right
		switch(direction)
		{
		case 0:
			y -= speed;
			dartTile = 2+27*32;
			break;
		case 1:
			y += speed;
			dartTile = 2+27*32;
			flipDart = 0x01;
			break;
		case 2:
			x -= speed;
			break;
		case 3:
			x += speed;
			flipDart = 0x01;
		}
		
		if(isHit)
		{
			x = -100;
			y = -100;
			speed = 0;
			isHit = false;
		}
		
	}

	public void render(Screen screen) {
		
		if(!isHit)
		{
		screen.render(x, y, dartTile, dartColor, flipDart, 1);
		}
		
		
	}
	
	
	public boolean check_hit(int x, int y)
	{
		
		// if this.x and this.y are within the 8x16 tile from x, y
		if(this.x >= x-4 && this.x <= x+8 && this.y >= y && this.y <= y+16)
		{
			isHit = true;
			return true;
		}
		
		return false;
	}
	
	public void isHit(boolean isHit)
	{
		this.isHit = isHit;
	}

}
