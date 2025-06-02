package org.osier.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface WindowListener {
	
	default public void mouseClicked(MouseEvent e, boolean processedInput) {}
	default public void mousePressed(MouseEvent e, boolean processedInput) {}
	default public void mouseReleased(MouseEvent e, boolean processedInput) {}
	default public void mouseMoved(MouseEvent e, boolean isDragging) {}
	default public void mouseScrolled(MouseEvent e) {}
	default public void keyPressed(KeyEvent e) {}
	default public void keyReleased(KeyEvent e) {}
	default public void windowResized(int width, int height) {}
	default public void mouseEntered(MouseEvent e) {}
	default public void mouseExited(MouseEvent e) {}
	default public void windowClosing() {}

}
