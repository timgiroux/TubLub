package game.level.tiles;

import game.Colors;
import game.gfx.Screen;
import game.level.Level;

public class BasicSolidTile extends BasicTile {

	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, x, y, tileColor, levelColor);
		
		this.solid = true;
		
		
		
	}
	
	
	public void render(Screen screen, Level level, int x, int y) {
			screen.render(x, y, tileId, tileColor, 0x00, 1);
			
			

			
		}
	}
	


