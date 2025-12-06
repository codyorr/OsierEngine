package org.osier.ui;

import java.awt.Graphics2D;

public class ScrollGrid extends GUIObject {
	
	private ScrollCanvas canvas;
	private ScrollBar horizontalBar;
	private ScrollBar verticalBar;
	private boolean gridEnabled;
	private int gridWidth, gridHeight;
	private int cellSizeOffsetX, cellSizeOffsetY;
	private float cellSizeScaleX, cellSizeScaleY;
	
	public ScrollGrid() {
		this.name = "ScrollGrid";
		this.gridEnabled = false;
		
		this.canvas = new ScrollCanvas();
		canvas.setSize(1,0,1,0);
		canvas.setPosition(0, 0, 0, 0);
		
		this.horizontalBar = new ScrollBar();
		horizontalBar.setVisible(false);
		children.add(horizontalBar);
		horizontalBar.parent = this;
		horizontalBar.setSize(0, 12, 0, 12);
		horizontalBar.setPosition(0, 0, 1, -12);
		
		
		this.verticalBar = new ScrollBar();
		children.add(verticalBar);
		verticalBar.parent = this;
		verticalBar.setSize(0,12,0,12);
		verticalBar.setPosition(1, -12, 0, 0);
	}
	
	public void render(Graphics2D g) {
		if(!visible) {
			return;
		}
		g.setClip(clipShape);
		canvas.render(g);
		horizontalBar.render(g);
		verticalBar.render(g);
		g.setClip(null);
	}
	
	protected void add(GUIObject obj) {
		canvas.children.add(obj);
		obj.parent = canvas;
	}
	
	public void setGridSize(int width, int height) {
		gridWidth = width;
		gridHeight = height;
		if(gridEnabled) {
			//reposition all canvas children;
		}
	}
	
	public void setCellSize(int ox, float sx, int oy, float sy) {
		cellSizeOffsetX = ox;
		cellSizeOffsetY = oy;
		cellSizeScaleX = sx;
		cellSizeScaleY = sy;
		if(gridEnabled) {
			//resize all children
		}
	}
	
	public void setCanvasSize(int ox, float sx, int oy, float sy) {
		canvas.setSize(ox, sx, oy, sy);
	}
}
