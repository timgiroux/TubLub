package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener {

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	public class Key {
		private int numTimesPressed = 0;
		public boolean pressed = false;
		
		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed) numTimesPressed++;
		}
	}

	// public List<Key> keys = new ArrayList<Key>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key space = new Key();
	public Key a = new Key();
	public Key b = new Key();

	

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			right.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_OPEN_BRACKET) {
			

			a.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_CLOSE_BRACKET) {
			b.toggle(isPressed);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
