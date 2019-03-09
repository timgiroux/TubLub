package game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Entity;

import game.gfx.Screen;
import game.level.tiles.Tile;

public class Level {
	private byte[] tiles;
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
						
//						if(t.getId() == 6) {
//							entities.add(new TallGrass(this));
//						}
						
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
		this.tiles[x+y*width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
		
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
	
	public void tick()
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
	
	public void renderEntities(Screen screen) {
		for(Entity e: entities) {
			
			e.render(screen);
			
		}
	}
	
	public void renderTrees(Screen screen, int py) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if (getTile(x, y).getId() == 6 && y<<3 > py-2)
				{
					// tall grass
					
				getTile(x, y).render(screen, this, x<<3, y<<3);
				}
				
				if (getTile(x, y).getId() == 4 && y<<3 > py)
				{
					// trees
				getTile(x, y).render(screen, this, x<<3, y<<3);
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
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

}
