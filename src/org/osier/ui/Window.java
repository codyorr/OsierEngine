package org.osier.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
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
import java.util.ArrayList;
import java.util.List;

import org.osier.listeners.WindowListener;

public class Window extends BaseGUIObject implements WindowListener {
	public static List<Window> windows = new ArrayList<Window>();
	
	private Frame frame;

	public Window(String title, int width, int height, boolean decorated) {
		this.name = "Window";
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		this.frame = new Frame();
		frame.setLayout(null);
		frame.setIgnoreRepaint(true);
		frame.setTitle(title);
		frame.setBackground(Color.black);
		frame.setUndecorated(!decorated);
		/*if(decorated) {
			frame.setSize(width+getInsets().left, height+getInsets().top);
		}else {
			frame.setSize(width,height);
		}*/
		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);
		
		windows.add(this);
	}
	
	public void render(Graphics2D g) {
		children.render(g);
	}
	
	public String getTitle() {
		return frame.getTitle();
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	public void center() {
		frame.setLocationRelativeTo(null);
	}
	
	public void setPosition(int x, int y) {
		frame.setLocation(null);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		/*if(!frame.isUndecorated()) {
			frame.setSize(width+getInsets().left, height+getInsets().top);
		}else {
			frame.setSize(width,height);
		}*/
		
		frame.setSize(width,height);
	}
	
	public int getX() {
		return frame.getX();
	}
	
	public int getY() {
		return frame.getY();
	}
	
	/*public int getOriginX() {
		return x;
	}
	
	public int getOriginY() {
		return y;
	}*/
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	public Insets getInsets() {
		return frame.getInsets();
	}
	
	public void setBackgroundColor(Color color) {
		frame.setBackground(color);
	}
	
	public void setVisible(boolean visible) {
		if(visible && !frame.isVisible()) {
			frame.setVisible(visible);

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
		        }
			});
		}else {
			frame.setVisible(visible);
		}
	}
	
	public boolean isVisible() {
		return frame.isVisible();
	}
	
	
	public void createBufferStrategy(int buffers) {
		if(!frame.isVisible()) return;
		frame.createBufferStrategy(buffers);
	}
	
	public BufferStrategy getBufferStrategy() {
		return frame.getBufferStrategy();
	}
}
