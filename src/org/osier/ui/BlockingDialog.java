package org.osier.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.osier.listeners.WindowListener;

public class BlockingDialog extends BaseGUIObject implements WindowListener {
	//public static List<Window> windows = new ArrayList<Window>();
	
	private Frame frame;
	private Graphics2D g;
	private BufferStrategy bs;
	private boolean disabled;
	private String title;
	private boolean decorated;
	private Color backgroundColor;
	private int windowPosX,windowPosY;
	private Insets insets;
	protected List<GUIButtonObject> buttons;
	private GUIButtonObject targetButton;
	
	public BlockingDialog(String title, int width, int height, boolean decorated) {
		this.title = title;
		this.decorated = decorated;
		this.backgroundColor = Color.black;
		this.name = "Window";
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		this.disabled = true;
		this.buttons = new ArrayList<GUIButtonObject>();
	}
	
	public void render() {
		if(disabled || !frame.isVisible())return;
		
		try {
			g = (Graphics2D) bs.getDrawGraphics();
		}catch(Exception e) {
			frame.createBufferStrategy(2);
			bs = frame.getBufferStrategy();
			g = (Graphics2D) bs.getDrawGraphics();
		}
		
		g.clearRect(0, 0, width,height);
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//g.translate(window.getInsets().left, window.getInsets().top);
		children.render(g);
	    bs.show();
	    g.dispose();
	}
	
	@Override
	public void add(GUIObject obj) {
		super.add(obj);
		updateButtons();
	}
	
	@Override
	public void remove(GUIObject obj) {
		super.remove(obj);
		updateButtons();
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
			
			frame.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(targetButton != null) {
						targetButton.mouseClicked(e);
						BlockingDialog.this.mouseClicked(e, true);
						return;
					}
					BlockingDialog.this.mouseClicked(e, false);
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					if(targetButton != null) {
						targetButton.setPressed(true);
						targetButton.mousePressed(e);
						BlockingDialog.this.mousePressed(e,  true);
						return;
					}
					BlockingDialog.this.mousePressed(e, false);
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					if(targetButton != null && targetButton.pressed) {
						targetButton.setPressed(false);
						targetButton.mouseReleased(e);
						BlockingDialog.this.mouseReleased(e, true);
						return;
					}
					BlockingDialog.this.mouseReleased(e, false);
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					BlockingDialog.this.mouseEntered(e);
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					BlockingDialog.this.mouseExited(e);
				}
				
			});
			frame.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					BlockingDialog.this.mouseMoved(e, true);
				}
	
				@Override
				public void mouseMoved(MouseEvent e) {
					BlockingDialog.this.mouseMoved(e, false);
					if(targetButton == null) {
						for(GUIButtonObject button : buttons) {
							if(button.contains(e.getX(), e.getY())) {
								targetButton = button;
								button.setHovered(true);
								button.mouseEntered(e);
								return;
							}
						}
					}else if(!targetButton.contains(e.getX(), e.getY())) {
						targetButton.setHovered(false);
						targetButton.setPressed(false);
						targetButton.mouseExited(e);
						targetButton = null;
					}
				}
				
			});
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					
				}
	
				@Override
				public void keyPressed(KeyEvent e) {
					BlockingDialog.this.keyPressed(e);
				}
	
				@Override
				public void keyReleased(KeyEvent e) {
					BlockingDialog.this.keyReleased(e);
				}
				
			});
			
			frame.addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent componentEvent) {
			    	width = frame.getWidth();
					height = frame.getHeight();
					children.updateSizes();
			    	windowResized(width, height);
			    }
			});
			
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
		            BlockingDialog.this.windowClosing();
		            disable();
		        }
			});
		}else {
			frame.setVisible(visible);
		}
	}
	
	public boolean isVisible() {
		return frame.isVisible();
	}
	
	protected void enable(Window window) {
		if(!disabled) return;
		disabled = false;
		window.setBlocked(true);
		frame = new Frame();
		frame.setLayout(null);
		frame.setIgnoreRepaint(true);
		frame.setTitle(title);
		frame.setBackground(backgroundColor);
		frame.setUndecorated(!decorated);
		frame.setResizable(false);
		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);
		windowPosX = frame.getLocation().x;
		windowPosY = frame.getLocation().y;
		setVisible(true);
		insets = frame.getInsets();
		/*if(decorated) {
			frame.setSize(width+insets.left, height+insets.top);
		}else {
			frame.setSize(width,height);
		}*/
		
		frame.setSize(width,height);
	}
	
	public void disable() {
		if(disabled) return;
		disabled = true;
		frame.dispose();
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		if(disabled) return;
		frame.setBackground(color);
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setResizable(boolean resizable) {
		frame.setResizable(resizable);
	}
	
	public boolean isResizable() {
		return frame.isResizable();
	}
	

	protected void updateButtons() {
		buttons = new ArrayList<GUIButtonObject>();
		collectButtons(children);
		buttons.sort(Comparator.comparingInt(GUIObject::getDisplayOrder).reversed());
	}
	
	private void collectButtons(GUIChildren guiChildren) {
		for(GUIObject child : guiChildren.list) {
			if(child instanceof GUIButtonObject) {
				buttons.add((GUIButtonObject)child);				
			}
			collectButtons(child.getChildren());
		}
	}
}
