package org.osier.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
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
	
	
	protected void updateChildGraphics() {
		Graphics2D g = (Graphics2D) image.createGraphics();
		g.clearRect(0, 0, width, height);
		for(int i = 0; i < children.list.size(); i++) {
			children.get(i).render(g);
		}
		g.dispose();
	}
	
	protected Graphics2D createGraphics() {
		return (Graphics2D) image.createGraphics();
	}
	
}
