package org.osier.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.osier.listeners.WindowListener;

public class Window extends BaseGUIObject implements WindowListener {
	public static List<Window> windows = new ArrayList<Window>();
	
	private Frame frame;
	private BufferStrategy bs;
	private boolean closing;
	private boolean disabled;
	private String title;
	private boolean decorated;
	private boolean visible;
	private Color backgroundColor;
	private int windowPosX,windowPosY;
	private Insets insets;
	public Window(String title, int width, int height, boolean decorated) {
		this.title = title;
		this.decorated = decorated;
		this.backgroundColor = Color.black;
		this.name = "Window";
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		this.frame = new Frame();
		frame.setLayout(null);
		frame.setIgnoreRepaint(true);
		frame.setTitle(title);
		frame.setBackground(backgroundColor);
		frame.setUndecorated(!decorated);
		/*if(decorated) {
			frame.setSize(width+getInsets().left, height+getInsets().top);
		}else {
			frame.setSize(width,height);
		}*/
		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);
		windowPosX = frame.getX();
		windowPosY = frame.getY();
		insets = frame.getInsets();
		visible = false;
		disabled = true;
		//windows.add(this);
	}
	
	public void render(Graphics2D g) {
		if(disabled || !frame.isVisible())return;
		children.render(g);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		if(disabled)return;
		frame.setTitle(title);
	}
	
	public void setIconImage(String path) {
		try {
           // Image icon = ImageIO.read(this.getClass().getResource(path));
            Image icon = ImageIO.read(new File(path));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void center() {
		if(disabled)return;
		frame.setLocationRelativeTo(null);
		windowPosX = frame.getX();
		windowPosY = frame.getY();
	}
	
	public void setPosition(int x, int y) {
		if(disabled)return;
		frame.setLocation(x,y);
		windowPosX = x;
		windowPosY = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		/*if(!frame.isUndecorated()) {
			frame.setSize(width+getInsets().left, height+getInsets().top);
		}else {
			frame.setSize(width,height);
		}*/
		if(disabled)return;
		frame.setSize(width,height);
	}
	
	public int getX() {
		return windowPosX;
	}
	
	public int getY() {
		if(disabled) return 0;
		return windowPosY;
	}
	
	/*public int getOriginX() {
		return x;
	}
	
	public int getOriginY() {
		return y;
	}*/
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Insets getInsets() {
		return insets;
	}

	
	private void setVisible(boolean visible) {
		if(visible && !frame.isVisible()) {
			frame.setVisible(visible);
			createBufferStrategy(2);
			frame.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Window.this.mouseClicked(e);
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					Window.this.mousePressed(e);
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					Window.this.mouseReleased(e);
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					Window.this.mouseEntered(e);
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					Window.this.mouseExited(e);
				}
				
			});
			frame.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					Window.this.mouseMoved(e, true);
				}
	
				@Override
				public void mouseMoved(MouseEvent e) {
					Window.this.mouseMoved(e, false);
				}
				
			});
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					
				}
	
				@Override
				public void keyPressed(KeyEvent e) {
					Window.this.keyPressed(e);
				}
	
				@Override
				public void keyReleased(KeyEvent e) {
					Window.this.keyReleased(e);
				}
				
			});
			
			frame.addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent componentEvent) {
			    	width = frame.getWidth();
					height = frame.getHeight();
					children.updateSizes();
			    	Window.this.windowResized(width, height);
			    }
			});
			
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
		            Window.this.windowClosing();
		            disable();
		        }
			});
		}else {
			frame.setVisible(visible);
		}
		
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isClosing() {
		return closing;
	}
	
	public void disable() {
		if(disabled) return;
		disabled = true;
		windows.remove(this);
		frame.dispose();
	}
	
	public void enable() {
		if(!disabled) return;
		disabled = false;
		frame = new Frame();
		frame.setLayout(null);
		frame.setIgnoreRepaint(true);
		frame.setTitle(title);
		frame.setBackground(backgroundColor);
		frame.setUndecorated(!decorated);
		/*if(decorated) {
			frame.setSize(width+getInsets().left, height+getInsets().top);
		}else {
			frame.setSize(width,height);
		}*/
		frame.setSize(width,height);
		frame.setLocation(windowPosX, windowPosY);
		insets = frame.getInsets();
		setVisible(true);
		windows.add(this);
	}
	
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		if(disabled) return;
		frame.setBackground(color);
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	
	public void createBufferStrategy(int buffers) {
		if(disabled || !frame.isVisible()) return;
		frame.createBufferStrategy(buffers);
		bs = frame.getBufferStrategy();
	}
	
	public BufferStrategy getBufferStrategy() {
		return disabled ? null : bs;
	}
	
	public Graphics2D getDrawGraphics() {
		return disabled ? null : (Graphics2D) bs.getDrawGraphics();
	}
	
	public void show() {
		try {
			bs.show();
		}catch(IllegalStateException e) {}
	}
}
