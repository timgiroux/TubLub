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
			
//			if (this.id == 4) // render trees from base
//			{
//				
//				int leafColor = Colors.get(522, 020, -1, 225);
//				
//				// render tree logs
//				screen.render(x, y-8, 3, tileColor, 0x11, 1);
//				screen.render(x, y-16, 3, tileColor, 0x11, 1);
//				
//
//				// render tree leaves
//				screen.render(x, y-16, 4, leafColor, 0x01, 1);
//				screen.render(x, y-24, 4, leafColor, 0x10, 1);
//				screen.render(x, y-32, 4, leafColor, 0x11, 1);
//				screen.render(x-8, y-16, 4, leafColor, 0x10, 1);
//				screen.render(x+8, y-16, 4, leafColor, 0x10, 1);
//				screen.render(x-16, y-16, 4, leafColor, 0x00, 1);
//				screen.render(x+16, y-16, 4, leafColor, 0x10, 1);
//				screen.render(x-8, y-24, 4, leafColor, 0x10, 1);
//				screen.render(x+8, y-24, 4, leafColor, 0x11, 1);
//				screen.render(x-16, y-24, 4, leafColor, 0x11, 1);
//				screen.render(x+16, y-24, 4, leafColor, 0x10, 1);
//				screen.render(x-8, y-32, 4, leafColor, 0x00, 1);
//				screen.render(x+8, y-32, 4, leafColor, 0x10, 1);
//				
//			}
			
		}
	}
	


