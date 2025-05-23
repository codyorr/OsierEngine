package org.osier;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.osier.ui.GUIObject;
import org.osier.ui.Window;

public class OsierEngine implements CoreListener {
	
	/*
	 * TODO
	 */

	private Graphics2D g;
	public Window window;
	private BufferStrategy bs;
	private Thread renderThread;
	private GraphicsDevice display;
	private ConcurrentLinkedQueue<Runnable> inputQueue;

	private int displayWidth;
	private int displayHeight;
	private int fps;
	private float frameRateSetting;
	private float frameRate;
	private float frameTime;
	private boolean shouldRender;
	private boolean vsyncEnabled;
	private boolean windowResizing;
	private boolean mouseMoving;
	private boolean running;
	private final long SECOND_NANOS = 1000000000L;
	private GUIObject targetGUIObject;

	
	public OsierEngine(String title, int width, int height, boolean decorated) {
		this.window = new Window(title, width, height, decorated);
		this.window.setName("MainWindow");
		this.inputQueue = new ConcurrentLinkedQueue<Runnable>();
		
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
         			while(!inputQueue.isEmpty()) {
         				inputQueue.poll().run();
         			}
         			
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
         				try {
	         				g = (Graphics2D) bs.getDrawGraphics();
	         				g.clearRect(0, 0, window.getWidth(), window.getHeight());
	         			    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	         				g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	         				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	         				g.translate(window.getInsets().left, window.getInsets().top);
	         			    window.render(g);
	         			    render(g);
	         			    bs.show();
	         			    g.dispose();
     	                }catch(Exception e) {
     	                	//nothing
     	                }
         				frames++;
         			}
         		}
			}
		};
	}
	
	
	
	public void start() {
		if(running)return;
		running = true;
		
		//initiation
		load();
		window.setVisible(true);
		window.createBufferStrategy(2);
        bs = window.getBufferStrategy();

		
		//INPUT EVENTS
		window.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.mouseClicked(e, false);
				});
			}
			public void mousePressed(MouseEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.mousePressed(e, false);
				});
			}
			public void mouseReleased(MouseEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.mouseReleased(e, false);
				});
			}
			public void mouseEntered(MouseEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.mouseEntered(e);
				});
			}
			public void mouseExited(MouseEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.mouseExited(e);
				});
			}
		});
		
		window.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if(mouseMoving) return;
				
				inputQueue.add(() -> {
					mouseMoving = true;
					OsierEngine.this.mouseMoved(e, true);
					
					mouseMoving = false;
				});
			}

			public void mouseMoved(MouseEvent e) {
				if(mouseMoving) return;

				inputQueue.add(() -> {
					mouseMoving = true;
					OsierEngine.this.mouseMoved(e, false);
					mouseMoving = false;
				});
			}
			
		});
		
		window.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.keyPressed(e, false);
				});
			}

			public void keyReleased(KeyEvent e) {
				inputQueue.add(() -> {
					OsierEngine.this.keyReleased(e, false);
				});
			}
			
		});
		
		window.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	if(windowResizing) return;
		    	inputQueue.add(new Runnable() {
		    		public void run() {
		    			windowResizing=true;
		    		    windowResized(window.getWidth(),window.getHeight());
		    		    windowResizing=false;
		    		}
		    	});
		    }
		});
		
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stop();
            }
        }); 
		
        //start the render thread
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
			frameRate = getDisplayRefreshRate();
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
	
	public GraphicsDevice getDisplay() {
		return display;
	}
	
	public float getDisplayRefreshRate() {
		return display.getDisplayMode().getRefreshRate();
	}
	
	public int getFPS() {
		return fps;
	}
		
	
}