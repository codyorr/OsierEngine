package org.osier.listeners;

import java.awt.event.MouseEvent;

public interface GUIButtonListener {
	
	default public void mouseClicked(MouseEvent e) {}
	default public void mousePressed(MouseEvent e) {}
	default public void mouseReleased(MouseEvent e) {}
	default public void mouseEntered(MouseEvent e) {}
	default public void mouseExited(MouseEvent e) {}
	

}
