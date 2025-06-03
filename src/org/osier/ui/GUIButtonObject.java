package org.osier.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osier.listeners.GUIButtonListener;

public class GUIButtonObject extends GUIObject implements GUIButtonListener {
	
	protected boolean hovered;
	protected boolean pressed;
	
	protected Color hoveredBackgroundColor, hoveredBorderColor, pressedBackgroundColor, pressedBorderColor;
	
	protected GUIButtonObject() {
		name = "GUIButtonObject";
		hoveredBackgroundColor = Color.white.darker().darker();
		hoveredBorderColor = Color.black.brighter().brighter();
		pressedBackgroundColor = Color.white.darker();
		pressedBorderColor = Color.black.brighter();
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
		
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);

		children.render(g);
	}	
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setHoveredBackgroundColor(Color color) {
		hoveredBackgroundColor = color;
	}
	public void setPressedBackgroundColor(Color color) {
		pressedBackgroundColor = color;
	}
	public void setHoveredBorderColor(Color color) {
		hoveredBorderColor = color;
	}
	public void setPressedBorderColor(Color color) {
		pressedBorderColor = color;
	}
	
	protected boolean contains(int tx, int ty) {
		if(tx >= x && ty >= y && tx <= x+width && ty <= y+height) {
			return true;
		}
		
		return false;
	}

}
