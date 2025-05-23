package org.osier.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	private BufferedImage selectedImage;
	public ImageButton() {
		name = "ImageButton";
		hoverBackgroundColor = Color.gray;
		hoverBorderColor = Color.DARK_GRAY;
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!visible)return;
		
        g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
        g.setColor(backgroundColor);
		if(cornerRadius<1) {
			g.fillRect(x, y, width, height);
			g.setColor(borderColor);
			g.setStroke(borderStroke);
			g.drawRect(borderX, borderY, borderWidth, borderHeight);

		} else {
			g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
			g.setColor(borderColor);
			g.setStroke(borderStroke);
			g.drawRoundRect(borderX, borderY, borderWidth, borderHeight, cornerRadius, cornerRadius);

		}
		
		/*if(hovered) {
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
		}*/
		
		if (selectedImage != null) {
		    if (scaleType == STRETCH) {
		        g.drawImage(selectedImage, x, y, width, height, null);
		    }else if (scaleType == FIT) {                    
		        g.drawImage(selectedImage, imageX, imageY, imageWidth,imageHeight, null);
		    } else if (scaleType == TILE) {
		        g.drawImage(tiledImage, x, y, width, height,null);
		    }
		}
		
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
		
		children.render(g);
	}
	
	
	@Override
	protected void updateFittedImage() {
	    canvasAspect = (double) width / height;
	    imageAspect = (double) selectedImage.getWidth(null) / selectedImage.getHeight(null);

	    // Determine if the selectedImage is larger than the background
	    boolean imageLarger = selectedImage.getWidth(null) > width || selectedImage.getHeight(null) > height;

	    if (canvasAspect > imageAspect) {
	        imageHeight = Math.min(height, imageLarger ? height : (int) (width / imageAspect));
	        imageWidth = (int) (imageHeight * imageAspect);
	    } else {
	        imageWidth = Math.min(width, imageLarger ? width : (int) (height * imageAspect));
	        imageHeight = (int) (imageWidth / imageAspect);
	    }

	    imageX = x + (width - imageWidth) / 2;
	    imageY = y + (height - imageHeight) / 2;
	}
	
	@Override
	protected void updateTiledImage() {
	    int offsetX = (gridWidth - width % gridWidth) % gridWidth;
	    int offsetY = (gridHeight - height % gridHeight) % gridHeight;
	    
	    tileWidth = (width + offsetX) / gridWidth;
	    tileHeight = (height + offsetY) / gridHeight;
		
		tiledImage = new BufferedImage(width+offsetX,height+offsetY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tiledImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridWidth; j++) { 
				g.drawImage(selectedImage, j*tileWidth,i*tileHeight,tileWidth,tileHeight,null);
			}
		}
		g.dispose();
	}
	
	@Override
	public void setImage(BufferedImage image) {
		if(this.image == null && !hovered) {
			selectedImage = image;
		}
		this.image = image;
	}
	
	public BufferedImage getHoverImage() {
		return hoverImage;
	}
	public void setHoverImage(BufferedImage image) {
		if(this.hoverImage == null && hovered) {
			selectedImage = image;
		}
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
		this.selectedImage = hovered ? hoverImage : image;
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
