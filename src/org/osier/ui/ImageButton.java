package org.osier.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.osier.util.Logger;

public class ImageButton extends ImageLabel implements GUIButtonListener {
	//TODO override ImageLabels updateTileImage() method and updateFittedImage() methods
	private Color hoverBackgroundColor;
	private Color hoverBorderColor;
	private boolean hovered;
	private BufferedImage hoverImage;
	
	public ImageButton() {
		name = "ImageButton";
		hoverBackgroundColor = Color.gray;
		hoverBorderColor = Color.DARK_GRAY;
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!visible)return;
		
        g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
		g.setColor(hovered ? hoverBackgroundColor : backgroundColor);
		if(cornerRadius<1) {
			g.fillRect(x, y, width, height);
		}else {
			g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
		}
		
		if(hovered) {
			if (hoverImage != null) {
			    if (scaleType == STRETCH) {
			        g.drawImage(hoverImage, x, y, width, height, null);
			    }else if (scaleType == FIT) {                    
			        g.drawImage(hoverImage, imageX, imageY, imageWidth,imageHeight, null);
			    } else if (scaleType == TILE) {
			        g.drawImage(tiledImage, x, y, width, height,null);
			    }
			}
		}else {
			if (image != null) {
			    if (scaleType == STRETCH) {
			        g.drawImage(image, x, y, width, height, null);
			    }else if (scaleType == FIT) {                    
			        g.drawImage(image, imageX, imageY, imageWidth,imageHeight, null);
			    } else if (scaleType == TILE) {
			        g.drawImage(tiledImage, x, y, width, height,null);
			    }
			}
		}
		

		 
		g.setColor(hovered ? hoverBorderColor : borderColor);
		g.setStroke(borderStroke);
		if(cornerRadius<1) {
			g.drawRect(borderX, borderY, borderWidth, borderHeight);
		}else {
			g.drawRoundRect(borderX, borderY, borderWidth, borderHeight, cornerRadius, cornerRadius);
		}
		
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
		
		children.render(g);
	}
	
	
	public BufferedImage getHoverImage() {
		return hoverImage;
	}
	public void setHoverImage(BufferedImage image) {
		hoverImage = image;
	}
	public void loadHoverImageFromFile(String path) {
	    loaded = false;

	    try {
	        BufferedImage loadedImage = ImageIO.read(new File(path));

	        if (loadedImage != null) {
	        	hoverImage = loadedImage;
	            loaded = true;
	        }
	    } catch (IOException e) {
	        Logger.log("Failed to load image from file: " + path);
	    }
	}
	
	public void loadHoverImageFromURL(String urlString) {
	    loaded = false;

	    try {
	        URI uri = new URI(urlString);
	        URL url = uri.toURL();
	        BufferedImage loadedImage = ImageIO.read(url);

	        if (loadedImage != null) {
	            hoverImage = loadedImage;
	            loaded = true;
	        }
	    } catch (URISyntaxException | IOException e) {
	        Logger.log("Failed to load image from URL: " + urlString);
	    }
	}
	public boolean isHovered() {
		return hovered;
	}
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	public Color getHoverBorderColor() {
		return hoverBorderColor;
	}
	public void setHoverBorderColor(Color hoverBorderColor) {
		this.hoverBorderColor = hoverBorderColor;
	}
	public Color getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}
	public void setHoverBackgroundColor(Color hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}
	public boolean contains(int mx, int my) {
		if(mx >= x && my >= y && mx <= width && my <= height) {
			return true;
		}
		return false;
	}
	

	
}
