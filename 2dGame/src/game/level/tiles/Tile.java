package game.level.tiles;

import game.Colors;
import game.gfx.Screen;
import game.level.Level;

public abstract class Tile {
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(000, -1, -1, -1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(-1, 333, 232, -1), 0xff555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, 322), 0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3, new int[][] {{0,5},{1,5},{2,5}, {1,5}},
			Colors.get(-1, 115, 225, -1), 0xff0000ff, 500);		
	public static final Tile TREE = new BasicSolidTile(4, 3, 0, Colors.get(-1, 322, 433, 500), 0xffffffff);
	public static final Tile LEAVE = new BasicTile(5, 4, 0,Colors.get(-1, 020, 132, 511), 0xff123456);
	public static final Tile TALLGRASS = new AnimatedTile(6, new int[][] {{0,6},{1,6},{2,6}},
			Colors.get(-1, 021, 231, -1), 0xff008000, 250);
	public static final Tile BLOB = new SpawnTile(7, 2, 0, Colors.get(-1, 131, 141, 322), 0xffff0000);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	public boolean isBreakable;
	private int levelColor;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplicate Tile ID");
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id] = this;
		
		if (id == 4 || id == 6)
		{
			isBreakable = true;
		}
		else
		{
			isBreakable = false;
		}
	}
	
	public byte getId() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmitter() {
		return emitter;
	}
	
	public int getLevelColor() {
		return levelColor;
	}
	
	public int getTileColor() {
		return this.getTileColor();
	}
	
	public String toString()
	{
		return String.valueOf(id);
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen, Level level, int x, int y);
	
	public abstract int getTileId();

}
