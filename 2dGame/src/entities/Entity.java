package entities;

import game.gfx.Screen;
import game.level.Level;

public abstract class Entity {
	
	public int x, y;
	public boolean isKill;
	
	protected Level level;
	
	public Entity(Level level) {
		init(level);
		this.isKill = false;
		
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);

	public void render(Screen screen, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	public void setKill(boolean b) {
		// TODO Auto-generated method stub

		isKill = b;
		
	}
	
	
	

}
