package org.osier.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.osier.util.Logger;

public class ImageLabel extends GUIObject {
	
	protected BufferedImage image;
	
	protected int imageX,imageY,imageWidth,imageHeight;
	
	public ImageLabel() {
		name = "ImageLabel";
	}
	
	
	@Override
	protected void render(Graphics2D g) {
		if(!visible || parent==null)return;
		
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
		
		if (image != null) {
			g.setClip(clipShape);
	        g.drawImage(image, x, y, width, height, null);
	        g.setClip(null);
		}
		
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
		
		children.render(g);
		
		g.setClip(null);
	}
	
	public void setImage(BufferedImage img) {
		image = img;
	}

	public BufferedImage loadImageFromFile(String path) {
	    try {
	        return  ImageIO.read(new File(path));
	    } catch (IOException e) {
	        Logger.log("Failed to load image from file: " + path);
	    }
	    
	    return null;
	}
	
	public BufferedImage loadImageFromURL(String urlString) {
	    try {
	        URI uri = new URI(urlString);
	        URL url = uri.toURL();
	       return ImageIO.read(url);
	    } catch (URISyntaxException | IOException e) {
	        Logger.log("Failed to load image from URL: " + urlString);
	    }
	    
	    return null;
	}
	
	public BufferedImage getImage() {
		return image;
	}

}
