package game.level.tiles;

import game.gfx.Screen;
import game.level.Level;

public class SpawnTile extends BasicTile{
	
	int spawnedCoords_length = 50;
	int num_spawned = 0;
	int[][] spawnedCoords = new int[spawnedCoords_length][2];
	
	// 50 blobs each with x, y spawn coords;

	
	
	public SpawnTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, x, y, tileColor, levelColor);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	private void spawn(Level level, int x, int y)
	{
		if(this.id == 7) // if its a blob
		{
			
			level.spawnBlob(x, y);
		}
		
		
		
		
	}
	
	private boolean check_spawn_coords(int x, int y) {
		// checks if anything has been spawned on these coords
		for(int entity = 0; entity < spawnedCoords_length; entity++)
		{
			if(x == spawnedCoords[entity][0] && y == spawnedCoords[entity][1])
			{
				// return false if anything has been spawned
				return false;
			}
		}
		
		// return true and add these coords to the array
		spawnedCoords[num_spawned][0] = x;
		spawnedCoords[num_spawned][1] = y;
		num_spawned++;
		return true;
	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {
		// render grass under spawn tile
		GRASS.render(screen, level, x, y);
		
		
		if(check_spawn_coords(x, y)) spawn(level, x, y);
		
		
		
		
		
	}

	@Override
	public int getTileId() {
		// TODO Auto-generated method stub
		return 7;
	}

}
