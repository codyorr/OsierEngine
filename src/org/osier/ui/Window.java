package org.osier.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

public class Window extends BaseGUIObject {
	
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
	}
	
	

	public void update() {
		this.width = frame.getWidth();
		this.height = frame.getHeight();
		children.updateSizes();
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
		frame.setVisible(visible);
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
	
	public void addMouseListener(MouseListener listener) {
		frame.addMouseListener(listener);
	}
	
	public void addMouseMotionListener(MouseMotionListener listener) {
		frame.addMouseMotionListener(listener);
	}
	
	public void addKeyListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}
	
	public void addComponentListener(ComponentAdapter adapter) {
		frame.addComponentListener(adapter);
	}
	
	public void addWindowListener(WindowListener listener) {
		frame.addWindowListener(listener);
	}
	
	

}
