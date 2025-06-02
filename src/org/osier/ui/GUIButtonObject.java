package org.osier.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osier.listeners.GUIButtonListener;

public class GUIButtonObject extends GUIObject implements GUIButtonListener {
	
	protected boolean hovered;
	protected boolean pressed;
	
	private Color hoveredBackgroundColor;
	private Color hoveredBorderColor;
	private Color pressedBackgroundColor;
	private Color pressedBorderColor;
	
	protected GUIButtonObject() {
		name = "GUIButtonObject";
	}
	
	@Override
	protected void render(Graphics2D g) {
		if(!visible)return;
		//g.translate(-g.getTransform().getTranslateX(), -g.getTransform().getTranslateY());
        g.rotate(rotationAngle, x + width / 2, y + height / 2);

		g.setColor(pressed ? pressedBackgroundColor : (hovered ? hoveredBackgroundColor : backgroundColor));
		if(cornerRadius<1) {
			g.fillRect(x, y, width, height);
			g.setColor(pressed ? pressedBorderColor : (hovered ? hoveredBorderColor : borderColor));
			g.setStroke(borderStroke);
			g.drawRect(borderX, borderY, borderWidth, borderHeight);

		}else {
			g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
			g.setColor(pressed ? pressedBorderColor : (hovered ? hoveredBorderColor : borderColor));
			g.setStroke(borderStroke);
			g.drawRoundRect(borderX, borderY, borderWidth, borderHeight, cornerRadius, cornerRadius);

		}
		
		children.render(g);
		
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
	}	
	
	protected boolean contains(int tx, int ty) {
		if(tx >= x && ty >= y && tx <= width && ty <= height) {
			return true;
		}
		
		return false;
	}

}
