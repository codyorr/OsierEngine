package org.osier.ui;


import java.awt.Graphics2D;
import java.util.List;

import org.osier.math.Vector2;


public class ScrollGrid extends GUIObject {
	

	private ScrollCanvas canvas;
	private ScrollBar horizontalBar;
	private ScrollBar verticalBar;
	private float canvasScaleX, canvasScaleY, canvasPaddingScaleX, canvasPaddingScaleY, cellSpacingScaleX, cellSpacingScaleY, cellSizeScaleX, cellSizeScaleY;
	private int canvasPaddingOffsetX, canvasPaddingOffsetY, cellSpacingOffsetX, cellSpacingOffsetY, cellSizeOffsetX, cellSizeOffsetY;
	private int cellWidth, cellHeight, gridWidth, gridHeight, canvasX, canvasY;
	private boolean gridEnabled;
	
	public ScrollGrid() {
		name = "ScrollGrid";
		
		canvas = new ScrollCanvas() {
			protected void update() {
				updateCanvas();
			}
		};
		canvas.setParent(this);
		canvas.setSize(0, 1, 0, 2);
		canvas.setPosition(0, 0, 0, 0);
		canvasScaleX = 1;
		canvasScaleY = 2;
		
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
		if(obj instanceof ScrollBar) 
			this.children.add(obj);
		else
			canvas.children.add(obj);
		
		obj.parent = this;
	}
	
	@Override
	protected void remove(GUIObject obj) {
		if(obj instanceof ScrollBar) 
			this.children.remove(obj);
		else
			canvas.children.remove(obj);
		
		obj.parent = null;
	}
	
	public void updateCanvas() {
		Graphics2D g = canvas.createGraphics();
		g.clearRect(0, 0, width, height);
		canvas.setPosition(canvasPaddingOffsetX, canvasPaddingScaleX, canvasPaddingOffsetY, canvasPaddingScaleY);
		canvas.setSize(canvas.width-canvasPaddingOffsetX, 0, canvas.height-canvasPaddingOffsetY, 0, false);
		int length = canvas.children.list.size() - 1;
		
		if(gridEnabled) {
			int index = 0;
			for(int y= 0; y < gridHeight; y++) {
				for(int x = 0; x < gridWidth; y++) {
					GUIObject obj = canvas.children.get(index);
					obj.setSize(cellWidth, 0, cellHeight, 0);
					obj.setPosition((int)(cellWidth*x)+cellSpacingOffsetX, 0, (int)(cellWidth*y)+cellSpacingOffsetY, 0);
					obj.render(g);
					
					if(index<length) {
						index++;
					}else {
						g.dispose();
						return;
					}
				}
			}
		}else  {
			for(int i = 0; i < length; i++) {
				canvas.children.get(i).render(g);
			}
		}
		g.dispose();
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
		
		cellWidth = canvas.width / width;
		cellHeight = canvas.height / height;
	}
	
	public Vector2 getGridSize() {
		return new Vector2(gridWidth, gridHeight);
	}
	
	public void setCanvasSize(float x, float y) {
		canvasScaleX = x;
		canvasScaleY = y;
		
	}
	
	
	public void setPadding(int paddingX, int paddingY) {
		this.canvasPaddingOffsetX = paddingX;
		this.canvasPaddingOffsetY = paddingY;
	}
	
	public void setSpacing(int spacingX, int spacingY) {
		this.cellSpacingOffsetX = spacingX;
		this.cellSpacingOffsetY = spacingY;
		if(gridEnabled) {
			updateCanvas();
		}
	}
	
	protected void scrollX(int dx) {
		canvas.x += dx;
	}
	
	protected void scrollY(int dy) {
		canvas.y += dy;
	}
	
	
}
