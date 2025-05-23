package org.osier;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface CoreListener {
	//setup defaults (as to not force users to implement all methods at once)
	default public void load() {}
	default public void update() {}
	default public void render(Graphics2D g) {}
	default public void mouseClicked(MouseEvent e, boolean processed) {}
	default public void mousePressed(MouseEvent e, boolean processed) {}
	default public void mouseReleased(MouseEvent e, boolean processed) {}
	default public void mouseMoved(MouseEvent e, boolean isDragging) {}
	default public void mouseScrolled(MouseEvent e, boolean processed) {}
	default public void keyPressed(KeyEvent e, boolean processed) {}
	default public void keyReleased(KeyEvent e, boolean processed) {}
	default public void windowResized(int width, int height) {}
	default public void mouseEntered(MouseEvent e) {}
	default public void mouseExited(MouseEvent e) {}
	default public void dispose() {}
	
}
