package org.osier.ui;


import java.awt.Graphics2D;

import org.osier.math.Vector2;


public class ScrollGrid extends GUIObject {
	

	private ScrollCanvas canvas;
	private ScrollBar horizontalBar;
	private ScrollBar verticalBar;
	private float canvasPaddingScaleX, canvasPaddingScaleY, cellPaddingScaleX, cellPaddingScaleY, cellSizeScaleX, cellSizeScaleY;
	private int canvasPaddingOffsetX, canvasPaddingOffsetY, cellPaddingOffsetX, cellPaddingOffsetY, cellSizeOffsetX, cellSizeOffsetY;
	private int cellDeltaX, cellDeltaY, cellWidth, cellHeight, gridWidth, gridHeight;
	private boolean gridEnabled;
	
	public ScrollGrid() {
		name = "ScrollGrid";
		
		canvas = new ScrollCanvas();
		canvas.setParent(this);
		canvas.setSize(0, 1, 0, 2);
		canvas.setPosition(0, 0, 0, 0);
		
		verticalBar = new ScrollBar();
		verticalBar.setParent(this);
		
		horizontalBar = new ScrollBar();
		horizontalBar.setParent(this);		
	}
	
	@Override
	protected void render(Graphics2D g) {
		if(!visible)return;
       // g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
        if(clipDescendants) {
        	g.setClip(clipShape);
        }
        
		g.setColor(backgroundColor);
		if(cornerRadius<1) {
			g.fillRect(x, y, width, height);
			g.setColor(borderColor);
			g.setStroke(borderStroke);
			g.drawRect(borderX, borderY, borderWidth, borderHeight);
		}else {
			g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
			g.setColor(borderColor);
			g.setStroke(borderStroke);
			g.drawRoundRect(borderX, borderY, borderWidth, borderHeight, cornerRadius, cornerRadius);
		}
		
		canvas.render(g);
			
		if(verticalBar!=null) 
			verticalBar.render(g);
		if(horizontalBar!=null)
			horizontalBar.render(g);
        //g.rotate(-rotationAngle, x + width / 2, y + height / 2);
        
		//children.render(g);
		
		g.setClip(null);
	}
	
	
	@Override
	protected void add(GUIObject obj) {
		canvas.children.add(obj);
		obj.parent = this;
	}
	
	@Override
	protected void remove(GUIObject obj) {
		canvas.children.remove(obj);
		obj.parent = null;
	}
	
	public boolean isGridEnabled() {
		return gridEnabled;
	}
	
	public void enableGrid(boolean enabled) {
		gridEnabled = enabled;
	}
	
	public void setGridSize(int width, int height) {
		gridWidth = width;
		gridHeight = height;
	}
	
	public Vector2 getGridSize() {
		return new Vector2(gridWidth, gridHeight);
	}
	
	
}
