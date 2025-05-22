package org.osier.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Insets;

public class Window extends BaseGUIObject {
	
	private Frame frame;
	
	public Window(String title, int width, int height, boolean decorated) {
		this.name = "Window";
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		this.frame = new Frame() {
			private static final long serialVersionUID = 1L;
			
		};
		frame.setLayout(null);
		frame.setIgnoreRepaint(true);
		frame.setTitle(title);
		frame.setBackground(Color.black);
		frame.setUndecorated(!decorated);
		if(decorated) {
			frame.setSize(width+getInsets().left, height+getInsets().top);
		}else {
			frame.setSize(width, height);
		}
		frame.setLocationRelativeTo(null);
	}
	
	public Insets getInsets() {
		return frame.getInsets();
	}
	
	
}
