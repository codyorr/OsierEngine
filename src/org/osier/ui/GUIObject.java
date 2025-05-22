package org.osier.ui;

import java.awt.BasicStroke;
import java.awt.Color;

public class GUIObject extends BaseGUIObject {
	
	protected int displayOrder;
	protected Color backgroundColor;
	protected Color borderColor;
	protected int posOffsetX, posOffsetY, sizeOffsetX, sizeOffsetY;
	protected float posScaleX, posScaleY, sizeScaleX, sizeScaleY;
	protected int borderSize, borderX, borderY, borderWidth, borderHeight;
	protected BasicStroke borderStroke;
	protected float originX, originY;
	protected int cornerRadius;
	protected BaseGUIObject parent;
	protected boolean visible;
	protected double rotationAngle;
	protected boolean clipDescendants;
	
	protected GUIObject() {
		name = "GUIObject";
		backgroundColor = Color.white;
		borderColor = Color.black;
		width = 75;
		height = 50;
		sizeOffsetX = 75;
		sizeOffsetY = 50;
		borderSize = 1;
		borderStroke = new BasicStroke(borderSize);
		borderX = x - (borderSize/2);
		borderY = y - (borderSize/2);
		borderWidth = width +  borderSize/2;
		borderHeight = height + borderSize/2;
		visible =  true;
	}
	
	
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	

}
