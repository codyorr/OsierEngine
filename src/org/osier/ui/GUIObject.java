package org.osier.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import org.osier.math.ScaledDimension;
import org.osier.math.Vector2;
import org.osier.util.Logger;

public class GUIObject extends BaseGUIObject {
	
	protected int displayOrder;
	protected Color backgroundColor;
	protected Color borderColor;
	protected int posOffsetX,posOffsetY,sizeOffsetX,sizeOffsetY;
	protected float posScaleX,posScaleY,sizeScaleX,sizeScaleY;
	protected int borderSize, borderX,borderY,borderWidth,borderHeight;
	protected BasicStroke borderStroke;
	protected float originX,originY;
	protected int cornerRadius;
	protected BaseGUIObject parent;
	protected boolean visible;
	protected double rotationAngle;
	protected boolean clipDescendants;
	protected Shape clipShape;
	
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
		clipShape = new Rectangle(x,y,width,height);
		visible =  true;
	}

	
	protected void render(Graphics2D g) {
		if(!visible)return;
		//g.translate(-g.getTransform().getTranslateX(), -g.getTransform().getTranslateY());
        g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
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
				
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
        
		children.render(g);
		
		g.setClip(null);
	}	
	
	public void setPosition(int offsetX, float scaleX, int offsetY, float scaleY) {
		posOffsetX = offsetX;
		posOffsetY = offsetY;
		posScaleX = scaleX;
		posScaleY = scaleY;
		
		if(parent==null)return;

		x = parent.x + (int)Math.round((parent.width*scaleX) - (originX*width)) + offsetX;
		y = parent.y + (int)Math.round((parent.height*scaleY) - (originY*height)) + offsetY;
		if(cornerRadius > 0) {
			clipShape = new RoundRectangle2D.Double(x,y,width,height,cornerRadius,cornerRadius);
		}else {
			clipShape = new Rectangle(x,y,width,height);
		}
		setBorderSize(borderSize);
		children.updatePositions();
	}

	public void setSize(int offsetX, float scaleX, int offsetY, float scaleY) {
		sizeOffsetX = offsetX;
		sizeOffsetY = offsetY;
		sizeScaleX = scaleX;
		sizeScaleY = scaleY;
		
		if(parent==null)return;
		
		width = (int)Math.round(parent.width * scaleX) + offsetX;
		height = (int)Math.round(parent.height * scaleY) + offsetY;
		setBorderSize(borderSize);
		children.updateSizes();
		updatePosition();		
	}
	
	public void setOrigin(float scaleX, float scaleY) {
		originX = scaleX;
		originY = scaleY;
		updatePosition();
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
	
	public void setCornerRadius(int cornerRadius) {
		this.cornerRadius = cornerRadius;
		if(cornerRadius > 0) {
			clipShape = new RoundRectangle2D.Double(x,y,width,height,cornerRadius,cornerRadius);
		}
	}
	
	public int getCornerRadius() {
		return cornerRadius;
	}
	
	public void setClipDescendants(boolean clipDescendants) {
		this.clipDescendants = clipDescendants;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BaseGUIObject> T getParent() {
		return (T) parent;
	}
	
	protected void updateSize() {
		setSize(sizeOffsetX, sizeScaleX, sizeOffsetY, sizeScaleY);
	}
	
	protected void updatePosition() {
		setPosition(posOffsetX, posScaleX, posOffsetY, posScaleY);
	}
	
	public ScaledDimension getSize() {
		return new ScaledDimension(sizeOffsetX,sizeScaleX,sizeOffsetY,sizeScaleY);
	}
	
	public ScaledDimension getPosition() {
		return new ScaledDimension(posOffsetX,posScaleX,posOffsetY,posScaleY);
	}
	
	public Vector2 getAbsoluteSize() {
		return new Vector2(width,height);
	}
	
	public Vector2 getAbsolutePosition() {
		return new Vector2(x,y);
	}
	
	public boolean getClipDescendants() {
		return clipDescendants;
	}
	
	public int getDisplayOrder() {
		return displayOrder;
	}
	
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public Color getBorderColor() {
		return borderColor;
	}
	
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public int getBorderSize() {
		return borderSize;
	}
	
	public void setBorderSize(int borderSize) {
		if(borderSize < 0) {
			borderSize = 0;
		}
		this.borderSize = borderSize;
		borderStroke = new BasicStroke(borderSize);
		borderX = x - (borderSize/2);
		borderY = y - (borderSize/2);
		borderWidth = width +  borderSize/2;
		borderHeight = height + borderSize/2;
	}
	
	public void setRotationAngle(double angle) {
		rotationAngle = angle;
	}
	
	public double getRotationAngle() {
		return rotationAngle;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	

}
