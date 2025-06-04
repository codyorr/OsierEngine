package org.osier;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.osier.listeners.CoreListener;
import org.osier.ui.Window;

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
         				update();
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
		
	
}