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

public class ImageLabel extends GUIObject {
	
	public static final int FIT = 1;
	public static final int STRETCH = 2;
	public static final int TILE = 3;
	
	protected BufferedImage image,tiledImage;
	protected int scaleType,tileWidth,tileHeight;
	
	//FIT
	protected int imageX,imageY,imageWidth,imageHeight;
	protected double imageAspect,canvasAspect;
	
	//TILE
	protected int gridWidth,gridHeight;
	protected boolean loaded;
	
	public ImageLabel() {
		super();
		name = "ImageLabel";
		image = new BufferedImage(75,50,BufferedImage.TYPE_INT_ARGB);
		gridWidth = 1;
		gridHeight = 1;
		tileWidth = width;
		tileHeight = height;
		scaleType = STRETCH;
	}
	
	
	@Override
	protected void render(Graphics2D g) {
		if(!visible)return;
		
        g.rotate(rotationAngle, x + width / 2, y + height / 2);

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
		    if (scaleType == STRETCH) {
		        g.drawImage(image, x, y, width, height, null);
		    }else if (scaleType == FIT) {                    
		        g.drawImage(image, imageX, imageY, imageWidth,imageHeight, null);
		    } else if (scaleType == TILE) {
		        g.drawImage(tiledImage, x, y, width, height,null);
		    }
		}
		
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
		
		children.render(g);
	}
	
	
	  
	@Override
	public void setPosition(int ox, float sx, int oy, float sy) {
		super.setPosition(ox, sx, oy, sy);
		if(image==null) return;
		updateImage();
	}
	
	
	private void updateImage() {
		if(scaleType == FIT) {
			updateFittedImage();
		}else if(scaleType == TILE) {
			updateTiledImage();
		}
	}
	
	protected void updateFittedImage() {
	    canvasAspect = (double) width / height;
	    imageAspect = (double) image.getWidth(null) / image.getHeight(null);

	    // Determine if the image is larger than the background
	    boolean imageLarger = image.getWidth(null) > width || image.getHeight(null) > height;

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
				g.drawImage(image, j*tileWidth,i*tileHeight,tileWidth,tileHeight,null);
			}
		}
		g.dispose();
	}
	
	
	public void setTileSize(int w, int h) {
		gridWidth = w;
		gridHeight = h;
		if(image == null || scaleType!=TILE) return;
		updateTiledImage();
	}
	
	public void setScaleType(int type) {
		scaleType = type;
		if(image==null) return;
		updateImage();
	}

	public void setImage(BufferedImage img) {
		loaded=false;
		image = img;
		loaded=true;
		if(image==null) return;
		updateImage();
	}

	public void loadImageFromFile(String path) {
	    loaded = false;

	    try {
	        BufferedImage loadedImage = ImageIO.read(new File(path));

	        if (loadedImage != null) {
	        	image = loadedImage;
	            updateImage();
	            loaded = true;
	        }
	    } catch (IOException e) {
	        Logger.log("Failed to load image from file: " + path);
	    }
	}
	
	public void loadImageFromURL(String urlString) {
	    loaded = false;

	    try {
	        URI uri = new URI(urlString);
	        URL url = uri.toURL();
	        BufferedImage loadedImage = ImageIO.read(url);

	        if (loadedImage != null) {
	            image = loadedImage;
	            updateImage();
	            loaded = true;
	        }
	    } catch (URISyntaxException | IOException e) {
	        Logger.log("Failed to load image from URL: " + urlString);
	    }
	}
	
	public void setRotationAngle(double angle) {
		rotationAngle = angle;
	}
	
	public double getRotationAngle() {
		return rotationAngle;
	}
	 
	public int getScaleType() {
		return scaleType;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public boolean isLoaded() {
		return loaded;
	}
}
