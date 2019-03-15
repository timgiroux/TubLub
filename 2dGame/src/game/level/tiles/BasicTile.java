package game.level.tiles;

import java.util.Random;

import game.gfx.Screen;
import game.level.Level;

public class BasicTile extends Tile {
	
	protected int tileId;
	protected int tileColor;
	private byte grassFlip = 0x00;
	
//	static Random random = new Random();
//	private static int choice = random.nextInt(4);
	private int choice;
	Random random = new Random();
	

	public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor);
		this.tileId = x + y * 32;
		this.tileColor = tileColor;
		
	}

	public void tick() {
		
	}
	public void render(Screen screen, Level level, int x, int y) {
		if (this.id == 2) // render grass
		{
			if((x+y)%5 == 3) {
				grassFlip = 2;
			}
			else if ((x+y)%5 == 1)
			{
				grassFlip = 0;
			}
			else
			{
				grassFlip = 1;
			}
			screen.render(x, y, tileId, tileColor, grassFlip, 1);
		}
		else
		{
			screen.render(x, y, tileId, tileColor, 0x00, 1);
		}
			
	}
	
	public int getTileColor() {
		return tileColor;
	}

	
	public int getTileId() {
		
		return 0;
	}
	
	
	
	
	

}
