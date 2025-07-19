package org.osier.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ScrollCanvas extends GUIObject {
	
	
	private BufferedImage image;
	protected ScrollCanvas() {
		name = "GUICanvas";
		image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.dispose();
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!visible)return;
		//g.translate(-g.getTransform().getTranslateX(), -g.getTransform().getTranslateY());
       // g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
        if(clipDescendants) {
        	g.setClip(clipShape);
        }
        
		/*g.setColor(backgroundColor);
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

		}*/
		
		g.drawImage(image, x, y, width, height, null);
				
       // g.rotate(-rotationAngle, x + width / 2, y + height / 2);
        
        
		g.setClip(null);
	}
	
	
	public void setPosition(int offsetX, float scaleX, int offsetY, float scaleY, boolean scaleChildren) {
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
		//setBorderSize(borderSize);
		if(scaleChildren) {
			children.updatePositions();
		}
	}
	
	public void setSize(int offsetX, float scaleX, int offsetY, float scaleY, boolean scaleChildren) {
		sizeOffsetX = offsetX;
		sizeOffsetY = offsetY;
		sizeScaleX = scaleX;
		sizeScaleY = scaleY;
		if(parent==null)return;
		width = (int)Math.round(parent.width * scaleX) + offsetX;
		height = (int)Math.round(parent.height * scaleY) + offsetY;
		if(scaleChildren) {
			children.updateSizes();
		}
		
		posOffsetX = offsetX;
		posOffsetY = offsetY;
		posScaleX = scaleX;
		posScaleY = scaleY;
		x = parent.x + (int)Math.round((parent.width*scaleX) - (originX*width)) + offsetX;
		y = parent.y + (int)Math.round((parent.height*scaleY) - (originY*height)) + offsetY;
		if(cornerRadius > 0) {
			clipShape = new RoundRectangle2D.Double(x,y,width,height,cornerRadius,cornerRadius);
		}else {
			clipShape = new Rectangle(x,y,width,height);
		}
		//setBorderSize(borderSize);
		if(scaleChildren) {
			children.updatePositions();
		}
		
	}
	
	
	protected void update() {
		Graphics2D g = (Graphics2D) image.createGraphics();
		g.clearRect(0, 0, width, height);
		for(int i = 0; i < children.list.size(); i++) {
			children.get(i).render(g);
		}
		g.dispose();
	}
	
	protected void offsetCanvas(int x, int y) {
		this.x+=x;
		this.y+=y;
	}
	
	protected Graphics2D createGraphics() {
		return (Graphics2D) image.createGraphics();
	}
	
}
