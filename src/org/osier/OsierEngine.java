package org.osier;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.osier.listeners.CoreListener;
import org.osier.ui.Window;
import org.osier.util.Logger;

public class OsierEngine implements CoreListener {
	
	/*
	 * TODO
	 */

	public Window window;
	private Thread renderThread;
	private GraphicsDevice display;

	private int displayWidth;
	private int displayHeight;
	private int fps;
	private float frameRateSetting;
	private float frameRate;
	private float frameTime;
	private boolean shouldRender;
	private boolean vsyncEnabled;
	private boolean running;
	private final long SECOND_NANOS = 1000000000L;

	
	public OsierEngine(String title, int width, int height, boolean decorated) {
		this.display = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		this.displayWidth = display.getDisplayMode().getWidth();
		this.displayHeight = display.getDisplayMode().getHeight();
		this.frameRate = display.getDisplayMode().getRefreshRate();
		this.frameRateSetting = frameRate;
		this.frameTime = 1.0F / frameRate;
		this.vsyncEnabled = true;
		
		renderThread = new Thread() {
			public void run() {
				int frames = 0;
         		long frameCounter = 0;
         		long lastTime = System.nanoTime();
         		double unprocessedTime = 0;
         		long startTime;
         		long elapsedTime;
         		int refreshRate;

        		window = new Window(title, width, height, decorated) {
        			public void mouseClicked(MouseEvent e, boolean processed) {
    					OsierEngine.this.mouseClicked(e, processed);
        			}
        			public void mousePressed(MouseEvent e, boolean processed) {
    					OsierEngine.this.mousePressed(e, false);
        			}
        			public void mouseReleased(MouseEvent e, boolean processed) {
    					OsierEngine.this.mouseReleased(e, false);
        			}
        			public void mouseEntered(MouseEvent e) {
    					OsierEngine.this.mouseEntered(e);
        			}
        			public void mouseExited(MouseEvent e) {
    					OsierEngine.this.mouseExited(e);
        			}
        			public void mouseMoved(MouseEvent e, boolean isDragging) {
    					OsierEngine.this.mouseMoved(e, isDragging);
        			}
        			public void keyPressed(KeyEvent e) {
    					OsierEngine.this.keyPressed(e, false);
        			}
        			public void keyReleased(KeyEvent e) {
    					OsierEngine.this.keyReleased(e, false);
        			}
        			public void windowResized(int width, int height) {
    	    		    OsierEngine.this.windowResized(window.getWidth(),window.getHeight());
        			}
        			public void windowClosing() {
        				OsierEngine.this.stop();
        			}
        			public void draw(Graphics2D g) {
        				OsierEngine.this.render(g);
        			}
        		};
        		window.enable();
        		window.setResizable(true);
         		load();
        		window.setName("MainWindow");

         		while(running) {
         			shouldRender = false;
         			startTime = System.nanoTime();
         			elapsedTime = startTime - lastTime;
         			lastTime = startTime;
         			unprocessedTime += elapsedTime / (double) SECOND_NANOS; 
         			frameCounter += elapsedTime;
         			
         			//handle vsync updates
         			refreshRate = display.getDisplayMode().getRefreshRate();
         			if(vsyncEnabled && refreshRate != frameRate) {
         				frameRate = refreshRate;
         				frameTime = 1.0f/refreshRate;
         			}
         			
         			//handle input
         			window.pollInput();
         			
         			//handle frame rate         			
         			while(unprocessedTime > frameTime) {
         				shouldRender = true;
         				unprocessedTime -= frameTime;
         				if(frameCounter >= SECOND_NANOS) {
         					fps = frames;
         					System.out.println(fps);
         					frames = 0;
         					frameCounter=0;
         				}
         			}
         			
         			//handle update/render
         			if(shouldRender) {
         				update((float) frameTime);
         				window.render();
         				frames++;
         			}
         		}
			}
		};
	}
	
	
	
	public void start() {
		if(running)return;
		running = true;
		renderThread.start();
	}
	
	public void stop() {
		running = false;
		try {
			renderThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dispose();
		System.exit(0);
	}
	
	public Window getWindow() {
		return window;
	}
	
	public void setVSyncEnabled(boolean bool) {
		vsyncEnabled = bool;
		if(bool==true) {
			frameRate = display.getDisplayMode().getRefreshRate();
		}else {
			frameRate = frameRateSetting;
		}
		frameTime = 1.0f / frameRate;
	}
	
	public boolean isVSyncEnabled() {
		return vsyncEnabled;
	}
	
	public void setFrameRate(int rate) {
		frameRateSetting = rate;
		if(!vsyncEnabled) {
			frameRate = rate;
			frameTime = 1.0f / rate;
		}
	}
	
	public float getFrameRate() {
		return frameRate;
	}
	
	public float getFrameRateSetting() {
		return frameRateSetting;
	}
	
	public float getFrameTime() {
		return frameTime;
	}
	
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	public int getDisplayHeight() {
		return displayHeight;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(new File(path));
		}catch(IOException e) {
			e.printStackTrace();
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
		
	
}