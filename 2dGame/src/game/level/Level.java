package game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Entity;
import game.Colors;
import game.gfx.Screen;
import game.level.tiles.Tile;

public class Level {
	private byte[] tiles;
	private byte[] tiles3d;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	private String imagePath;
	private BufferedImage image;
	
	
	public Level(String imagePath) {
		
		if(imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		} else {
			this.width = 64;
			this.height = 64;
			tiles = new byte[width*height];
			
		}
		
	}
	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width*height];
			tiles3d = new byte[width*height];
			this.loadTiles();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadTiles() {
		int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++ ) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null && t.getLevelColor() == tileColors[x+y*width]) {
						this.tiles[x+y*width] = t.getId();
						
						if (t.getId() == 4 || t.getId() == 6) {
							// if tree or grass, add to tiles3d
							
							this.tiles3d[x+y*width] = t.getId();
							
							
						}
						
						break tileCheck;
					}
				}
			}
		}
		
	}
	
	private void saveLevelToFile() {
		try { 
			
			ImageIO.write(image,  "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e ) {
			e.printStackTrace();
		}
	}
	
	public void alterTile(int x, int y, Tile newTile) {
		
		if (getTile(x, y) != newTile && getTile(x, y).isBreakable)
		{
			this.tiles[x+y*width] = newTile.getId();
			image.setRGB(x, y, newTile.getLevelColor());
		}
		
	}
	
	public void generateLevel() {
		// not generate manually
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
					if(x*y %10 < 7) {
					tiles[x+y * width] = Tile.GRASS.getId();
					} else {
						tiles[x+y * width] = Tile.STONE.getId();
					}
				
			}
		}
	}
	
	public void tick(int tickCount)
	{
		
		if(tickCount%4 == 0) // slows down player movement
		{
			for(Entity e: entities)
			{
				e.tick();
			}
			
			for (Tile t : Tile.tiles)
			{
				if (t == null)
				{
					break;
				}
				t.tick();
			}
		}
		
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0) xOffset = 0;
		if(xOffset > ((width <<3)-screen.width)) xOffset = ((width <<3)-screen.width);
		if (yOffset < 0) yOffset = 0;
		if(yOffset > ((height <<3)-screen.height)) yOffset = ((height <<3)-screen.height);

		screen.setOffset(xOffset, yOffset);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				
				getTile(x, y).render(screen, this, x<<3, y<<3);
				
				if (getTile(x, y).getId() == 6)
				{
					// if tile is tall grass, render grass
					
					Tile.GRASS.render(screen, this, x<<3, y<<3);
					getTile(x, y).render(screen, this, x<<3, (y<<3));
				}
				
				
			}
		}
	}
	
	public void renderTile(Screen screen, int xOffset, int yOffset, int x, int y, boolean is3d) {
		if (xOffset < 0) xOffset = 0;
		if(xOffset > ((width <<3)-screen.width)) xOffset = ((width <<3)-screen.width);
		if (yOffset < 0) yOffset = 0;
		if(yOffset > ((height <<3)-screen.height)) yOffset = ((height <<3)-screen.height);

		screen.setOffset(xOffset, yOffset);
		
		if(!is3d)
		{
		
			getTile(x, y).render(screen, this, x<<3, y<<3);
			
			if(getTile(x,y).getId() == 6)
			{
				Tile.GRASS.render(screen, this, x<<3, y<<3);
				getTile(x, y).render(screen, this, x<<3, y<<3);
			}
		
		}
		else {
			if(getTile(x, y).isBreakable)
			{
				getTile(x, y).render(screen, this, x<<3, y<<3);
			}
		}
		
		
	}
	
	
//	public void renderEntities(Screen screen) {
//		for(Entity e: entities) {
//			
//			e.render(screen);
//			
//		}
//		
//	}
	
	public void renderMobs(Screen screen, int xOffset, int yOffset) {
		
		for(Entity e: entities) 
		{
			int tileX = (e.x+2)>>3;
			int tileY = (e.y+5)>>3;
				
			if(getTile(tileX, tileY).isBreakable)
			{
				renderTile(screen, xOffset, yOffset, tileX, tileY-1, false);
				renderTile(screen, xOffset, yOffset, tileX, tileY, false);
			
				e.render(screen);
				renderTile(screen, xOffset, yOffset, tileX, tileY, true);
			}
			else
			{
				e.render(screen);
			}
		}
	}
	
	
	public void renderTrees(Screen screen) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if (getTile(x, y).getId() == 4)
				{
					// trees
					
					int treex = x<<3;
					int treey = y<<3;
					int treeColor = getTile(x, y).getTileColor();
					int leafColor = Colors.get(522, 020, -1, 225);
					
					// render tree logs
					screen.render(treex, treey-8, 3, treeColor, 0x11, 1);
					screen.render(treex, treey-16, 3, treeColor, 0x11, 1);
					

					// render tree leaves
					screen.render(treex, treey-16, 4, leafColor, 0x01, 1);
					screen.render(treex, treey-24, 4, leafColor, 0x10, 1);
					screen.render(treex, treey-32, 4, leafColor, 0x11, 1);
					screen.render(treex-8, treey-16, 4, leafColor, 0x10, 1);
					screen.render(treex+8, treey-16, 4, leafColor, 0x10, 1);
					screen.render(treex-16, treey-16, 4, leafColor, 0x00, 1);
					screen.render(treex+16, treey-16, 4, leafColor, 0x10, 1);
					screen.render(treex-8, treey-24, 4, leafColor, 0x10, 1);
					screen.render(treex+8, treey-24, 4, leafColor, 0x11, 1);
					screen.render(treex-16, treey-24, 4, leafColor, 0x11, 1);
					screen.render(treex+16, treey-24, 4, leafColor, 0x10, 1);
					screen.render(treex-8, treey-32, 4, leafColor, 0x00, 1);
					screen.render(treex+8, treey-32, 4, leafColor, 0x10, 1);
				}
				
			}
		}
	}

	public Tile getTile(int x, int y) {
		
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.VOID;
		}
		return Tile.tiles[tiles[x+y*width]];
	}
	
	
	public int getTile3d(int x, int y) {
		return tiles3d[x+y*width];
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

}
