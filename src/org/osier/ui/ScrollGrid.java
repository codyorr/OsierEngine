package org.osier.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.osier.math.ScaledDimension;

public class ScrollGrid extends GUIObject {
	

	private BufferedImage canvas;
	private ScrollBar horizontalBar;
	private ScrollBar verticalBar;
	private float canvasScaleX,canvasScaleY;
	private int canvasWidth,canvasHeight, canvasPaddingX,canvasPaddingY;
	private float cellScaleX, cellScaleY;
	private int cellWidth,cellHeight, cellPaddingX, cellPaddingY;
	private int gridWidth, gridHeight;
	private boolean gridEnabled;
	
	public ScrollGrid() {
		name = "ScrollGrid";
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
		clipShape = new Rectangle(x,y,width,height);
		
		canvasScaleX = 1;
		canvasScaleY = 2;
		canvasWidth = 75;
		canvasHeight = 100;
		canvasPaddingX = 0;
		canvasPaddingY = 0;
		canvas = new BufferedImage(canvasWidth,canvasHeight, BufferedImage.TYPE_INT_ARGB);
		
		verticalBar = new ScrollBar();
		verticalBar.setParent(this);
		
		horizontalBar = new ScrollBar();
		horizontalBar.setParent(this);
		
		visible =  true;
	}
	
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
		
		g.drawImage(canvas, canvasPaddingX, canvasPaddingY, canvasWidth,canvasHeight, null);
				
		verticalBar.render(g);
        //g.rotate(-rotationAngle, x + width / 2, y + height / 2);
        
		//children.render(g);
		
		g.setClip(null);
	}
	
	@Override
	public void setSize(int ox, float sx, int oy, float sy) {
		super.setSize(ox, sx, oy, sy);
		canvasWidth = (int)(width*canvasScaleX);
		canvasHeight = (int)(height*canvasScaleY);
	}
	
	public void setCanvasSize(float sx, float sy) {
		canvasScaleX=x;
		canvasScaleY=y;
		canvasWidth = (int)(width*canvasScaleX)-canvasPaddingX;
		canvasHeight = (int)(height*canvasScaleY)-canvasPaddingY;
	}
	
	public void setCanvasPadding(int px, int py) {
		canvasPaddingX = px;
		canvasPaddingY = py;
		canvasWidth = (int)(width*canvasScaleX) - px;
		canvasHeight = (int)(height*canvasScaleY) - py;
	}
	
	
	public void enableGrid(boolean enabled) {
		gridEnabled = enabled;
	}
	
	public boolean isGridEnabled() {
		return gridEnabled;
	}
	
	public void setGridSize(int width, int height) {
		gridWidth = width;
		gridHeight = height;
	}
	
	public void setCellSize(int ox, float sx, int oy, float sy) {
		cellScaleX = sx;
		cellScaleY = sy;
		cellWidth = (int)(canvasWidth*sx)+ox;
		cellHeight = (int)(canvasHeight*sy)+oy;
	}
	
	private void updateCanvas() {
		//TODO decide whether or not to change image size or just scale it in the drawImage method
		//canvas = new BufferedImage(canvasWidth,canvasHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) canvas.getGraphics();
		
		
		if(gridEnabled) {
			int childIndex = 0;
			int childCount = children.list.size();
			for(int y = 0; y < gridHeight; y++) {
				for(int x = 0; x < gridWidth; x++) {
					if(childIndex < childCount) {
						children.get(childIndex).render(g);
					}
				}
			}
		} 
		else {
			
		}
		
		g.dispose();
	}
		

}
