package org.osier.ui;

import java.awt.Graphics2D;

import org.osier.util.Logger;

public class ScrollGrid extends GUIObject {
	
	private ScrollBar horizontalBar,verticalBar;
	private ImageLabel canvas;
	private int gridWidth, gridHeight;
	private boolean useGrid;
	
	public ScrollGrid() {
		this.name = "ScrollGrid";
		this.clipDescendants = true;
		this.horizontalBar = new ScrollBar();
		this.horizontalBar.name = "HorizontalBar";
		this.verticalBar = new ScrollBar();
		this.verticalBar.name = "VerticalBar";
		
		this.canvas = new ImageLabel();
		canvas.setSize(0, 1, 0, 2);
		canvas.setParent(this);
	}
	
	protected void render(Graphics2D g) {
		if(!visible)return;
		//g.translate(-g.getTransform().getTranslateX(), -g.getTransform().getTranslateY());
        //g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
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
						
        //g.rotate(-rotationAngle, x + width / 2, y + height / 2);
        
		children.render(g);
		
		g.setClip(null);
	}
	
	
	public void setParent(BaseGUIObject obj) {
		if(obj == this) {
			Logger.log("Cannot parent a GUIObject to itself.");
			return;
		}
		if(parent!=null) {
			parent.children.remove(this);
			if(obj == null) {
				BaseGUIObject p = parent;
				while(true) {
					if(p instanceof Window) {
						Window window = (Window) p;
						window.updateButtons();
						break;
					}else if(p instanceof BlockingDialog) {
						BlockingDialog dialog = (BlockingDialog) p;
						dialog.updateButtons();
						break;
					}else {
						GUIObject guiObject = (GUIObject)p;
						p = guiObject.parent;
					}
				}
			}
			parent = null;
		}
		if(obj!=null) {
			obj.children.add(this);
			if(obj instanceof Window) {
				Window window = (Window) obj;
				window.updateButtons();
			}else if(obj instanceof BlockingDialog) {
				BlockingDialog dialog = (BlockingDialog) obj;
				dialog.updateButtons();
			}
			parent = obj;
			setSize(sizeOffsetX, sizeScaleX, sizeOffsetY, sizeScaleY);
		}
	}
		

}
